package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailListDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;

public interface AssetDetailService {
    ResponseAssetDetailListDto getAssetDetail(String userId, String assetType);
    void createAssetDetail(String userId, RequestAssetDetailDto requestAssetDetailDto);

}
