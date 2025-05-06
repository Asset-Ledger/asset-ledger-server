package asset.ledger.assetledgerserver.scheduler.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailDto;
import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseFCMPushSchedulerListDto {
    private final List<ResponseFCMPushSchedulerDto> responseFCMPushSchedulerDtos = new ArrayList<>();

    public ResponseFCMPushSchedulerListDto(final List<FCMPushScheduler> fcmPushSchedulers) {
        for (FCMPushScheduler fcmPushScheduler : fcmPushSchedulers) {
            this.responseFCMPushSchedulerDtos.add(ResponseFCMPushSchedulerDto.fromEntity(fcmPushScheduler));
        }
    }

    public void addResponseFCMPushSchedulerDto(ResponseFCMPushSchedulerDto responseFCMPushSchedulerDto) {
        this.responseFCMPushSchedulerDtos.add(responseFCMPushSchedulerDto);
    }
}
