package ma.centralbank.services.client;

import ma.centralbank.models.BankAccount;
import ma.centralbank.models.User;

public interface ClientService {


    User addClient(User user);

    BankAccount createBankAccount(BankAccount bankAccount);

}
