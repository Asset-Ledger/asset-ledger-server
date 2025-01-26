package asset.ledger.assetledgerserver.ledger.domain.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PlusMinusType {
    PLUS("PLUS"),
    MINUS("MINUS");

    private String type;

    PlusMinusType(final String type) {
        this.type = type;
    }

    public static PlusMinusType fromEnum(String plusMinusType) {
        return Arrays.stream(PlusMinusType.values())
                .filter(type -> type.getType().equals(plusMinusType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 PLUS / MINUS 타입 입니다."));
    }

}
