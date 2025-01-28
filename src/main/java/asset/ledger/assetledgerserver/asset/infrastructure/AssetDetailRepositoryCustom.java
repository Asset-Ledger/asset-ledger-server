package asset.ledger.assetledgerserver.asset.infrastructure;

import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import java.util.List;

public interface AssetDetailRepositoryCustom {
    List<AssetDetail> getAssetDetail(String userId, String assetType);

}
