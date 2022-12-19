package ma.centralbank;

import ma.centralbank.models.User;
import ma.centralbank.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CentralBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralBankApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



            /*
                @Bean
                CommandLineRunner commandLineRunner(UserService userService){
                    return args -> {
                        userService.addNewUser(new User(null,"User1","user","casablanca","user123","user@gmail.com","0630155355","CLIENT",null));
                    };
                }
             */

}
