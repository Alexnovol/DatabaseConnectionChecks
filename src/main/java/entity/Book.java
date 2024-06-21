package entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "book_title")
    private String bookTitle;
    @Column(name = "author_id")
    private long authorId;
    @Column(name = "updated")
    private Timestamp updated;
}
