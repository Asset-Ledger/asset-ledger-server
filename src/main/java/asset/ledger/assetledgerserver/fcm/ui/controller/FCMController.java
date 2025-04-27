package asset.ledger.assetledgerserver.fcm.ui.controller;

import asset.ledger.assetledgerserver.fcm.application.service.FCMService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FCMController {

    private final FCMService fcmService;

    /**
     * @param userId    유저 id
     * @param fcmDeviceToken   fcmDeviceToken
     * @return void
     */
    @Operation(
            summary = "FCM Device token 저장",
            description = "FCM Device token을 저장합니다."
    )
    @PostMapping("")
    public void saveFCMDeviceToken(
            @RequestHeader("user-id") String userId,
            @RequestBody String fcmDeviceToken
    ) {
        try {
            fcmService.saveFCMDeviceToken(userId, fcmDeviceToken);

        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다");
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
}
