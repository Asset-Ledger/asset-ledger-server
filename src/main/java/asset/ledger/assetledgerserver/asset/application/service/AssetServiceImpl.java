package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.asset.domain.repository.AssetRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    @Override
    public ResponseAssetListDto getAssets(final String userId) {
        List<Asset> assets = assetRepository.getAssets(userId);

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

    @Override
    public Boolean existAsset(final String userId, final String assetType) {
        return assetRepository.existAsset(userId, assetType);
    }

    @Override
    public Asset getAsset(final String userId, final String assetType) {
        Asset asset = assetRepository.getAsset(userId, assetType);

        if (asset == null) {
            throw new EntityNotFoundException("존재하지 않는 자산 타입 입니다.");
        }

        return asset;
    }

    @Override
    public void updateAssetAmount(
            final String userId,
            final String assetType,
            final String plusMinusType,
            final int amount
    ) {
        Asset asset = getAsset(userId, assetType);

        asset.calculateAmount(plusMinusType, amount);

        assetRepository.save(asset);
    }

}
