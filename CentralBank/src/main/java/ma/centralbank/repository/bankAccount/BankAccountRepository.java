package ma.centralbank.repository.bankAccount;

import ma.centralbank.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {



}
