package asset.ledger.assetledgerserver.scheduler.application.service;

import asset.ledger.assetledgerserver.scheduler.domain.RequestFCMPushSchedulerDto;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;

public interface FCMPushSchedulerService {
    ResponseEntity<Void> createFCMPushScheduler(final String userId, final RequestFCMPushSchedulerDto requestFCMPushSchedulerDto) throws Exception;
    void getWorkingFCMPushSchedulerJobs() throws SchedulerException;

}
