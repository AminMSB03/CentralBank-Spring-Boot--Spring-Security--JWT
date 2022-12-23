package ma.centralbank.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import ma.centralbank.enums.AccountStatus;

import java.time.LocalDate;

@Entity @Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long accountNumber;
    private double balance;
    private LocalDate creationDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int default '0'")
    private AccountStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    private User customer;

}
