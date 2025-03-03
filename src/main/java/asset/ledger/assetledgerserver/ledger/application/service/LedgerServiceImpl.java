package asset.ledger.assetledgerserver.ledger.application.service;

import asset.ledger.assetledgerserver.asset.application.service.AssetDetailService;
import asset.ledger.assetledgerserver.asset.application.service.AssetService;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import asset.ledger.assetledgerserver.ledger.domain.dto.RequestLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerListDto;
import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import asset.ledger.assetledgerserver.ledger.domain.repository.LedgerRepository;
import asset.ledger.assetledgerserver.ledger.infrastructure.utils.LocalDataTimeUtils;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;
    private final AssetService assetService;
    private final AssetDetailService assetDetailService;

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

    @Transactional
    @Override
    public void createPlusMinusLedger(final String userId, final RequestLedgerDto requestLedgerDto) {
        Asset asset = assetService.getAsset(userId, requestLedgerDto.getAssetType());
        AssetDetail assetDetail = null;

        if (requestLedgerDto.getAssetType().equals("계좌") || requestLedgerDto.getAssetType().equals("카드")) {
            assetDetail = assetDetailService.getAssetDetail(
                    userId,
                    requestLedgerDto.getAssetType(),
                    requestLedgerDto.getAssetTypeDetail()
            );
        }

        Ledger ledger = requestLedgerDto.toEntity(userId);
        ledgerRepository.save(ledger);

        asset.calculateAmount(requestLedgerDto.getPlusMinusType(), requestLedgerDto.getAmount());

        if (assetDetail != null) {
            assetDetail.calculateAmount(requestLedgerDto.getPlusMinusType(), requestLedgerDto.getAmount());
        }
    }

    @Transactional
    @Override
    public void createTransferLedgers(
            final String userId,
            final RequestLedgerDto requestOutLedgerDto,
            final RequestLedgerDto requestInLedgerDto) {
        Asset asset = assetService.getAsset(userId, requestOutLedgerDto.getAssetType());
        createTransferLedger(userId, asset, requestOutLedgerDto);
        createTransferLedger(userId, asset, requestInLedgerDto);
    }

    private void createTransferLedger(final String userId, final Asset asset, final RequestLedgerDto requestLedgerDto) {
        AssetDetail assetDetail = assetDetailService.getAssetDetail(
                userId,
                requestLedgerDto.getAssetType(),
                requestLedgerDto.getAssetTypeDetail()
        );

        Ledger ledger = requestLedgerDto.toEntity(userId);
        ledgerRepository.save(ledger);

        asset.calculateAmount(requestLedgerDto.getPlusMinusType(), requestLedgerDto.getAmount());
        assetDetail.calculateAmount(requestLedgerDto.getPlusMinusType(), requestLedgerDto.getAmount());
    }

    @Override
    public void updateLedger(final Long id, final RequestLedgerDto requestLedgerDto) {
        Ledger ledger = ledgerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Ledger 입니다."));

        Ledger updateLedger = requestLedgerDto.toEntity(ledger.getUserId());
        ledger.update(updateLedger);

        ledgerRepository.save(ledger);
    }

    @Override
    public void deleteLedger(final Long id) {
        // soft delete
        Ledger ledger = ledgerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Ledger 입니다."));

        Asset asset = assetService.getAsset(ledger.getUserId(), ledger.getAssetType());
        asset.rollbackAmount(ledger.getPlusMinusType().getType(), ledger.getAmount());

        if (ledger.getAssetType().equals("계좌") || ledger.getAssetType().equals("카드")) {
            AssetDetail assetDetail = assetDetailService.getAssetDetail(
                    ledger.getUserId(),
                    ledger.getAssetType(),
                    ledger.getAssetTypeDetail()
            );
            assetDetail.rollbackAmount(ledger.getPlusMinusType().getType(), ledger.getAmount());
        }

        ledger.delete();
        ledgerRepository.save(ledger);
    }

}
