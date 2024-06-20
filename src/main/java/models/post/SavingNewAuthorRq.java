package models.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SavingNewAuthorRq {

    private String firstName;
    private String familyName;
    private String secondName;
    private String birthDate;

    public SavingNewAuthorRq(String firstName, String familyName, String secondName) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.secondName = secondName;
    }
}
