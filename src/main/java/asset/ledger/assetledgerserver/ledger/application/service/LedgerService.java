package asset.ledger.assetledgerserver.ledger.application.service;

import asset.ledger.assetledgerserver.ledger.domain.dto.RequestLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerListDto;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;

public interface LedgerService {
    ResponseLedgerListDto searchLedgerByUserIdAndCondition(SearchLedgerDto requestLedgerDto);
    void createLedger(String userId, RequestLedgerDto requestLedgerDto);

}
