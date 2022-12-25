package ma.centralbank.repository.bankAccount;

import ma.centralbank.models.BankAccount;
import ma.centralbank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    BankAccount findByAccountNumber(Long accountNumber);


}
