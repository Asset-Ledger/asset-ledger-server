package asset.ledger.assetledgerserver.ledger.domain.dto;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RequestTransferLedgerDto {
    private RequestLedgerDto requestOutLedgerDto;
    private RequestLedgerDto requestInLedgerDto;
}
