package ma.centralbank.services.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.centralbank.models.AccountOperation;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.CardLimit;
import ma.centralbank.models.User;
import ma.centralbank.repository.Card.CardRepository;
import ma.centralbank.repository.accountOperationsRepository.OperationsRepository;
import ma.centralbank.repository.bankAccount.BankAccountRepository;
import ma.centralbank.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final OperationsRepository operationsRepository;
    private final CardRepository cardRepository;


    @Override
    public User addNewUser(User user) {
        String pw = user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));
        return this.userRepository.save(user);
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> listUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public BankAccount addNewBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getAccountTypes() {
        return bankAccountRepository.findAll();
    }

    @Override
    public AccountOperation userAccountOperations(AccountOperation accountOperation) {
        return operationsRepository.save(accountOperation);
    }

    @Override
    public List<CardLimit> getCardLimits() {
        return cardRepository.findAll();
    }

    @Override
    public CardLimit AddNewCardLimits(CardLimit cardLimit) {
        return cardRepository.save(cardLimit);
    }


}
