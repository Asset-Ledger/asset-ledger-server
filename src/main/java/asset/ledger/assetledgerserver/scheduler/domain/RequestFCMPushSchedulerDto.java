package asset.ledger.assetledgerserver.scheduler.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class RequestFCMPushSchedulerDto {
    private List<String> days;
    private String time;
}
