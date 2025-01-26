package asset.ledger.assetledgerserver.ledger.domain.entity;

import asset.ledger.assetledgerserver.entity.BaseEntity;
import asset.ledger.assetledgerserver.ledger.domain.converter.PlusMinusTypeConverter;
import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LEDGER")
public class Ledger extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @Convert(converter = PlusMinusTypeConverter.class)
    private PlusMinusType plusMinusType;
    private LocalDateTime editDateTime;
    private String useCategory;
    private String assetType;
    private String assetTypeDetail;
    private String description;
    private int amount;

}
