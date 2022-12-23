package ma.centralbank;

import ma.centralbank.models.BankAccount;
import ma.centralbank.models.Cin;
import ma.centralbank.models.User;
import ma.centralbank.repository.bankAccount.BankAccountRepository;
import ma.centralbank.repository.cin.CinRepository;
import ma.centralbank.services.client.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class CentralBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralBankApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




                @Bean @Transactional
                CommandLineRunner commandLineRunner(ClientService clientService, CinRepository cinRepository, BankAccountRepository bankAccountRepository){
                    return args -> {
                        /*
                            User user = clientService.addClient(
                                    User.builder()
                                            .firstName("Agent")
                                            .lastName("Agent")
                                            .email("agent@gmail.com")
                                            .password("agent")
                                            .role("AGENT")
                                            .build()
                            );

                        Optional<Cin> cin = cinRepository.findById(1L);

                        BankAccount bankAccount = bankAccountRepository.save(BankAccount.builder().accountNumber(123456789L).balance(200.50)
                                .creationDate(LocalDate.from(LocalDateTime.now())).build());
                         */






                    };
                }

}
