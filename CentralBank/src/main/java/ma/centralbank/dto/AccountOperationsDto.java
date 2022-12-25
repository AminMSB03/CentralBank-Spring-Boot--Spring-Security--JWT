package ma.centralbank.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccountOperationsDto {
    private int amountWithdrawed;
    private double moneyToTransfer;
    private Long accountNumber;
    private String email;
}
