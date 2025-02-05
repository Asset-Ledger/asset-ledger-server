package asset.ledger.assetledgerserver.ledger.ui.controller;

import asset.ledger.assetledgerserver.ledger.application.service.LedgerService;
import asset.ledger.assetledgerserver.ledger.domain.dto.RequestLedgerDto;
import asset.ledger.assetledgerserver.ledger.domain.dto.ResponseLedgerListDto;
import asset.ledger.assetledgerserver.ledger.ui.dto.SearchLedgerDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    /**
     * @param userId    유저 id
     * @param searchYearMonth   2025년 1월
     * @param startDate 급여 날짜를 선택하지 않으면 기본 1, 선택하면 급여일
     * @param plusMinusType
     * @param useCategory
     * @param assetType
     * @param assetTypeDetail
     * @return 가계부 조회 결과
     */
    @Operation(
            summary = "가계부 조회",
            description = "가계부를 조회합니다."
    )
    @GetMapping("")
    public ResponseEntity<ResponseLedgerListDto> getLedgers(
            @RequestHeader("user-id") String userId,
            @RequestParam String searchYearMonth,
            @RequestParam String startDate,
            @RequestParam String plusMinusType,
            @RequestParam String useCategory,
            @RequestParam String assetType,
            @RequestParam String assetTypeDetail
    ) {
        SearchLedgerDto searchLedgerDto = new SearchLedgerDto(
                userId,
                searchYearMonth,
                startDate,
                plusMinusType,
                useCategory,
                assetType,
                assetTypeDetail
        );

        try {
            ResponseLedgerListDto responseLedgerListDto
                    = ledgerService.searchLedgerByUserIdAndCondition(searchLedgerDto);

            return new ResponseEntity<>(responseLedgerListDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");
            System.out.println(e);
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param userId    유저 id
     * @param requestLedgerDto "plusMinusType": "PLUS",
     *                         "editDate": "2025/01/24 (금)",
     *                         "editTime": "오후 4:40",
     *                         "useCategory": "교통비",
     *                         "assetType": "계좌",
     *                         "assetTypeDetail": "하나은행(급여통장)",
     *                         "description": "교통비",
     *                         "amount": 10000
     * @return void
     *
     */
    @Operation(
            summary = "가계부 작성",
            description = "가계부를 작성합니다."
    )
    @PostMapping("")
    public ResponseEntity<Void> createLedger(
            @RequestHeader("user-id") String userId,
            @RequestBody RequestLedgerDto requestLedgerDto
    ) {

        try {
            ledgerService.createLedger(userId, requestLedgerDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id    가계부 id
     * @param requestLedgerDto "plusMinusType": "PLUS",
     *                         "editDate": "2025/01/24 (금)",
     *                         "editTime": "오후 4:40",
     *                         "useCategory": "교통비",
     *                         "assetType": "계좌",
     *                         "assetTypeDetail": "하나은행(급여통장)",
     *                         "description": "교통비",
     *                         "amount": 10000
     * @return void
     *
     */
    @Operation(
            summary = "가계부 수정",
            description = "가계부를 수정합니다."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLedger(
            @PathVariable("id") Long id,
            @RequestBody RequestLedgerDto requestLedgerDto
    ) {

        try {
            ledgerService.updateLedger(id, requestLedgerDto);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id    가계부 id
     * @return void
     *
     */
    @Operation(
            summary = "가계부 삭제",
            description = "가계부를 삭제합니다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLedger(@PathVariable("id") Long id) {

        try {
            ledgerService.deleteLedger(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
