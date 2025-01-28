package asset.ledger.assetledgerserver.asset.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseAssetDetailListDto {
    List<ResponseAssetDetailDto> assetDetailDtos;

    public ResponseAssetDetailListDto() {
        this.assetDetailDtos = new ArrayList<>();
    }

    public void addResponseAssetDetailDto(ResponseAssetDetailDto responseAssetDetailDto) {
        this.assetDetailDtos.add(responseAssetDetailDto);
    }

}
