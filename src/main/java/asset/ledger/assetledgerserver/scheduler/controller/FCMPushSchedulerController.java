package asset.ledger.assetledgerserver.scheduler.controller;

import asset.ledger.assetledgerserver.scheduler.service.FCMPushSchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class FCMPushSchedulerController {

    private final FCMPushSchedulerService fcmPushSchedulerService;

    @Operation(
            summary = "FCM push 스케줄러를 생성",
            description = "FCM push 스케줄러를 생성합니다."
    )
    @GetMapping("/")
    public void createFCMPushScheduler(final String userId) {
        fcmPushSchedulerService.createFCMPushScheduler(userId);

    }

    @Operation(
            summary = "동작중인 스케줄러 Job 확인",
            description = "동작중인 스케줄러 Job을 확인합니다."
    )
    @GetMapping("/working")
    public void getWorkingFCMPushSchedulerJobs() {
        fcmPushSchedulerService.getWorkingFCMPushSchedulerJobs();

    }

}
