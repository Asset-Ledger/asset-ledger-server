package asset.ledger.assetledgerserver.scheduler.infrastructure;

import static asset.ledger.assetledgerserver.scheduler.domain.entity.QFCMPushScheduler.fCMPushScheduler;

import asset.ledger.assetledgerserver.scheduler.domain.entity.FCMPushScheduler;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FCMPushSchedulerRepositoryCustomImpl implements FCMPushSchedulerRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<FCMPushScheduler> findFCMPushSchedulerByUserId(final String userId) {
        return jpqlQueryFactory
                .selectFrom(fCMPushScheduler)
                .where(
                        isNotDeleted(),
                        fCMPushScheduler.userId.eq(userId)
                )
                .fetch();
    }

    @Override
    public FCMPushScheduler findFCMPushSchedulerById(final Long id, final String userId) {
        return jpqlQueryFactory
                .selectFrom(fCMPushScheduler)
                .where(
                        isNotDeleted(),
                        fCMPushScheduler.id.eq(id),
                        fCMPushScheduler.userId.eq(userId)
                )
                .fetchOne();
    }

    private BooleanExpression isNotDeleted() {
        return fCMPushScheduler.isDeleted.eq(Boolean.FALSE);
    }
}
