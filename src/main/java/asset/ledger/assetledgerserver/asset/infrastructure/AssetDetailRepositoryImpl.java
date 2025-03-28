package asset.ledger.assetledgerserver.asset.infrastructure;

import static asset.ledger.assetledgerserver.asset.domain.entity.QAssetDetail.assetDetail;

import asset.ledger.assetledgerserver.asset.domain.entity.AssetDetail;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AssetDetailRepositoryImpl implements AssetDetailRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<AssetDetail> getAssetDetail(final String userId, final String assetType) {
        return jpqlQueryFactory
                .selectFrom(assetDetail)
                .where(
                        isNotDeleted(),
                        assetDetail.userId.eq(userId),
                        assetDetail.assetType.eq(assetType)
                )
                .fetch();
    }

    @Override
    public Boolean existAssetDetail(final String userId, final String assetType, final String assetDetailType) {
        return (jpqlQueryFactory
                .selectFrom(assetDetail)
                .where(
                        isNotDeleted(),
                        assetDetail.userId.eq(userId),
                        assetDetail.assetType.eq(assetType),
                        assetDetail.assetDetailType.eq(assetDetailType)
                ).fetchCount() == 1);
    }

    @Override
    public AssetDetail getAssetDetail(final String userId, final String assetType, final String assetDetailType) {
        return jpqlQueryFactory
                .selectFrom(assetDetail)
                .where(
                        isNotDeleted(),
                        assetDetail.userId.eq(userId),
                        assetDetail.assetType.eq(assetType),
                        assetDetail.assetDetailType.eq(assetDetailType)
                ).fetchOne();
    }

    private BooleanExpression isNotDeleted() {
        return assetDetail.isDeleted.eq(Boolean.FALSE);
    }

}
