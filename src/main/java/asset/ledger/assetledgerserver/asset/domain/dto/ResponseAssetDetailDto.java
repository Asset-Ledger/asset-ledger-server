package asset.ledger.assetledgerserver.asset.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseAssetDetailDto {
    private String assetType;
    private String assetDetailType;
    private String connectedAccount; // 카드일 경우 연결된 계좌 설정
    private int totalAmount;

    public static ResponseAssetDetailDto fromEntity(AssetDetail assetDetail) {
        return ResponseAssetDetailDto
                .builder()
                .assetType(assetDetail.getAssetType())
                .assetDetailType(assetDetail.getAssetDetailType())
                .connectedAccount(assetDetail.getConnectedAccount())
                .totalAmount(assetDetail.getTotalAmount())
                .build();
    }

}
