package asset.ledger.assetledgerserver.category.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ResponseUseCategoryListDto {
    List<ResponseUseCategoryDto> useCategoryDtos;

    public ResponseUseCategoryListDto() {
        this.useCategoryDtos = new ArrayList<>();
    }

    public void addResponseUseCategoryDto(ResponseUseCategoryDto responseUseCategoryDto) {
        this.useCategoryDtos.add(responseUseCategoryDto);
    }

}
