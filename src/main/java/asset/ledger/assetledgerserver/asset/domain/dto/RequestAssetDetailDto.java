package asset.ledger.assetledgerserver.asset.domain.dto;

import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import lombok.Getter;

@Getter
public class RequestAssetDetailDto {
    private String assetType;
    private String assetDetailType;
    private String connectedAccount; // 카드일 경우 연결된 계좌 설정
    private int totalAmount;

    public AssetDetail toEntity(String userId) {
        return AssetDetail
                .builder()
                .userId(userId)
                .assetType(this.assetType)
                .assetDetailType(this.assetDetailType)
                .connectedAccount(this.connectedAccount)
                .totalAmount(this.totalAmount)
                .build();
    }

}
