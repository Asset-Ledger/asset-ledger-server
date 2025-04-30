package asset.ledger.assetledgerserver.scheduler.application.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class FCMPushSchedulerJob implements Job {
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        // 파이버베이스 FCM 연동
        log.info("파이어베이스 FCM push 호출");
    }

}
