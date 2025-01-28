package asset.ledger.assetledgerserver.asset.domain.dto;

import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ResponseAssetListDto {
    List<ResponseAssetDto> assetDtos;

    public ResponseAssetListDto() {
        this.assetDtos = new ArrayList<>();
    }

    public void addResponseAssetDto(ResponseAssetDto responseAssetDto) {
        this.assetDtos.add(responseAssetDto);
    }

}
