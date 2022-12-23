package ma.centralbank.services.user;

import ma.centralbank.models.User;

import java.util.List;

public interface UserService {

    User addNewUser(User user);

    User loadUserByEmail(String email);

    List<User> listUsers();

    User findOne(String email);

}
