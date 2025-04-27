package asset.ledger.assetledgerserver.fcm.infrastructure;

import static asset.ledger.assetledgerserver.fcm.domain.entity.QFCMDeviceToken.fCMDeviceToken;

import asset.ledger.assetledgerserver.fcm.domain.entity.FCMDeviceToken;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FCMDeviceTokenRepositoryCustomImpl implements FCMDeviceTokenRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public FCMDeviceToken getFCMDeviceTokenByUserId(final String userId) {
        return jpqlQueryFactory
                .selectFrom(fCMDeviceToken)
                .where(
                        isNotDeleted(),
                        fCMDeviceToken.userId.eq(userId)
                )
                .fetchOne();
    }

    private BooleanExpression isNotDeleted() {
        return fCMDeviceToken.isDeleted.eq(Boolean.FALSE);
    }

}
