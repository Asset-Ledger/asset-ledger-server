package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.asset.domain.repository.AssetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    @Override
    public ResponseAssetListDto getAssets(final String userId) {
        List<Asset> assets = assetRepository.getAsset(userId);

        ResponseAssetListDto responseAssetListDto = new ResponseAssetListDto();

        for (Asset asset : assets) {
            responseAssetListDto.addResponseAssetDto(ResponseAssetDto.fromEntity(asset));
        }

        return responseAssetListDto;
    }

    @Override
    public void createAsset(final String userId, final RequestAssetDto requestAssetDto) {
        Asset asset = requestAssetDto.toEntity(userId);

        assetRepository.save(asset);
    }

}
