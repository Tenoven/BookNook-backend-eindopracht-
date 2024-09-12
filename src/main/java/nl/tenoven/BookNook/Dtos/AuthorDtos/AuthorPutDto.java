package nl.tenoven.BookNook.Dtos.AuthorDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;

import java.util.List;

@Getter
@Setter
public class AuthorPutDto {
    private String name;
    private String dateOfBirth;
    private String description;
    private boolean validated;
    private List<Book> books;
    private Image photo;
}
