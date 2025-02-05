package asset.ledger.assetledgerserver.asset.infrastructure;

import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import java.util.List;

public interface AssetDetailRepositoryCustom {
    List<AssetDetail> getAssetDetail(final String userId, final String assetType);

    Boolean existAssetDetail(final String userId, final String assetType, final String assetDetailType);

    AssetDetail getAssetDetail(final String userId, final String assetType, final String assetDetailType);

}
