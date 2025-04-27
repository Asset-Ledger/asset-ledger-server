package asset.ledger.assetledgerserver.scheduler.application.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FCMPushSchedulerJob implements Job {
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        // 파이버베이스 FCM 연동
        System.out.println("파이어베이스 FCM push 호출");
    }

}
