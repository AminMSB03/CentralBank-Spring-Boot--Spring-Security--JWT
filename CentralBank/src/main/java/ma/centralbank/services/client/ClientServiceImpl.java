package ma.centralbank.services.client;

import lombok.RequiredArgsConstructor;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.User;
import ma.centralbank.repository.bankAccount.BankAccountRepository;
import ma.centralbank.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private UserRepository userRepository;
    private BankAccountRepository bankAccountRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public User addClient(User user) {
        String pw = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pw));
        return userRepository.save(user);
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }


}
