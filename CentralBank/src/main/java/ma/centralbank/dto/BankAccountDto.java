package ma.centralbank.dto;

import lombok.Data;
import ma.centralbank.enums.AccountStatus;

import java.time.LocalDate;

@Data
public class BankAccountDto {

    private Long accountNumber;
    private double balance;
    private LocalDate creationDate;
    private AccountStatus status;

    private UserDto userDto;

}
