package asset.ledger.assetledgerserver.config.scheduler;

import asset.ledger.assetledgerserver.scheduler.application.job.FCMPushSchedulerJob;
import asset.ledger.assetledgerserver.scheduler.application.listener.FCMPushSchedulerJobListener;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class FCMPushSchedulerConfig {

    private final Scheduler scheduler;

    public void createFCMPushScheduler(final String userId) throws SchedulerException {
        System.out.println("createFCMPushScheduler 생성 시작");
        JobDetail jobDetail = getFCMPushJobDetail(userId + " FCM push job", "asset-ledger FCM job");
        Trigger trigger = getFCMPushTrigger(userId + " FCM push trigger", "asset-ledger fcm trigger");

        FCMPushSchedulerJobListener fcmPushSchedulerJobListener = new FCMPushSchedulerJobListener();

        scheduler.getListenerManager().addJobListener(fcmPushSchedulerJobListener);
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("createFCMPushScheduler 생성 완료");
    }

    private JobDetail getFCMPushJobDetail(String jobName, String jobGroup) {
        return JobBuilder
                .newJob(FCMPushSchedulerJob.class)
                .withIdentity(jobName + " " + LocalDateTime.now(), jobGroup)
                .withDescription("가계부 생성 알림 FCM push 요청 job" + " " + LocalDateTime.now())
                .build();
    }

    private Trigger getFCMPushTrigger(String triggerName, String triggerGroup) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName + " " + LocalDateTime.now(), triggerGroup)
                .withDescription("가계부 생성 알림 FCM push 요청 trigger" + " " + LocalDateTime.now())
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 2 18 * * ?")
                )
                .build();
    }

    public void getWorkingSchedulerJobs() throws SchedulerException {
//        List<JobExecutionContext> jobs = scheduler.get();
//
//        for (JobExecutionContext jobExecutionContext : jobs) {
//            System.out.println("Executing Job: " + jobExecutionContext.getJobDetail().getDescription());
//        }

        for (String groupName : scheduler.getJobGroupNames()) {
            System.out.println("GroupName: " + groupName);
            // 그룹 내의 모든 Job들을 조회
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                // Job에 관련된 Trigger 들을 가져옴
                List<? extends Trigger> triggerKeys = scheduler.getTriggersOfJob(jobKey);

                System.out.println("JobKey: " + jobKey);
                System.out.println("JobKey.getName(): " + jobKey.getName());

                // Trigger 출력
                for (Trigger triggerKey : triggerKeys) {
                    Trigger trigger = scheduler.getTrigger(triggerKey.getKey());
                    System.out.println("Trigger.getKey(): " + trigger.getKey());
                    System.out.println("Trigger.getKey().getName(): " + trigger.getKey().getName());
                }
            }
        }
    }
}
