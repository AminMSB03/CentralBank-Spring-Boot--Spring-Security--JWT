package ma.centralbank.services.user;

import ma.centralbank.models.AccountOperation;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.CardLimit;
import ma.centralbank.models.User;

import java.util.List;

public interface UserService {

    User addNewUser(User user);

    User loadUserByEmail(String email);

    List<User> listUsers();

    BankAccount addNewBankAccount(BankAccount bankAccount);
    List<BankAccount> getAccountTypes();

    CardLimit AddNewCardLimits(CardLimit cardLimit);

    User getAccountPhoneNumberByPhoneNumber(Long phoneNumber);

}
