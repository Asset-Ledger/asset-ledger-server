package asset.ledger.assetledgerserver.fcm.domain.entity;

import asset.ledger.assetledgerserver.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FCM_DEVICE_TOKEN")
public class FCMDeviceToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userId;
    private String fcmDeviceToken;

    public void updateFCMDeviceToken(final String newToken) {
        this.fcmDeviceToken = newToken;
    }
}
