package models.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.Author;
import jakarta.xml.bind.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
public class GettingAuthorsBooksXmlRq {
    @XmlTransient
    private Author author;

    @XmlElement(name = "author_id")
    public Long getAuthorId() {
        return author.getId();
    }

}
