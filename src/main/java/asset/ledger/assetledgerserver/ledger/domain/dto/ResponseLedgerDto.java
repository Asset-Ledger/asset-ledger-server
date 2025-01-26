package asset.ledger.assetledgerserver.ledger.domain.dto;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseLedgerDto {
    private String plusMinusType;
    private String editDate;
    private String editTime;
    private String useCategory;
    private String assetType;
    private String assetTypeDetail;
    private String description;
    private int amount;

    public static ResponseLedgerDto fromEntity(Ledger ledger) {
        return ResponseLedgerDto
                .builder()
                .plusMinusType(ledger.getPlusMinusType().getType())
                .editDate(toEditDate(ledger.getEditDateTime()))
                .editTime(toEditTime(ledger.getEditDateTime()))
                .useCategory(ledger.getUseCategory())
                .assetType(ledger.getAssetType())
                .assetTypeDetail(ledger.getAssetTypeDetail())
                .description(ledger.getDescription())
                .amount(ledger.getAmount())
                .build();
    }

    private static String toEditDate(LocalDateTime editDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return editDateTime.format(dateFormatter);
    }

    private static String toEditTime(LocalDateTime editDateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm:ss");
        return editDateTime.format(timeFormatter);
    }

}
