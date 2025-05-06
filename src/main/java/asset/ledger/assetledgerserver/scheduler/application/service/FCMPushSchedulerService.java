package asset.ledger.assetledgerserver.scheduler.application.service;

import asset.ledger.assetledgerserver.scheduler.domain.dto.RequestFCMPushSchedulerDto;
import asset.ledger.assetledgerserver.scheduler.domain.dto.ResponseFCMPushSchedulerListDto;
import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;

public interface FCMPushSchedulerService {
    void createFCMPushScheduler(final String userId, final RequestFCMPushSchedulerDto requestFCMPushSchedulerDto) throws Exception;
    ResponseFCMPushSchedulerListDto getFCMPushSchedulers(final String userId);
    void turnOnFcmPushSchedulerById(final Long id, final String userId)
            throws JsonProcessingException, SchedulerException;
    void deleteFCMPushSchedulerById(final Long id, final String userId)
            throws JsonProcessingException, SchedulerException;
    void getWorkingFCMPushSchedulerJobs() throws SchedulerException;

}
