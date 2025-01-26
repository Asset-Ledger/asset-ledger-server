package asset.ledger.assetledgerserver.ledger.domain.converter;

import asset.ledger.assetledgerserver.ledger.domain.enums.PlusMinusType;
import jakarta.persistence.AttributeConverter;

public class PlusMinusTypeConverter implements AttributeConverter<PlusMinusType, String> {
    @Override
    public String convertToDatabaseColumn(final PlusMinusType plusMinusType) {
        if (plusMinusType == null) {
            return null;
        }
        return plusMinusType.getType();
    }

    @Override
    public PlusMinusType convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return PlusMinusType.fromEnum(dbData);
        } catch (IllegalArgumentException e) {
            System.out.println("존재하지 않는 PLUS / MINUS 타입 입니다.");
            throw e;
        }
    }

}
