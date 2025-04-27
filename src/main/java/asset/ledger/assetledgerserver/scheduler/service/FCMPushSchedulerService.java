package asset.ledger.assetledgerserver.scheduler.service;

public interface FCMPushSchedulerService {
    void createFCMPushScheduler(final String userId);
    void getWorkingFCMPushSchedulerJobs();

}
