package asset.ledger.assetledgerserver.asset.infrastructure;

import static asset.ledger.assetledgerserver.asset.domain.entity.QAsset.asset;

import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AssetRepositoryImpl implements AssetRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<Asset> getAssets(final String userId) {
        return jpqlQueryFactory
                .selectFrom(asset)
                .where(
                        isNotDeleted(),
                        asset.userId.eq(userId)
                )
                .fetch();
    }

    @Override
    public Boolean existAsset(final String userId, final String assetType) {
        return (jpqlQueryFactory
                .selectFrom(asset)
                .where(
                        isNotDeleted(),
                        asset.userId.eq(userId),
                        asset.assetType.eq(assetType)
                )
                .fetchCount() == 1);
    }

    @Override
    public Asset getAsset(final String userId, final String assetType) {
        return jpqlQueryFactory
                .selectFrom(asset)
                .where(
                        isNotDeleted(),
                        asset.userId.eq(userId),
                        asset.assetType.eq(assetType)
                )
                .fetchOne();
    }

    private BooleanExpression isNotDeleted() {
        return asset.isDeleted.eq(Boolean.FALSE);
    }


}
