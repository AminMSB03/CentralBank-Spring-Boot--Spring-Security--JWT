package ma.centralbank.ExecptionsHandler.ExecptionsData;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ma.centralbank.enums.CardType;

import java.util.Date;

@Data
public class LimitExecption {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int limitforDay;

    private int limitForYear;

    private String operation;
    private CardType cardType;


    public LimitExecption(Date timestamp, int limitforDay, int limitForYear, String operation,CardType cardType) {
        this.timestamp = timestamp;
        this.limitforDay = limitforDay;
        this.limitForYear = limitForYear;
        this.operation = operation;
        this.cardType = cardType;
    }
}
