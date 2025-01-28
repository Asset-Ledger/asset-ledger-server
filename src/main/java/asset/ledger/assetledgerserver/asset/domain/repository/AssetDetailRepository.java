package asset.ledger.assetledgerserver.asset.domain.repository;

import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import asset.ledger.assetledgerserver.asset.infrastructure.AssetDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetDetailRepository extends JpaRepository<AssetDetail, Long>, AssetDetailRepositoryCustom {

}
