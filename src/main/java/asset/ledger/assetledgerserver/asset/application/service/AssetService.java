package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;

public interface AssetService {
    ResponseAssetListDto getAssets(final String userId);
    void createAsset(final String userId, final RequestAssetDto requestAssetDto);

    Boolean existAsset(final String userId, final String assetType);

    Asset getAsset(final String userId, final String assetType);

    void updateAssetAmount(final String userId, final String assetType, final String plusMinusType, final int amount);

}
