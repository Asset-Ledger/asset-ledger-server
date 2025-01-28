package asset.ledger.assetledgerserver.asset.domain.repository;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.asset.infrastructure.AssetRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long>, AssetRepositoryCustom {

}
