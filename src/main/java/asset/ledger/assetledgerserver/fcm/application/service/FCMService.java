package asset.ledger.assetledgerserver.fcm.application.service;

import java.io.IOException;

public interface FCMService {
    void requestFCMPush(String fcmDeviceToken, String title, String body) throws IOException;
    void saveFCMDeviceToken(String userId, final String fcmDeviceToken);
}
