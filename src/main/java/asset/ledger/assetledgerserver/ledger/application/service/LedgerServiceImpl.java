package asset.ledger.assetledgerserver.ledger.application.service;

import asset.ledger.assetledgerserver.asset.application.service.AssetDetailService;
import asset.ledger.assetledgerserver.asset.application.service.AssetService;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import asset.ledger.assetledgerserver.ledger.domain.dto.RequestLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerListDto;
import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.domain.entity.TransferLedgers;
import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import asset.ledger.assetledgerserver.ledger.domain.repository.LedgerRepository;
import asset.ledger.assetledgerserver.ledger.domain.repository.TransferLedgersRepository;
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
    private final TransferLedgersRepository transferLedgersRepository;

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
        Long outLedgerId = createTransferLedger(userId, asset, requestOutLedgerDto);
        Long inLedgerId = createTransferLedger(userId, asset, requestInLedgerDto);

        TransferLedgers transferLedgers = TransferLedgers
                .builder()
                .outLedgerId(outLedgerId)
                .inLedgerId(inLedgerId)
                .build();

        transferLedgersRepository.save(transferLedgers);
    }

    private Long createTransferLedger(final String userId, final Asset asset, final RequestLedgerDto requestLedgerDto) {
        AssetDetail assetDetail = assetDetailService.getAssetDetail(
                userId,
                requestLedgerDto.getAssetType(),
                requestLedgerDto.getAssetTypeDetail()
        );

        Ledger ledger = requestLedgerDto.toEntity(userId);
        Ledger savedLedger = ledgerRepository.save(ledger);

        asset.calculateAmount(requestLedgerDto.getPlusMinusType(), requestLedgerDto.getAmount());
        assetDetail.calculateAmount(requestLedgerDto.getPlusMinusType(), requestLedgerDto.getAmount());

        return savedLedger.getId();
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

        if (!ledger.getUseCategory().equals("입금 이체") && !ledger.getUseCategory().equals("출금 이체")) {
            deletePlusMinusLedger(ledger);
        }
        else {
            deleteTransferLedgers(ledger);
        }
    }

    private void deletePlusMinusLedger(final Ledger ledger) {
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

    private void deleteTransferLedgers(final Ledger ledger) {
        TransferLedgers transferLedgers = transferLedgersRepository.getAssociatedLedgerId(ledger.getId());
        Long outLedgerId = transferLedgers.getOutLedgerId();
        Long inLedgerId = transferLedgers.getInLedgerId();

        Ledger outLedger = null;
        Ledger inLedger = null;

        if (ledger.getId().equals(outLedgerId)) {
            outLedger = ledger;
            inLedger = ledgerRepository.findById(inLedgerId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Ledger 입니다."));
        }
        else {
            inLedger = ledger;
            outLedger = ledgerRepository.findById(inLedgerId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Ledger 입니다."));
        }

        deletePlusMinusLedger(outLedger);
        deletePlusMinusLedger(inLedger);
        transferLedgers.delete();
        transferLedgersRepository.save(transferLedgers);
    }

}
