package nl.tenoven.BookNook.Dtos.AuthorDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;

import java.util.List;

@Getter
@Setter
public class AuthorInputDto {
    @NotEmpty
    private String name;
    private String dateOfBirth;
    @NotEmpty
    private String description;
    @NotEmpty
    private boolean validated;
    private List<Book> books;
    private Image photo;

    public AuthorInputDto(String name, String dateOfBirth, String description, boolean validated, List<Book> books, Image photo) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.validated = validated;
        this.books = books;
        this.photo = photo;
    }
}
