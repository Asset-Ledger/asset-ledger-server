package asset.ledger.assetledgerserver.asset.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import asset.ledger.assetledgerserver.asset.domain.repository.AssetDetailRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetDetailServiceImpl implements AssetDetailService {

    private final AssetDetailRepository assetDetailRepository;

    @Override
    public ResponseAssetDetailListDto getAssetDetail(final String userId, final String assetType) {
        List<AssetDetail> assetDetails = assetDetailRepository.getAssetDetail(userId, assetType);

        ResponseAssetDetailListDto responseAssetDetailListDto = new ResponseAssetDetailListDto();

        for (AssetDetail assetDetail : assetDetails) {
            responseAssetDetailListDto.addResponseAssetDetailDto(ResponseAssetDetailDto.fromEntity(assetDetail));
        }

        return responseAssetDetailListDto;
    }

    @Override
    public void createAssetDetail(final String userId, final RequestAssetDetailDto requestAssetDetailDto) {
        AssetDetail assetDetail = requestAssetDetailDto.toEntity(userId);

        assetDetailRepository.save(assetDetail);
    }

}
