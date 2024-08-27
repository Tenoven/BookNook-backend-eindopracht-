package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Authors")
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String Name;
    private String DateOfBirth;
    private String Description;

    public Author() {
    }

    public Author(long Id, String Name, String dateOfBirth, String description) {
        this.Id = Id;
        this.Name = Name;
        DateOfBirth = dateOfBirth;
        Description = description;
    }
}
