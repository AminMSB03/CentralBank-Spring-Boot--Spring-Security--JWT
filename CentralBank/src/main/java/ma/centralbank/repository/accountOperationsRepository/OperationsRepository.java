package ma.centralbank.repository.accountOperationsRepository;

import ma.centralbank.models.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsRepository  extends JpaRepository<AccountOperation,Integer> {
}
