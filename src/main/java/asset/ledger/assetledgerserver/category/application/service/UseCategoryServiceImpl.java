package asset.ledger.assetledgerserver.category.application.service;

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
        if (requestUseCategoryDto.getUseCategory().equals("입금 이체") || requestUseCategoryDto.getUseCategory().equals("출금 이체")) {
            throw new IllegalArgumentException("해당 카테고리는 생성이 불가능 합니다. 이체 가계부 생성을 이용해주세요");
        }
        UseCategory useCategory = requestUseCategoryDto.toEntity(userId);

        useCategoryRepository.save(useCategory);
    }

}
