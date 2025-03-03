package asset.ledger.assetledgerserver.category.infrastructure;

import static asset.ledger.assetledgerserver.category.domain.entity.QUseCategory.useCategory1;

import asset.ledger.assetledgerserver.category.domain.entity.UseCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UseCategoryRepositoryImpl implements UseCategoryRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<UseCategory> getUseCategories(final String userId) {
        return jpqlQueryFactory
                .selectFrom(useCategory1)
                .where(
                        isNotDeleted(),
                        useCategory1.userId.eq(userId)
                )
                .fetch();
    }

    private BooleanExpression isNotDeleted() {
        return useCategory1.isDeleted.eq(Boolean.FALSE);
    }

}
