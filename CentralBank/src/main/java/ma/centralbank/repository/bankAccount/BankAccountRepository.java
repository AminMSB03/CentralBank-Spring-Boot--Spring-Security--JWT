package ma.centralbank.repository.bankAccount;

import ma.centralbank.models.BankAccount;
import ma.centralbank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    @Query(value = "SELECT * FROM bank_account where customer_id = :id", nativeQuery = true)
    BankAccount findByUser_Id(@Param("id") Long id);

}
