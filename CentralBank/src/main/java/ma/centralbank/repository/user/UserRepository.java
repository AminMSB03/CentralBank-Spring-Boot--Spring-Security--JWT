package ma.centralbank.repository.user;

import ma.centralbank.models.OnlinePayments;
import ma.centralbank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findAccountPhoneNumberByPhoneNumber(Long phoneNumber);

}
