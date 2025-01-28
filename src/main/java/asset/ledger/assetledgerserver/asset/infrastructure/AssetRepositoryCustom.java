package asset.ledger.assetledgerserver.asset.infrastructure;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import java.util.List;

public interface AssetRepositoryCustom {
    List<Asset> getAsset(String userId);

}
