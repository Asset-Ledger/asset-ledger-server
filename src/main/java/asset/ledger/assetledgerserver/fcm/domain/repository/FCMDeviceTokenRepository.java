package asset.ledger.assetledgerserver.fcm.domain.repository;

import asset.ledger.assetledgerserver.fcm.domain.entity.FCMDeviceToken;
import asset.ledger.assetledgerserver.fcm.infrastructure.FCMDeviceTokenRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMDeviceTokenRepository extends JpaRepository<FCMDeviceToken, Long>, FCMDeviceTokenRepositoryCustom {

}
