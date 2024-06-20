package models.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.Author;
import jakarta.xml.bind.annotation.*;
import lombok.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
@XmlRootElement(name = "authors_books")
@XmlAccessorType(XmlAccessType.FIELD)
public class GettingAuthorsBooksXmlRs {
    @XmlElement(name = "book")
    @XmlElementWrapper
    private List<Book> books;

    private int errorCode;
    private String errorMessage;
    private String errorDetails;

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement(name = "book")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Book {
        private long id;
        @XmlElement(name = "book_title")
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
