package ma.centralbank.services.UserOperationsService;

import ma.centralbank.dto.AccountOperationsDto;
import ma.centralbank.dto.OnlinePaymentsDto;
import ma.centralbank.models.*;
import org.springframework.http.ResponseEntity;
import ma.centralbank.models.OnlinePayments;
import java.util.List;

public interface UserOperationsService {
    ResponseEntity<?> withdraw(AccountOperationsDto accountOperationsDto);
    List<CardLimit> getCardLimits();
    AccountOperation userAccountOperations(AccountOperation accountOperation);

    ResponseEntity<?> transferMoney(AccountOperationsDto accountOperationsDto);

    BankAccount findReciverTransfer(AccountOperationsDto accountOperationsDto);

    ResponseEntity<?> payments(OnlinePaymentsDto onlinePaymentsDto);

    OnlinePayments addOnlinePayment(OnlinePayments onlinePayments);
}
