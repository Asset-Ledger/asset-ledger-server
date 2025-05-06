package asset.ledger.assetledgerserver.scheduler.application.service;

import asset.ledger.assetledgerserver.scheduler.application.job.FCMPushSchedulerJob;
import asset.ledger.assetledgerserver.scheduler.application.listener.FCMPushSchedulerJobListener;
import asset.ledger.assetledgerserver.scheduler.application.service.dto.FCMPushSchedulerInfoDto;
import asset.ledger.assetledgerserver.scheduler.domain.dto.RequestFCMPushSchedulerDto;
import asset.ledger.assetledgerserver.scheduler.domain.dto.ResponseFCMPushSchedulerListDto;
import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import asset.ledger.assetledgerserver.scheduler.domain.repository.FCMPushSchedulerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMPushSchedulerServiceImpl implements FCMPushSchedulerService {

    private final Scheduler scheduler;
    private final FCMPushSchedulerRepository fcmPushSchedulerRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void createFCMPushScheduler(
            final String userId,
            final RequestFCMPushSchedulerDto requestFCMPushSchedulerDto
    ) throws Exception {
        FCMPushSchedulerInfoDto fcmPushSchedulerInfoDto = buildFCMPushScheduler(userId, requestFCMPushSchedulerDto);
        saveFCMPushScheduler(userId, requestFCMPushSchedulerDto, fcmPushSchedulerInfoDto);
    }

    @Override
    public ResponseFCMPushSchedulerListDto getFCMPushSchedulers(final String userId) {
        List<FCMPushScheduler> fcmPushSchedulers = fcmPushSchedulerRepository.findFCMPushSchedulerByUserId(userId);
        return new ResponseFCMPushSchedulerListDto(fcmPushSchedulers);
    }

    @Override
    public void turnOnFcmPushSchedulerById(final Long id, final String userId)
            throws JsonProcessingException, SchedulerException {
        FCMPushScheduler fcmPushScheduler = fcmPushSchedulerRepository.findFCMPushSchedulerById(id, userId);

        if (fcmPushScheduler == null) {
            throw new EntityNotFoundException(String.format("존재하지 않는 fcmPushScheduler 입니다. id={}, userId={}", id, userId));
        }

        JobKey jobKey = objectMapper.readValue(fcmPushScheduler.getJobKey(), JobKey.class);
        TriggerKey triggerKey = objectMapper.readValue(fcmPushScheduler.getTriggerKey(), TriggerKey.class);

        // 원래 켜져 있어서 끄는 경우
        // job은 삭제하지 않고 trigger만 삭제
        if (fcmPushScheduler.getStatus()) {
            if (scheduler.checkExists(triggerKey)) {
                scheduler.unscheduleJob(triggerKey);
            }
        }
        // 원래 꺼져 있어서 켜는 경우
        else {
            // job이 존재하지 않으면 job을 먼저 생성 trigger만 다시 생성해서 job과 연결
            registJobDetail(jobKey);
            // trigger 생성
            registTrigger(jobKey, fcmPushScheduler.getUserId(), fcmPushScheduler.getListDays(), fcmPushScheduler.getTime());
        }

        fcmPushScheduler.turnOnOff();

        fcmPushSchedulerRepository.save(fcmPushScheduler);

        log.info("turnOnFcmPushSchedulerById scheduler onOff 상태={}", fcmPushScheduler.getStatus());
    }

    @Override
    public void deleteFCMPushSchedulerById(final Long id, final String userId)
            throws JsonProcessingException, SchedulerException {
        FCMPushScheduler fcmPushScheduler = fcmPushSchedulerRepository.findFCMPushSchedulerById(id, userId);

        if (fcmPushScheduler == null) {
            throw new EntityNotFoundException(String.format("존재하지 않는 fcmPushScheduler 입니다. id={}, userId={}", id, userId));
        }

        JobKey jobKey = objectMapper.readValue(fcmPushScheduler.getJobKey(), JobKey.class);
        TriggerKey triggerKey = objectMapper.readValue(fcmPushScheduler.getTriggerKey(), TriggerKey.class);

        if (scheduler.checkExists(triggerKey)) {
            scheduler.unscheduleJob(triggerKey);
        }
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }

        // scheduler 데이터는 자주 생성될거 같아서 데이터가 많이 쌓일 수도, 겹치는 데이터가 생길수도 있어서 hard delete
        fcmPushSchedulerRepository.delete(fcmPushScheduler);

        log.info("turnOnFcmPushSchedulerById scheduler onOff 상태={}", fcmPushScheduler.getStatus());
    }

    @Override
    public void getWorkingFCMPushSchedulerJobs() throws SchedulerException {
        //        List<JobExecutionContext> jobs = scheduler.get();
//
//        for (JobExecutionContext jobExecutionContext : jobs) {
//            log.info("Executing Job: " + jobExecutionContext.getJobDetail().getDescription());
//        }

        for (String groupName : scheduler.getJobGroupNames()) {
            log.info("GroupName: {}", groupName);
            // 그룹 내의 모든 Job들을 조회
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                // Job에 관련된 Trigger 들을 가져옴
                List<? extends Trigger> triggerKeys = scheduler.getTriggersOfJob(jobKey);

                log.info("JobKey: {}", jobKey);
                log.info("JobKey.getName(): {}", jobKey.getName());

                // Trigger 출력
                for (Trigger triggerKey : triggerKeys) {
                    Trigger trigger = scheduler.getTrigger(triggerKey.getKey());
                    log.info("Trigger.getKey(): {}", trigger.getKey());
                    log.info("Trigger.getKey().getName(): {}", trigger.getKey().getName());
                }
            }
        }
    }

    private FCMPushSchedulerInfoDto buildFCMPushScheduler(final String userId, final RequestFCMPushSchedulerDto requestFCMPushSchedulerDto)
            throws Exception {
        // 해당 알림을 꺼두면 trigger를 삭제하고, 다시 알림을 켜면 trigger를 생성해서 붙여주자
        // MON, TUE, WED, THU, FRI, SAT, SUN
        // time 형식 16:38
        log.info("createFCMPushScheduler 생성 시작");

        List<String> days = requestFCMPushSchedulerDto.getDays();
        String time = requestFCMPushSchedulerDto.getTime();

        validateTimeFormat(time);

        JobKey jobKey = new JobKey("fcmPush", userId);
//        registJobListener();
        registJobDetail(jobKey);
        TriggerKey triggerKey = registTrigger(jobKey, userId, days, time);

        log.info("createFCMPushScheduler 생성 완료");

        return FCMPushSchedulerInfoDto
                .builder()
                .jobKey(jobKey)
                .triggerKey(triggerKey)
                .build();
    }

    private void saveFCMPushScheduler(
            final String userId,
            final RequestFCMPushSchedulerDto requestFCMPushSchedulerDto,
            final FCMPushSchedulerInfoDto fcmPushSchedulerInfoDto
    ) throws JsonProcessingException {
        FCMPushScheduler fcmPushScheduler = requestFCMPushSchedulerDto
                .toEntity(
                        userId,
                        objectMapper.writeValueAsString(fcmPushSchedulerInfoDto.getJobKey()),
                        objectMapper.writeValueAsString(fcmPushSchedulerInfoDto.getTriggerKey())
                );
        fcmPushSchedulerRepository.save(fcmPushScheduler);

        log.info("createFCMPushScheduler DB 저장 완료");
    }

    private void registJobListener() throws SchedulerException {
        FCMPushSchedulerJobListener fcmPushSchedulerJobListener = new FCMPushSchedulerJobListener();

        scheduler.getListenerManager().addJobListener(fcmPushSchedulerJobListener);
    }

    private void registJobDetail(JobKey jobKey) throws SchedulerException {
        // Job이 존재하지 않으면 Job 추가
        if (!scheduler.checkExists(jobKey)) {
            JobDetail jobDetail = JobBuilder.newJob(FCMPushSchedulerJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("userId", jobKey.getGroup())
                    .storeDurably()
                    .build();

            scheduler.addJob(jobDetail, false);
        }
    }

    private TriggerKey registTrigger(
            final JobKey jobKey, final String userId, final List<String> days, final String time
    ) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey("fcmPush", getTriggerGroupName(userId, days, time));

        CronScheduleBuilder cronSchedule = createCronSchedule(days, time);

        if (!scheduler.checkExists(triggerKey)) {
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronSchedule)
                    .forJob(jobKey)
                    .build();

            scheduler.scheduleJob(trigger);  // 기존 Trigger 교체
        }

        return triggerKey;
    }

    private String getTriggerGroupName(final String userId, final List<String> days, final String time) {
        String daysString = String.join(",", days);

        return userId + "/" + daysString + "/" + time;
    }

    private CronScheduleBuilder createCronSchedule(final List<String> days, final String time) {
        String scheduleDays = String.join(",", days);
        String[] timeSplits = time.split(":");
        String hour = timeSplits[0];
        String minute = timeSplits[1];
        String cronTime = "0 " + minute + " " + hour + " " + "?" + " * " + scheduleDays;

        return CronScheduleBuilder.cronSchedule(cronTime);
    }

    private void validateTimeFormat(String timeString) throws Exception {
        String timePattern = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9])$";
        Pattern pattern = Pattern.compile(timePattern);
        Matcher matcher = pattern.matcher(timeString);

        if (!matcher.matches()) {
            throw new Exception("알맞지 않은 시간 형식입니다");
        }
    }
}
