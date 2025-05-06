package asset.ledger.assetledgerserver.scheduler.domain.repository;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.infrastructure.LedgerRepositoryCustom;
import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import asset.ledger.assetledgerserver.scheduler.infrastructure.FCMPushSchedulerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMPushSchedulerRepository extends JpaRepository<FCMPushScheduler, Long>,
        FCMPushSchedulerRepositoryCustom {

}
