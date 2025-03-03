package asset.ledger.assetledgerserver.category.application.service;

import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;
import asset.ledger.assetledgerserver.asset.domain.entity.Asset;
import asset.ledger.assetledgerserver.category.domain.dto.RequestUseCategoryDto;
import asset.ledger.assetledgerserver.category.domain.dto.ResponseUseCategoryDto;
import asset.ledger.assetledgerserver.category.domain.dto.ResponseUseCategoryListDto;
import asset.ledger.assetledgerserver.category.domain.entity.UseCategory;
import asset.ledger.assetledgerserver.category.domain.repository.UseCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UseCategoryServiceImpl implements UseCategoryService {

    private final UseCategoryRepository useCategoryRepository;

    @Override
    public ResponseUseCategoryListDto getUseCategories(final String userId) {
        List<UseCategory> useCategories = useCategoryRepository.getUseCategories(userId);

        ResponseUseCategoryListDto responseUseCategoryListDto = new ResponseUseCategoryListDto();

        for (UseCategory useCategory : useCategories) {
            responseUseCategoryListDto.addResponseUseCategoryDto(ResponseUseCategoryDto.fromEntity(useCategory));
        }

        return responseUseCategoryListDto;
    }

    @Override
    public void createUseCategory(final String userId, final RequestUseCategoryDto requestUseCategoryDto) {
        UseCategory useCategory = requestUseCategoryDto.toEntity(userId);

        useCategoryRepository.save(useCategory);
    }

}
