package asset.ledger.assetledgerserver.scheduler.domain.dto;

import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import java.util.List;
import lombok.Getter;

@Getter
public class RequestFCMPushSchedulerDto {
    private List<String> days;
    private String time;

    public FCMPushScheduler toEntity(final String userId, final String jobKey, final String triggerKey) {
        return FCMPushScheduler
                .builder()
                .userId(userId)
                .days(daysToString())
                .time(this.time)
                .status(true)
                .jobKey(jobKey)
                .triggerKey(triggerKey)
                .build();
    }

    private String daysToString() {
        return String.join(",", this.days);
    }
}
