package asset.ledger.assetledgerserver.asset.ui.controller;

import asset.ledger.assetledgerserver.asset.application.service.AssetDetailService;
import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDetailDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetDetailListDto;
import asset.ledger.assetledgerserver.asset.domain.dto.ResponseAssetListDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asset-detail")
@RequiredArgsConstructor
public class AssetDetailController {

    private final AssetDetailService assetDetailService;

    /**
     * @param userId    유저 id
     * @param assetType 자산 종류
     * @return 자산 세부 종류 조회 결과
     */
    @Operation(
            summary = "자산 세부 종류 조회",
            description = "자산 세부 종류를 조회합니다."
    )
    @GetMapping("")
    public ResponseEntity<ResponseAssetDetailListDto> getAssetDetails(
            @RequestHeader("user-id") String userId,
            @RequestParam String assetType
    ) {
        try {
            ResponseAssetDetailListDto responseAssetDetailListDto
                    = assetDetailService.getAssetDetails(userId, assetType);

            return new ResponseEntity<>(responseAssetDetailListDto, HttpStatus.OK);
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
     * @param requestAssetDetailDto 자산 종류
     * @return 자산 세부 종류 조회 결과
     */
    @Operation(
            summary = "자산 세부 종류 생성",
            description = "자산 세부 종류를 생성합니다."
    )
    @PostMapping("")
    public ResponseEntity<Void> createAssetDetail(
            @RequestHeader("user-id") String userId,
            @RequestBody RequestAssetDetailDto requestAssetDetailDto
    ) {
        try {
            assetDetailService.createAssetDetail(userId, requestAssetDetailDto);

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
