package asset.ledger.assetledgerserver.ledger.application.service;

import asset.ledger.assetledgerserver.ledger.domain.dto.RequestLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerListDto;
import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.domain.repository.LedgerRepository;
import asset.ledger.assetledgerserver.ledger.infrastructure.utils.LocalDataTimeUtils;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    @Override
    public ResponseLedgerListDto searchLedgerByUserIdAndCondition(final SearchLedgerDto searchLedgerDto) {
        List<LocalDateTime> localDateTimes = LocalDataTimeUtils.searchYearMonthWithStartDateToLocalDateTimes(
                searchLedgerDto.getSearchYearMonth(),
                searchLedgerDto.getStartDate()
        );

        List<Ledger> ledgers = ledgerRepository.searchLedgerByUserIdAndCondition(
                searchLedgerDto,
                localDateTimes.get(0), // startDateTime
                localDateTimes.get(1) // endDateTime
        );

        ResponseLedgerListDto responseLedgerListDto = new ResponseLedgerListDto();

        for (Ledger ledger : ledgers) {
            responseLedgerListDto.addResponseLedgerDto(ResponseLedgerDto.fromEntity(ledger));
        }

        return responseLedgerListDto;
    }

    @Override
    public void createLedger(final String userId, final RequestLedgerDto requestLedgerDto) {
        Ledger ledger = requestLedgerDto.toEntity(userId);

        ledgerRepository.save(ledger);
    }

}
