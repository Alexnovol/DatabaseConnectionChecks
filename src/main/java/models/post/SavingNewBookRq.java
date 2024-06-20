package models.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.Author;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SavingNewBookRq {

    private String bookTitle;
    private Author author;
}
