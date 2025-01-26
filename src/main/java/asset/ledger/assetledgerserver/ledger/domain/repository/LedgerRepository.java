package asset.ledger.assetledgerserver.ledger.domain.repository;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.infrastructure.LedgerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<Ledger, Long>, LedgerRepositoryCustom {

}
