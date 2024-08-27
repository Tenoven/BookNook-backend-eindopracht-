package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Books")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String Title;
    private String Author;
    private String Description;
    private short AmountOfPages;
    private float Price;
//  private Comments
    private String Genre;
//  private Cover (Picture)
    private boolean Validated;

    public Book() {
    }

    
}
