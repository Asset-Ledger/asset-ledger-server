package asset.ledger.assetledgerserver.scheduler.application.service;

public interface FCMPushSchedulerService {
    void createFCMPushScheduler(final String userId);
    void getWorkingFCMPushSchedulerJobs();

}
