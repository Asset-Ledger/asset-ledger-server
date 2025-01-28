package asset.ledger.assetledgerserver.asset.ui.controller;

import asset.ledger.assetledgerserver.asset.application.service.AssetService;
import asset.ledger.assetledgerserver.asset.domain.dto.RequestAssetDto;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    /**
     * @param userId    유저 id
     * @return 자산 종류 조회 결과
     */
    @Operation(
            summary = "자산 종류 조회",
            description = "자산 종류를 조회합니다."
    )
    @GetMapping("")
    public ResponseEntity<ResponseAssetListDto> getAssets(
            @RequestHeader("user-id") String userId
    ) {
        try {
            ResponseAssetListDto responseAssetListDto
                    = assetService.getAssets(userId);

            return new ResponseEntity<>(responseAssetListDto, HttpStatus.OK);
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
     * @param requestAssetDto
     * @return 자산 종류 조회 결과
     */
    @Operation(
            summary = "자산 종류 생성",
            description = "자산 종류를 생성합니다."
    )
    @PostMapping("")
    public ResponseEntity<Void> createAsset(
            @RequestHeader("user-id") String userId,
            @RequestBody RequestAssetDto requestAssetDto
    ) {
        try {
            assetService.createAsset(userId, requestAssetDto);

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
