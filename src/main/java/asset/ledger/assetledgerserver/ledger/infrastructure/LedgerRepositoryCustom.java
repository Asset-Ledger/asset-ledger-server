package asset.ledger.assetledgerserver.ledger.infrastructure;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;
import java.time.LocalDateTime;
import java.util.List;

public interface LedgerRepositoryCustom {
    List<Ledger> searchLedgerByUserIdAndCondition(
            SearchLedgerDto searchLedgerDto,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );

}
