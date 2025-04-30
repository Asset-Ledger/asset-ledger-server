package asset.ledger.assetledgerserver.fcm.application.service;

import java.io.IOException;

public interface FCMService {
    void requestFCMPush(String userId) throws Exception;
    void saveFCMDeviceToken(String userId, final String fcmDeviceToken);
}
