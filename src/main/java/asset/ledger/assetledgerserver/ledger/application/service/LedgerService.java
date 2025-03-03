package asset.ledger.assetledgerserver.ledger.application.service;

import asset.ledger.assetledgerserver.ledger.domain.dto.RequestLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerListDto;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;

public interface LedgerService {
    ResponseLedgerListDto searchLedgerByUserIdAndCondition(final SearchLedgerDto requestLedgerDto);
    void createPlusMinusLedger(final String userId, final RequestLedgerDto requestLedgerDto);
    void createTransferLedgers(
            final String userId,
            final RequestLedgerDto requestOutLedgerDto,
            final RequestLedgerDto requestInLedgerDto
    );
    void updateLedger(final Long id, final RequestLedgerDto requestLedgerDto);
    void deleteLedger(final Long id);

;}
