package asset.ledger.assetledgerserver.asset.infrastructure;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import java.util.List;

public interface AssetRepositoryCustom {
    List<Asset> getAssets(final String userId);

    Boolean existAsset(final String userId, final String assetType);

    Asset getAsset(final String userId, final String assetType);

}
