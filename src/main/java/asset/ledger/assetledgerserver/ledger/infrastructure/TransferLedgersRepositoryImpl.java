package asset.ledger.assetledgerserver.ledger.infrastructure;

import static asset.ledger.assetledgerserver.ledger.domain.entity.QTransferLedgers.transferLedgers;

import asset.ledger.assetledgerserver.ledger.domain.entity.TransferLedgers;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransferLedgersRepositoryImpl implements TransferLedgersRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public TransferLedgers getAssociatedLedgerId(final Long ledgerId) {
        return jpqlQueryFactory
                .selectFrom(transferLedgers)
                .where(
                        isNotDeleted(),
                        (transferLedgers.outLedgerId.eq(ledgerId)
                                .or(transferLedgers.inLedgerId.eq(ledgerId)))
                )
                .fetchOne();
    }

    private BooleanExpression isNotDeleted() {
        return transferLedgers.isDeleted.eq(Boolean.FALSE);
    }

}
