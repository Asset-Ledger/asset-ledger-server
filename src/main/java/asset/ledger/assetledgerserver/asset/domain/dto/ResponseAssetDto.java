package asset.ledger.assetledgerserver.asset.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseAssetDto {
    private String assetType;
    private int totalAmount;

    public static ResponseAssetDto fromEntity(Asset asset) {
        return ResponseAssetDto
                .builder()
                .assetType(asset.getAssetType())
                .totalAmount(asset.getTotalAmount())
                .build();
    }

}
