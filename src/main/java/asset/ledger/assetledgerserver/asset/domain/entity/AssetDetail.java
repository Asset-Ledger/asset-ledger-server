package asset.ledger.assetledgerserver.asset.domain.entity;

import asset.ledger.assetledgerserver.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ASSET_DETAIL")
public class AssetDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String assetType;
    @Column(unique = true)
    private String assetDetailType;
    private String connectedAccount; // 카드일 경우 연결된 계좌 설정
    private int totalAmount;

    public void updateAmount(final String plusMinusType, final int amount) {
        if (plusMinusType.equals("PLUS") || plusMinusType.equals("plus")) {
            this.totalAmount += amount;
        } else if (plusMinusType.equals("MINUS") || plusMinusType.equals("minus")) {
            this.totalAmount -= amount;
        }
    }

}
