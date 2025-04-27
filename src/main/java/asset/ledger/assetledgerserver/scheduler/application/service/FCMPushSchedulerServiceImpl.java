package asset.ledger.assetledgerserver.scheduler.application.service;

import asset.ledger.assetledgerserver.config.scheduler.FCMPushSchedulerConfig;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMPushSchedulerServiceImpl implements FCMPushSchedulerService {

    private final FCMPushSchedulerConfig fcmPushSchedulerConfig;

    @Override
    public void createFCMPushScheduler(final String userId) {
        try {
            fcmPushSchedulerConfig.createFCMPushScheduler(userId);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[FCMPushSchedulerServiceImpl] createFCMPushScheduler에서 알수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public void getWorkingFCMPushSchedulerJobs() {
        try {
            fcmPushSchedulerConfig.getWorkingSchedulerJobs();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("[FCMPushSchedulerServiceImpl] getWorkingFCMPushSchedulerJobs에서 알수 없는 오류가 발생했습니다.");
        }
    }


}
