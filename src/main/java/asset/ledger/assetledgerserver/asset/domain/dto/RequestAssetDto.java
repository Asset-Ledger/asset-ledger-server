package asset.ledger.assetledgerserver.asset.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import lombok.Getter;

@Getter
public class RequestAssetDto {
    private String assetType;
    private int totalAmount;

    public Asset toEntity(String userId) {
        return Asset
                .builder()
                .userId(userId)
                .assetType(this.assetType)
                .totalAmount(this.totalAmount)
                .build();
    }

}
