package asset.ledger.assetledgerserver.scheduler.application.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class FCMPushSchedulerJobListener implements JobListener {

    @Override
    public String getName() {
        return "FCMPushJobListener";
    }

    @Override
    public void jobToBeExecuted(final JobExecutionContext context) {
        System.out.println("Job 실행 전 실행");
    }

    @Override
    public void jobExecutionVetoed(final JobExecutionContext context) {
        System.out.println("Job 실행 실패");
    }

    @Override
    public void jobWasExecuted(final JobExecutionContext context, final JobExecutionException jobException) {
        System.out.println("Job 실행 후 실행");
    }
}
