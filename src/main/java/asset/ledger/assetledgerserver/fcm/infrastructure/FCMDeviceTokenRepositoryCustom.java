package asset.ledger.assetledgerserver.fcm.infrastructure;

import asset.ledger.assetledgerserver.fcm.domain.entity.FCMDeviceToken;

public interface FCMDeviceTokenRepositoryCustom {
    FCMDeviceToken getFCMDeviceTokenByUserId(final String userId);
}
