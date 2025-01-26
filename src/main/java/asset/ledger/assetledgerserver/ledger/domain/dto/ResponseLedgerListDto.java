package asset.ledger.assetledgerserver.ledger.domain.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ResponseLedgerListDto {
    private List<ResponseLedgerDto> ledgerDtos;

    public ResponseLedgerListDto() {
        this.ledgerDtos = new ArrayList<>();
    }

    public void addResponseLedgerDto(ResponseLedgerDto responseLedgerDto) {
        this.ledgerDtos.add(responseLedgerDto);
    }
}
