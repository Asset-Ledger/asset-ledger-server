package asset.ledger.assetledgerserver.scheduler.application.service.dto;

import lombok.Builder;
import lombok.Getter;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

@Getter
@Builder
public class FCMPushSchedulerInfoDto {
    private JobKey jobKey;
    private TriggerKey triggerKey;
}
