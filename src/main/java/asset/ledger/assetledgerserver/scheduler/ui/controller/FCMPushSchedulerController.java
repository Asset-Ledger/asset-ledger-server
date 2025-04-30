package asset.ledger.assetledgerserver.scheduler.ui.controller;

import asset.ledger.assetledgerserver.scheduler.application.service.FCMPushSchedulerService;
import asset.ledger.assetledgerserver.scheduler.domain.RequestFCMPushSchedulerDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class FCMPushSchedulerController {

    private final FCMPushSchedulerService fcmPushSchedulerService;

    @Operation(
            summary = "FCM push 스케줄러를 생성",
            description = "FCM push 스케줄러를 생성합니다."
    )
    @PostMapping("")
    public ResponseEntity<Void> createFCMPushScheduler(
            @RequestHeader("user-id") String userId,
            @RequestBody RequestFCMPushSchedulerDto requestFCMPushSchedulerDto
    ) {
        try {
            return fcmPushSchedulerService.createFCMPushScheduler(userId, requestFCMPushSchedulerDto);
        } catch (Exception e) {
            log.error("FCMPushSchedulerController createFCMPushScheduler error 발생 errorMessage={}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "동작중인 스케줄러 Job 확인",
            description = "동작중인 스케줄러 Job을 확인합니다."
    )
    @GetMapping("/working")
    public void getWorkingFCMPushSchedulerJobs() {
        try {
            fcmPushSchedulerService.getWorkingFCMPushSchedulerJobs();
        } catch (Exception e) {
            log.error("FCMPushSchedulerController getWorkingFCMPushSchedulerJobs error 발생 errorMessage={}", e.getMessage());
        }
    }

}
