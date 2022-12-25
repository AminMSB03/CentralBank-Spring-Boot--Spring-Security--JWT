package ma.centralbank.services.UserOperationsService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.centralbank.ExecptionsHandler.AccountOperationsExecptions;
import ma.centralbank.ExecptionsHandler.ExecptionsData.Execptions;
import ma.centralbank.ExecptionsHandler.ExecptionsData.LimitExecption;
import ma.centralbank.ExecptionsHandler.ExecptionsData.OperationExecption;
import ma.centralbank.dto.AccountOperationsDto;
import ma.centralbank.dto.OnlinePaymentsDto;
import ma.centralbank.enums.OperationType;
import ma.centralbank.models.*;
import ma.centralbank.repository.Card.CardRepository;
import ma.centralbank.repository.accountOperationsRepository.OperationsRepository;
import ma.centralbank.repository.bankAccount.BankAccountRepository;
import ma.centralbank.repository.payments.OnlinePaymentsRepository;
import ma.centralbank.repository.user.UserRepository;
import ma.centralbank.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserOperationsServiceImp implements UserOperationsService {
    private final UserService userService;

    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationsExecptions exceptionHandler;
    private final CardRepository cardRepository;
    private final OperationsRepository operationsRepository;
    private final OnlinePaymentsRepository onlinePaymentsRepository;


    @Override
    public List<CardLimit> getCardLimits() {
        return cardRepository.findAll();
    }

    @Override
    public BankAccount findReciverTransfer(AccountOperationsDto accountOperationsDto) {
        return bankAccountRepository.findByAccountNumber(accountOperationsDto.getAccountNumber());
    }

    @Override
    public OnlinePayments addOnlinePayment(OnlinePayments onlinePayments) {
        return onlinePaymentsRepository.save(onlinePayments);
    }

    @Override
    public AccountOperation userAccountOperations(AccountOperation accountOperation) {
        return operationsRepository.save(accountOperation);
    }

    @Override
    public ResponseEntity<?> withdraw(AccountOperationsDto accountOperationsDto) {
        User user = userService.loadUserByEmail(accountOperationsDto.getEmail());

        for (BankAccount account : user.getBankAccounts()) {

            for (CardLimit card : getCardLimits()) {
                if ((int) account.getBalance() != 0) {

                    if (card.getCardType().equals(account.getAccountType())) {
                        if (accountOperationsDto.getAmountWithdrawed() <= card.getLimitForDay()) {

                            double userCurrentBalance = account.getBalance() - accountOperationsDto.getAmountWithdrawed();
                            account.setBalance(userCurrentBalance);

                            userAccountOperations(
                                    new AccountOperation
                                            (
                                                    Date.from(Instant.now()),
                                                    accountOperationsDto.getAmountWithdrawed(),
                                                    OperationType.DEBIT,
                                                    account
                                            )
                            );

                            userService.addNewBankAccount(account);

                            return ResponseEntity
                                    .ok()
                                    .body(
                                            exceptionHandler.successfuOperations
                                                    (
                                                            new OperationExecption
                                                                    (
                                                                            Date.from(Instant.now()),
                                                                            accountOperationsDto.getAmountWithdrawed(),
                                                                            userCurrentBalance,
                                                                            "Withdrawal"
                                                                    )
                                                    )
                                    );
                        } else {
                            return ResponseEntity
                                    .ok()
                                    .body(
                                            exceptionHandler.limitExecption(
                                                    (
                                                            new LimitExecption(
                                                                    Date.from(Instant.now()),
                                                                    card.getLimitForDay(),
                                                                    card.getLimitForYear(),
                                                                    "Withdrawal",
                                                                    card.getCardType()
                                                            )
                                                    )
                                            )
                                    );
                        }
                    }


                }
            }


        }

        return ResponseEntity
                .badRequest()
                .body(
                        exceptionHandler.AccountExceptions
                                (
                                        new Execptions
                                                (
                                                        Date.from(Instant.now()),
                                                        400,
                                                        "Error",
                                                        "insufficient balance"
                                                )
                                )
                );
    }


    @Override
    public ResponseEntity<?> transferMoney(AccountOperationsDto accountOperationsDto) {
        BankAccount userBankAccount = findReciverTransfer(accountOperationsDto);
        if (userBankAccount == null) {
            return ResponseEntity.badRequest().body(
                    exceptionHandler.AccountExceptions(
                            new Execptions(
                                    Date.from(Instant.now()),
                                    400,
                                    "Error",
                                    String.format("User with { %d } account number dosnt exist", accountOperationsDto.getAccountNumber())
                            )
                    )
            );
        } else if (accountOperationsDto.getMoneyToTransfer() >= 10) {
            double reciverBalance = userBankAccount.getBalance() + accountOperationsDto.getMoneyToTransfer();
            userBankAccount.setBalance(reciverBalance);
            userService.addNewBankAccount(userBankAccount);

            return ResponseEntity
                    .ok()
                    .body(
                            exceptionHandler.successfuOperations
                                    (
                                            new OperationExecption
                                                    (
                                                            Date.from(Instant.now()),
                                                            accountOperationsDto.getMoneyToTransfer(),
                                                            reciverBalance,
                                                            "Transfer"
                                                    )
                                    )
                    );
        } else {
            return ResponseEntity.badRequest().body(
                    exceptionHandler.AccountExceptions(
                            new Execptions(
                                    Date.from(Instant.now()),
                                    400,
                                    "Error",
                                    String.format("Please enter amount of money to transfer")
                            )
                    )
            );


        }


    }

    @Override
    public ResponseEntity<?> payments(OnlinePaymentsDto onlinePaymentsDto) {
        User user = userService.getAccountPhoneNumberByPhoneNumber(onlinePaymentsDto.getPhoneNumber());

        if (user == null) {
            return ResponseEntity.badRequest().body(
                    exceptionHandler.AccountExceptions(
                            new Execptions(
                                    Date.from(Instant.now()),
                                    400,
                                    "Error",
                                    String.format("User with phone number { %d } is not found", onlinePaymentsDto.getPhoneNumber())
                            )
                    )
            );

        }
        for (BankAccount account : user.getBankAccounts()) {

            if (account.getBalance() < account.getPayments().getAmoutToPay()) {
                return ResponseEntity.badRequest().body(
                        exceptionHandler.AccountExceptions(
                                new Execptions(
                                        Date.from(Instant.now()),
                                        400,
                                        "Error",
                                        String.format("Balance inssufisant", account.getBalance())
                                )
                        )

                );
            } else {
                double amountPayed = account.getBalance() - account.getPayments().getAmoutToPay();
                account.setBalance(amountPayed);
                userService.addNewBankAccount(account);

                return ResponseEntity
                        .ok()
                        .body(
                                exceptionHandler.successfuOperations
                                        (
                                                new OperationExecption
                                                        (
                                                                Date.from(Instant.now()),
                                                                account.getPayments().getAmoutToPay(),
                                                                amountPayed,
                                                                "Bill Payment"
                                                        )
                                        )
                        );

            }

        }
        return ResponseEntity
                .badRequest()
                .body(
                        exceptionHandler.AccountExceptions
                                (
                                        new Execptions
                                                (
                                                        Date.from(Instant.now()),
                                                        400,
                                                        "Error",
                                                        "check ur details"
                                                )
                                )
                );


    }
}
