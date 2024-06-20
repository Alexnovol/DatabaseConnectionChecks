package models.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import entity.Author;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GettingAuthorsBooksRs {
    @JsonUnwrapped
    private Book book;
    private int statusCode;

    private int errorCode;
    private String errorMessage;
    private String errorDetails;

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Book {

        private long id;
        private String bookTitle;
        private Author author;
        private String updated;

        public Book(long id, String bookTitle, Author author) {
            this.id = id;
            this.bookTitle = bookTitle;
            this.author = author;
        }

    }

}
