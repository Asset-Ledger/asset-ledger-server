package asset.ledger.assetledgerserver.category.infrastructure;

import asset.ledger.assetledgerserver.category.domain.entity.UseCategory;
import java.util.List;

public interface UseCategoryRepositoryCustom {
    List<UseCategory> getUseCategories(final String userId);

}
