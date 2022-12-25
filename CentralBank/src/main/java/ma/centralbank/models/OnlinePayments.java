package ma.centralbank.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlinePayments {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payment_id;
    private Long contractNumber;
    private Long phoneNumber;
    private String operationType;
    private double amoutToPay;
    @OneToMany(mappedBy = "payments",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BankAccount> bankPaymentUser;


}
