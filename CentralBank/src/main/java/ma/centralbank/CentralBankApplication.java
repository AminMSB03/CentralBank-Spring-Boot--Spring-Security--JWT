package ma.centralbank;

import lombok.RequiredArgsConstructor;
import ma.centralbank.enums.AccountStatus;
import ma.centralbank.enums.CardType;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.CardLimit;
import ma.centralbank.models.OnlinePayments;
import ma.centralbank.models.User;
import ma.centralbank.services.UserOperationsService.UserOperationsServiceImp;
import ma.centralbank.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class CentralBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralBankApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    CommandLineRunner commandLineRunner(UserService userService, UserOperationsServiceImp userOperationsServiceImp) {


        return args -> {
            userOperationsServiceImp.addOnlinePayment(
                    new OnlinePayments(
                            1,
                            100010010L,
                            06114401116L,
                            "Facture-Payment",
                            1000,
                            null

                    )
            );


            userOperationsServiceImp.addOnlinePayment(
                    new OnlinePayments(
                            2,
                            0001010010L,
                            06114401116L,
                            "water-Payment",
                            250,
                            null

                    )
            );


            userService.addNewBankAccount(
                    new BankAccount(
                            1,
                            11111332111L,
                            CardType.STANDARD,
                            2000.0,
                            LocalDate.now(),
                            AccountStatus.INACTIVE,
                            new User
                                    (
                                            null, "Agent",
                                            "de", "casablanca",
                                            passwordEncoder().encode("katlife"), "dw@gmail.com",
                                            0630155355L, "AGENT",
                                            null
                                    ),
                            new OnlinePayments(
                                    1,
                                    100010010L,
                                    06114401116L,
                                    "Facture-Payment",
                                    1000,
                                    null

                            )


                    )
            );
            userService.addNewBankAccount(
                    new BankAccount(
                            2,
                            11122311332111L,
                            CardType.PROFESSIONAL,
                            4000.20,
                            LocalDate.now(),
                            AccountStatus.INACTIVE,
                            new User
                                    (
                                            null, "zk",
                                            "tj", "rabat",
                                            "agent123", "zk@gmail.com",
                                            0622155355L, "AGENT",
                                            null
                                    ),
                            new OnlinePayments(
                                    2,
                                    0001010010L,
                                    06114401116L,
                                    "water-Payment",
                                    250,
                                    null

                            )

                    )
            );



            userService.AddNewCardLimits(
                    new CardLimit(
                            1,
                            5000,
                            100000,
                            CardType.STANDARD
                    )
            );

            userService.AddNewCardLimits(
                    new CardLimit(
                            2,
                            10000,
                            200000,
                            CardType.PROFESSIONAL
                    )
            );

        };
    }

}
