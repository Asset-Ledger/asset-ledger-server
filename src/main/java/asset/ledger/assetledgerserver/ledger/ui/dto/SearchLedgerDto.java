package asset.ledger.assetledgerserver.ledger.ui.dto;

import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@AllArgsConstructor
public class SearchLedgerDto {
    private String userId;
    private String searchYearMonth;
    private String startDate;
    private String plusMinusType;
    private String userCategory;
    private String assetType;
    private String assetTypeDetail;

    public PlusMinusType toPlusMinusType() {
        return Arrays.stream(PlusMinusType.values())
                .filter(type -> type.getType().equals(this.plusMinusType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 PLUS / MINUS 타입 입니다."));
    }
}
