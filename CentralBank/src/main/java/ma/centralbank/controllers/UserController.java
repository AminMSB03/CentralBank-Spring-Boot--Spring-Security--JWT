package ma.centralbank.controllers;


import ma.centralbank.models.User;
import ma.centralbank.services.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableWebSecurity
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> appUsers(){
        return userService.listUsers();
    }

}
