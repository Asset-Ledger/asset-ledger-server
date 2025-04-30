package asset.ledger.assetledgerserver.scheduler.application.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class FCMPushSchedulerJobListener implements JobListener {

    @Override
    public String getName() {
        return "FCMPushJobListener";
    }

    @Override
    public void jobToBeExecuted(final JobExecutionContext context) {
        log.info("Job 실행 전 실행");
    }

    @Override
    public void jobExecutionVetoed(final JobExecutionContext context) {
        log.info("Job 실행 실패");
    }

    @Override
    public void jobWasExecuted(final JobExecutionContext context, final JobExecutionException jobException) {
        log.info("Job 실행 후 실행");
    }
}
