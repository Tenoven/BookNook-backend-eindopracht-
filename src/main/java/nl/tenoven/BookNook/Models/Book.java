package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Table(name = "Books")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @ManyToOne
    private Author author;
    private String description;
    private short amountOfPages;
    private float price;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> review;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image cover;

    private boolean validated;

    public Book() {
    }

    
}
