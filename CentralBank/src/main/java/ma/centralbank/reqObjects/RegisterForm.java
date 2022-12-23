package ma.centralbank.reqObjects;

import lombok.Data;
import ma.centralbank.models.User;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterForm {
    User user;
    MultipartFile file;

}
