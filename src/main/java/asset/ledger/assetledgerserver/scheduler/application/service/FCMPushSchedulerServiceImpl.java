package asset.ledger.assetledgerserver.scheduler.application.service;

import asset.ledger.assetledgerserver.scheduler.application.job.FCMPushSchedulerJob;
import asset.ledger.assetledgerserver.scheduler.application.listener.FCMPushSchedulerJobListener;
import asset.ledger.assetledgerserver.scheduler.domain.RequestFCMPushSchedulerDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMPushSchedulerServiceImpl implements FCMPushSchedulerService {

    private final Scheduler scheduler;

    @Override
    public ResponseEntity<Void> createFCMPushScheduler(
            final String userId,
            final RequestFCMPushSchedulerDto requestFCMPushSchedulerDto
    ) throws Exception {
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
        registTrigger(jobKey, userId, days, time);

        log.info("createFCMPushScheduler 생성 완료");

        return new ResponseEntity<>(HttpStatus.OK);
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

    private void registJobListener() throws SchedulerException {
        // TODO("scheduler 생성 성공하면 DB에 scheduler 저장 로직 구현")
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

    private void registTrigger(
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
