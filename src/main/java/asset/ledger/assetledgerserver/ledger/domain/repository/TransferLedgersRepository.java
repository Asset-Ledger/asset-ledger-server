package asset.ledger.assetledgerserver.ledger.domain.repository;

import asset.ledger.assetledgerserver.ledger.domain.entity.TransferLedgers;
import asset.ledger.assetledgerserver.ledger.infrastructure.TransferLedgersRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferLedgersRepository extends
        JpaRepository<TransferLedgers, Long>,
        TransferLedgersRepositoryCustom {
}
