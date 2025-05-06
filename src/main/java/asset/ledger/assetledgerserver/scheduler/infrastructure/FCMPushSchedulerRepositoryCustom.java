package asset.ledger.assetledgerserver.scheduler.infrastructure;

import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import java.util.List;

public interface FCMPushSchedulerRepositoryCustom {
    List<FCMPushScheduler> findFCMPushSchedulerByUserId(final String userId);
    FCMPushScheduler findFCMPushSchedulerById(final Long id, final String userId);
}
