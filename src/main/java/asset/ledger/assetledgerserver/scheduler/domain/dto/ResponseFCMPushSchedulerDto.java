package asset.ledger.assetledgerserver.scheduler.domain.dto;

import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseFCMPushSchedulerDto {
    private Long id;
    private String userId;
    private List<String> days;
    private String time;
    private Boolean status;

    public static ResponseFCMPushSchedulerDto fromEntity(final FCMPushScheduler fcmPushScheduler) {
        return ResponseFCMPushSchedulerDto
                .builder()
                .id(fcmPushScheduler.getId())
                .userId(fcmPushScheduler.getUserId())
                .days(daysStringToDays(fcmPushScheduler.getDays()))
                .time(fcmPushScheduler.getTime())
                .status(fcmPushScheduler.getStatus())
                .build();
    }

    private static List<String> daysStringToDays(final String days) {
        return Arrays.asList(days.split(","));
    }
}
