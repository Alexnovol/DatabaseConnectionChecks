package models.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SavingNewBookRs {

    private long bookId;

    private int errorCode;
    private String errorMessage;
    private String errorDetails;
}
