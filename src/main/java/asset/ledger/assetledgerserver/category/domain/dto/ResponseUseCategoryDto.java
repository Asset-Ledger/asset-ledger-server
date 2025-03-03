package asset.ledger.assetledgerserver.category.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.category.domain.entity.UseCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUseCategoryDto {
    private String useCategory;

    public static ResponseUseCategoryDto fromEntity(UseCategory useCategory) {
        return ResponseUseCategoryDto
                .builder()
                .useCategory(useCategory.getUseCategory())
                .build();
    }

}
