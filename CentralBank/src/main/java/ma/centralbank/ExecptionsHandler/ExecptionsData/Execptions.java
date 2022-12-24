package ma.centralbank.ExecptionsHandler.ExecptionsData;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class Execptions {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    public Execptions(Date timestamp, int code, String status, String message) {
        this.timestamp = timestamp;
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
