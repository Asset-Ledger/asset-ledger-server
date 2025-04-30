package asset.ledger.assetledgerserver.scheduler.application.service;

import asset.ledger.assetledgerserver.config.scheduler.FCMPushSchedulerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

@Slf4j
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
            log.error(e.getMessage());
            log.error("[FCMPushSchedulerServiceImpl] createFCMPushScheduler에서 알수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public void getWorkingFCMPushSchedulerJobs() {
        try {
            fcmPushSchedulerConfig.getWorkingSchedulerJobs();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("[FCMPushSchedulerServiceImpl] getWorkingFCMPushSchedulerJobs에서 알수 없는 오류가 발생했습니다.");
        }
    }


}
