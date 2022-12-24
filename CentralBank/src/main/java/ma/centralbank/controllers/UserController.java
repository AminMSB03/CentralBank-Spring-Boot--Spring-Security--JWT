package ma.centralbank.controllers;


import lombok.RequiredArgsConstructor;
import ma.centralbank.ExecptionsHandler.AccountOperationsExecptions;
import ma.centralbank.ExecptionsHandler.ExecptionsData.Execptions;
import ma.centralbank.ExecptionsHandler.ExecptionsData.LimitExecption;
import ma.centralbank.ExecptionsHandler.ExecptionsData.OperationExecption;
import ma.centralbank.dto.AccountOperationsDto;
import ma.centralbank.dto.GetUserByEmailDto;
import ma.centralbank.enums.OperationType;
import ma.centralbank.models.AccountOperation;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.CardLimit;
import ma.centralbank.models.User;
import ma.centralbank.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class UserController {

    private final UserService userService;

    private final AccountOperationsExecptions exceptionHandler;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> appUsers(User user) {
        System.out.println(user.getBankAccounts());
        return userService.listUsers();
    }

    @GetMapping("/accountTypesList")
    public List<BankAccount> getAccountTypes() {
        return userService.getAccountTypes();
    }


    @PostMapping("/getUserByEmail")
    @PreAuthorize("hasAuthority('AGENT')")
    public ResponseEntity<User> getUserByEmail(@RequestBody GetUserByEmailDto getUserByEmailDto) {
        return ResponseEntity.ok().body(userService.loadUserByEmail(getUserByEmailDto.getEmail()));
    }

    @PostMapping("/addNewUser")
    @PreAuthorize("hasAuthority('AGENT')")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.addNewUser(user));
    }


    @PostMapping("/Withdraw")
    @PreAuthorize("hasAuthority('AGENT')")
    public ResponseEntity<?> withdraw(@RequestBody AccountOperationsDto accountOperationsDto) {
        User user = userService.loadUserByEmail(accountOperationsDto.getEmail());
        for (BankAccount account : user.getBankAccounts()) {

            for (CardLimit card : userService.getCardLimits()) {
                if ((int) account.getBalance() != 0) {

                    if (card.getCardType().equals(account.getAccountType())) {
                        if (accountOperationsDto.getAmountWithdrawed() <= card.getLimitForDay()) {

                            double userCurrentBalance = account.getBalance() - accountOperationsDto.getAmountWithdrawed();
                            account.setBalance(userCurrentBalance);

                            userService.userAccountOperations(
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
                        }else {
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

}
