package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty
    private String title;
    @NotNull
    @ManyToOne
    private Author author;
    @NotEmpty
    @Column(length = 2000)
    private String description;
    private Short amountOfPages;
    private Float price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> review;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image cover;

    private boolean validated;

    public Book() {
    }




}
