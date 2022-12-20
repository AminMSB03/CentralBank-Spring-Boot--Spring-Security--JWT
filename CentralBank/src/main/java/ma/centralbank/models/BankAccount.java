package ma.centralbank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.centralbank.enums.AccountStatus;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccount {
    @Id
    private String id;
    @Column(unique = true)
    private Long accountNumber;
    private double balance;
    private LocalDate creationDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "smallint default 0")
    private AccountStatus status;

    @ManyToOne
    private User customer;


}
