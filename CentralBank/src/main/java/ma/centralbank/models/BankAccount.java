package ma.centralbank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.centralbank.enums.AccountStatus;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date creationDate;
    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status;

    @ManyToOne
    private User customer;


}
