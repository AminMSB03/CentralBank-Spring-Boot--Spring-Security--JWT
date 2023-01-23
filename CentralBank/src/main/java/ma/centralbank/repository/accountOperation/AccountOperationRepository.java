package ma.centralbank.repository.accountOperation;

import ma.centralbank.models.AccountOperation;
import ma.centralbank.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

    public List<AccountOperation> findByBankAccount(BankAccount bankAccount);
}
