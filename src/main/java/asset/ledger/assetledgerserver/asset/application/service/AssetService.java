package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;

public interface AssetService {
    ResponseAssetListDto getAssets(String userId);
    void createAsset(String userId, RequestAssetDto requestAssetDto);

}
