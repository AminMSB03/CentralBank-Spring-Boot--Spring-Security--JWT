package ma.centralbank.reqObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Response {
    private String error;
    private Object value;
}
