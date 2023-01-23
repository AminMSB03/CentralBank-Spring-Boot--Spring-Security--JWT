package ma.centralbank.services.client;

import ma.centralbank.dto.AccountOperationResponseDto;
import ma.centralbank.dto.BankAccountDto;
import ma.centralbank.reqObjects.RegisterForm;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.Cin;
import ma.centralbank.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClientService {


    User addClient(User user);
    BankAccount createBankAccount(BankAccount bankAccount);
    Cin addCin(MultipartFile file,User user) throws IOException;

    void register(RegisterForm registerForm) throws IOException;

    boolean makeTransaction(String emailSenderBankAccount, Long idReceiverBankAccount, double amount);

    boolean makeTransfer(Long idBankAccount, double amount);

    User getUserByEmail(String email);

    BankAccountDto getBankAccount(String email);

    List<AccountOperationResponseDto> getClientOperations(String email);

}
