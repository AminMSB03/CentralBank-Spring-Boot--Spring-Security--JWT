package ma.centralbank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.centralbank.enums.OperationType;

import java.time.LocalDate;

@Entity @Builder
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate operationDate;
    private double amount;
    @Enumerated(EnumType.ORDINAL)
    private OperationType type;

    @ManyToOne
    private BankAccount bankAccount;
    @ManyToOne
    private BankAccount secondBankAccount;

}
