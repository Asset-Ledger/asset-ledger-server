package asset.ledger.assetledgerserver.ledger.infrastructure;

import static asset.ledger.assetledgerserver.ledger.domain.entity.QLedger.ledger;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LedgerRepositoryImpl implements LedgerRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<Ledger> searchLedgerByUserIdAndCondition(
            SearchLedgerDto searchLedgerDto,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime

    ) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder
                .and(isNotDeleted())
                .and(ledger.userId.eq(searchLedgerDto.getUserId()))
                .and(ledger.editDateTime.between(startDateTime, endDateTime));

        if (!"전체".equals(searchLedgerDto.getPlusMinusType())) {
            booleanBuilder
                    .and(ledger.plusMinusType.eq(searchLedgerDto.toPlusMinusType()));
        }

        if (!"전체".equals(searchLedgerDto.getUserCategory())) {
            booleanBuilder
                    .and(ledger.useCategory.eq(searchLedgerDto.getUserCategory()));
        }

        if ("계좌".equals(searchLedgerDto.getAssetType()) || "카드".equals(searchLedgerDto.getAssetType())) {
            booleanBuilder
                    .and(ledger.assetType.eq(searchLedgerDto.getAssetType()));

            if (!"전체".equals(searchLedgerDto.getAssetTypeDetail())) {
                booleanBuilder
                        .and(ledger.assetTypeDetail.eq(searchLedgerDto.getAssetTypeDetail()));
            }
        } else if ("현금".equals(searchLedgerDto.getAssetType())) {
            booleanBuilder
                    .and(ledger.assetType.eq(searchLedgerDto.getAssetType()));
        }

        return jpqlQueryFactory
                .selectFrom(ledger)
                .where(booleanBuilder)
                .orderBy(ledger.editDateTime.desc(), ledger.createdAt.desc())
                .fetch();
    }

    private BooleanExpression isNotDeleted() {
        return ledger.isDeleted.eq(Boolean.FALSE);
    }

}
