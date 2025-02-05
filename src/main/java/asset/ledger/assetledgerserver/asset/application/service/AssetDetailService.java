package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;

public interface AssetDetailService {
    ResponseAssetDetailListDto getAssetDetails(final String userId, final String assetType);
    void createAssetDetail(final String userId, final RequestAssetDetailDto requestAssetDetailDto);

    Boolean existAssetDetail(final String userId, final String assetType, final String assetDetailType);

    AssetDetail getAssetDetail(final String userId, final String assetType, final String assetDetailType);

}
