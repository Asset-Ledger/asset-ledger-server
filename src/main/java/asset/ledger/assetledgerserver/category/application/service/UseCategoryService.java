package asset.ledger.assetledgerserver.category.application.service;

import asset.ledger.assetledgerserver.category.domain.dto.RequestUseCategoryDto;
import asset.ledger.assetledgerserver.category.domain.dto.ResponseUseCategoryListDto;

public interface UseCategoryService {
    ResponseUseCategoryListDto getUseCategories(final String userId);

    void createUseCategory(final String userId, final RequestUseCategoryDto requestUseCategoryDto);

}
