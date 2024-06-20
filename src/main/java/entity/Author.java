package entity;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Table(name = "Author")
public class Author {
    @XmlElement(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @XmlElement(name = "first_name")
    @Column(name = "first_name")
    private String firstName;
    @XmlElement(name = "family_name")
    @Column(name = "family_name")
    private String familyName;
    @XmlElement(name = "second_name")
    @Column(name = "second_name")
    private String secondName;
    @XmlElement(name = "birth_date")
    @Column(name = "birth_date")
    private String birthDate;

    public Author(long id, String firstName, String familyName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.secondName = secondName;
    }
}
