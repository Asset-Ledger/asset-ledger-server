package asset.ledger.assetledgerserver.ledger.domain.dto;

import asset.ledger.assetledgerserver.ledger.domain.entity.Ledger;
import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RequestLedgerDto {
    private String plusMinusType;
    private String editDate;
    private String editTime;
    private String useCategory;
    private String assetType;
    private String assetTypeDetail;
    private String description;
    private int amount;

    public Ledger toEntity(String userId) {
        return Ledger
                .builder()
                .userId(userId)
                .plusMinusType(toPlusMinusType(this.plusMinusType))
                .editDateTime(toLocalDateTime(this.editDate, this.editTime))
                .useCategory(this.useCategory)
                .assetType(this.assetType)
                .assetTypeDetail(this.assetTypeDetail)
                .description(this.description)
                .amount(this.amount)
                .build();
    }

    private PlusMinusType toPlusMinusType(String plusMinusType) {
        return PlusMinusType.fromEnum(plusMinusType);
    }

    private LocalDateTime toLocalDateTime(String editDate, String editTime) {
        String[] editDateAndDay = editDate.strip().split(" ");

        if (editDateAndDay.length != 2) {
            throw new IllegalArgumentException("잘못된 날짜 및 요일 형식입니다.");
        }

        String[] editYearMonthDate = editDateAndDay[0].strip().split("/");

        if (editYearMonthDate.length != 3) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }

        int year = Integer.parseInt(editYearMonthDate[0]);
        int month = Integer.parseInt(editYearMonthDate[1]);
        int date = Integer.parseInt(editYearMonthDate[2]);

        String[] editAPTime = editTime.strip().split(" ");

        if (editAPTime.length != 2) {
            throw new IllegalArgumentException("잘못된 오전/오후 및시간 형식입니다.");
        }

        String amPm = editAPTime[0];
        String[] editHourMinute = editAPTime[1].strip().split(":");

        if (editHourMinute.length != 2) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다.");
        }

        int hour = Integer.parseInt(editHourMinute[0]);
        int minute = Integer.parseInt(editHourMinute[1]);

        if ("오후".equals(amPm)) {
            hour += 12;
        }

        return LocalDateTime.of(year, month, date, hour, minute);
    }

}
