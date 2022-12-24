package ma.centralbank.ExecptionsHandler.ExecptionsData;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class OperationExecption {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private double amount;

    private double currentBalance;

    private String operation;

    public OperationExecption(Date timestamp, double amount, double currentBalance, String operation) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.operation = operation;
    }
}
