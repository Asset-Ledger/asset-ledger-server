package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import asset.ledger.assetledgerserver.asset.domain.repository.AssetDetailRepository;
import asset.ledger.assetledgerserver.asset.domain.repository.AssetRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssetDetailServiceImpl implements AssetDetailService {

    private final AssetDetailRepository assetDetailRepository;
    private final AssetRepository assetRepository;
    private final AssetService assetService;

    @Override
    public ResponseAssetDetailListDto getAssetDetails(final String userId, final String assetType) {
        List<AssetDetail> assetDetails = assetDetailRepository.getAssetDetail(userId, assetType);

        ResponseAssetDetailListDto responseAssetDetailListDto = new ResponseAssetDetailListDto();

        for (AssetDetail assetDetail : assetDetails) {
            responseAssetDetailListDto.addResponseAssetDetailDto(ResponseAssetDetailDto.fromEntity(assetDetail));
        }

        return responseAssetDetailListDto;
    }

    @Transactional
    @Override
    public void createAssetDetail(final String userId, final RequestAssetDetailDto requestAssetDetailDto) {
        assetService.existAsset(userId, requestAssetDetailDto.getAssetType());

        Asset asset = assetService.getAsset(userId, requestAssetDetailDto.getAssetType());
        AssetDetail assetDetail = requestAssetDetailDto.toEntity(userId);

        asset.addAssetDetailTypeAmount(requestAssetDetailDto.getTotalAmount());

        assetDetailRepository.save(assetDetail);
    }

    @Override
    public Boolean existAssetDetail(final String userId, final String assetType, final String assetDetailType) {
        return assetDetailRepository.existAssetDetail(userId, assetType, assetDetailType);
    }

    @Override
    public AssetDetail getAssetDetail(final String userId, final String assetType, final String assetDetailType) {
        AssetDetail assetDetail = assetDetailRepository.getAssetDetail(userId, assetType, assetDetailType);

        if (assetDetail == null) {
            throw new EntityNotFoundException("존재하지 않는 자산 세부 타입 입니다.");
        }
        return assetDetail;
    }

}
