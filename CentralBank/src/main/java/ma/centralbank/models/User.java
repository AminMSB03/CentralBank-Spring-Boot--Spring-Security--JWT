package ma.centralbank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "_user")
@Table(name="_user")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    private String email;
    private Long phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role;
    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts;

}
