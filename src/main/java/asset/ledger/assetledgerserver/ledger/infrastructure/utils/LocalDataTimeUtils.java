package asset.ledger.assetledgerserver.ledger.infrastructure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class LocalDataTimeUtils {
    public static List<LocalDateTime> searchYearMonthWithStartDateToLocalDateTimes(
            String searchYearMonth,
            String startDate
    ) {
        String[] searchYearMonthSplit = searchYearMonth.split("년 |월");

        if (searchYearMonthSplit.length != 2) {
            throw new IllegalArgumentException("잘못된 년, 월 형식 입니다.");
        }

        int year = Integer.parseInt(searchYearMonthSplit[0]);  // 연도
        int month = Integer.parseInt(searchYearMonthSplit[1]); // 월

        // fd는 시작일 (String -> int로 변환)
        int startDay = Integer.parseInt(startDate);

        // 시작 날짜 계산 (YYYY-MM-DD 00:00:00)
        LocalDate startLocalDate = LocalDate.of(year, month, startDay);
        LocalDateTime startLocalDateTime = startLocalDate.atStartOfDay();  // 시작일의 00:00

        // 다음 달의 fd 값 하루 전 23:59로 종료일을 계산
        LocalDate nextMonthStartLocalDate = startLocalDate.plusMonths(1);
        LocalDate endLocalDate = nextMonthStartLocalDate.minusDays(1);
        LocalDateTime endLocalDateTime = endLocalDate.atTime(23, 59, 59); // 마지막 날 23:59

        // DateRange 객체를 반환
        return Arrays.asList(startLocalDateTime, endLocalDateTime);
    }

}
