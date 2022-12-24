package ma.centralbank.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetUserByEmailDto {

    private String email;
}
