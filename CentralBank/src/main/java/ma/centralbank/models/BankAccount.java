package ma.centralbank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.centralbank.enums.AccountStatus;
import ma.centralbank.enums.CardType;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    private CardType accountType;
    private double balance;

    private LocalDate creationDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "smallint default 0")
    private AccountStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User customer;


}
