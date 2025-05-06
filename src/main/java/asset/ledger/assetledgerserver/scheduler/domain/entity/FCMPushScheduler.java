package asset.ledger.assetledgerserver.scheduler.domain.entity;

import asset.ledger.assetledgerserver.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FCM_PUSH_SCHEDULER")
public class FCMPushScheduler extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String days;
    private String time;
    private String jobKey;
    private String triggerKey;
    private Boolean status;

    public void turnOnOff() {
        this.status = !this.status;
    }

    public List<String> getListDays() {
        return Arrays.asList(this.days.split(","));
    }
}
