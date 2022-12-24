package ma.centralbank;

import lombok.RequiredArgsConstructor;
import ma.centralbank.enums.AccountStatus;
import ma.centralbank.enums.CardType;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.CardLimit;
import ma.centralbank.models.User;
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
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
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
                                            "agent123", "dw@gmail.com",
                                            0630155355L, "AGENT",
                                            null
                                    )

                    )
            );
            userService.addNewBankAccount(
                    new BankAccount(
                            2,
                            11122311332111L,
                            CardType.PROFESSIONAL,
                            0.0,
                            LocalDate.now(),
                            AccountStatus.INACTIVE,
                            null

                    )
            );

            userService.addNewUser(
                    new User
                            (
                                    null, "Agent",
                                    "Agent", "casablanca",
                                    "agent123", "agent@gmail.com",
                                    0630155355L, "AGENT",
                                    null
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
