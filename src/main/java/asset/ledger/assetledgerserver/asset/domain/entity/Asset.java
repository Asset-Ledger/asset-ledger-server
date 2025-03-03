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
@Table(name = "ASSET")
public class Asset extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @Column(unique = true)
    private String assetType;
    private int totalAmount;

    public void addAssetDetailTypeAmount(final int assetDetailAmount) {
        this.totalAmount += assetDetailAmount;
    }

    public void calculateAmount(final String plusMinusType, final int amount) {
        if (plusMinusType.equals("PLUS") || plusMinusType.equals("plus")) {
            this.totalAmount += amount;
        } else if (plusMinusType.equals("MINUS") || plusMinusType.equals("minus")) {
            this.totalAmount -= amount;
        }
    }

    public void rollbackAmount(final String plusMinusType, final int amount) {
        // ledger를 삭제하는 경우 더했던 값은 빼주고, 뺐던 값은 더해준다.
        if (plusMinusType.equals("PLUS") || plusMinusType.equals("plus")) {
            this.totalAmount -= amount;
        } else if (plusMinusType.equals("MINUS") || plusMinusType.equals("minus")) {
            this.totalAmount += amount;
        }
    }
}
