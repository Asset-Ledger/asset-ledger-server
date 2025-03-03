package asset.ledger.assetledgerserver.category.ui.controller;

import asset.ledger.assetledgerserver.category.application.service.UseCategoryService;
import asset.ledger.assetledgerserver.category.domain.dto.RequestUseCategoryDto;
import asset.ledger.assetledgerserver.category.domain.dto.ResponseUseCategoryListDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/use-category")
@RequiredArgsConstructor
public class UseCategoryController {

    private final UseCategoryService useCategoryService;

    /**
     * @param userId    유저 id
     * @return 사용 분류 조회 결과
     */
    @Operation(
            summary = "사용 분류 조회",
            description = "사용 분류를 조회합니다."
    )
    @GetMapping("")
    public ResponseEntity<ResponseUseCategoryListDto> getCategories(
            @RequestHeader("user-id") String userId
    ) {
        try {
            ResponseUseCategoryListDto responseUseCategoryListDto
                    = useCategoryService.getUseCategories(userId);

            return new ResponseEntity<>(responseUseCategoryListDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param userId    유저 id
     * @param requestUseCategoryDto 사용 분류
     * @return 사용 분류 조회 결과
     */
    @Operation(
            summary = "사용 분류 생성",
            description = "사용 분류를 생성합니다."
    )
    @PostMapping("")
    public ResponseEntity<Void> createCategory(
            @RequestHeader("user-id") String userId,
            @RequestBody RequestUseCategoryDto requestUseCategoryDto
    ) {
        try {
            useCategoryService.createUseCategory(userId, requestUseCategoryDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
