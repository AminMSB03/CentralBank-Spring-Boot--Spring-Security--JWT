package ma.centralbank.services.client;

import ma.centralbank.models.BankAccount;
import ma.centralbank.models.User;
import ma.centralbank.repository.bankAccount.BankAccountRepository;
import ma.centralbank.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;
    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(UserRepository userRepository, BankAccountRepository bankAccountRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addClient(User user) {
        String pw = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pw));
        return this.userRepository.save(user);
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        return this.bankAccountRepository.save(bankAccount);
    }
}
