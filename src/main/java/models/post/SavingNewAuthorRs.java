package models.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SavingNewAuthorRs {

    private long authorId;

    private int errorCode;
    private String errorMessage;
    private String errorDetails;


}
