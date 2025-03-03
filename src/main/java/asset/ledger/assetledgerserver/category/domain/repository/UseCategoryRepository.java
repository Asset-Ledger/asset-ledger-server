package asset.ledger.assetledgerserver.category.domain.repository;

import asset.ledger.assetledgerserver.category.domain.entity.UseCategory;
import asset.ledger.assetledgerserver.category.infrastructure.UseCategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseCategoryRepository extends JpaRepository<UseCategory, Long>, UseCategoryRepositoryCustom {

}
