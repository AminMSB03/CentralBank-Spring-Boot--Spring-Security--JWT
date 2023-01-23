package ma.centralbank.dto;


import lombok.Data;

@Data
public class AccountOperationRequestDto {
    private Long bankAccountId;
    private Long secondBankAccountId;
    private Double amount;
}
