package ma.centralbank.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OnlinePaymentsDto {

    private Long contractNumber;
    private Long phoneNumber;
    private String operationType;

}
