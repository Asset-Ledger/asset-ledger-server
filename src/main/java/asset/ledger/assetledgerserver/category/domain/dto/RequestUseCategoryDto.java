package asset.ledger.assetledgerserver.category.domain.dto;

import asset.ledger.assetledgerserver.category.domain.entity.UseCategory;
import lombok.Getter;

@Getter
public class RequestUseCategoryDto {
    private String useCategory;

    public UseCategory toEntity(String userId) {
        return UseCategory
                .builder()
                .userId(userId)
                .useCategory(this.useCategory)
                .build();
    }

}
