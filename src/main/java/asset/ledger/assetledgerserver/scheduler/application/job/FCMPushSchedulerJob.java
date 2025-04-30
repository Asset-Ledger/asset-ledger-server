package asset.ledger.assetledgerserver.scheduler.application.job;

import asset.ledger.assetledgerserver.fcm.application.service.FCMService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@NoArgsConstructor
public class FCMPushSchedulerJob implements Job {

    @Autowired
    private FCMService fcmService;

    @Override
    public void execute(final JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String userId = jobDataMap.getString("userId");

        try {
            // fCMPush 호출
            fcmService.requestFCMPush(userId);
            log.info("FCMPushSchedulerJob requestFCMPush FCM Push 호출 성공");
        } catch (Exception e) {
            log.error("FCMPushSchedulerJob requestFCMPush FCM Push error 발생");
        }
    }
}
