package ma.centralbank.dto;



import lombok.Data;
import ma.centralbank.enums.OperationType;
import ma.centralbank.models.BankAccount;

import java.time.LocalDate;

@Data
public class AccountOperationResponseDto {
    private Long id;
    private LocalDate operationDate;
    private double amount;
    private OperationType type;
    private BankAccount secondBankAccount;

}
