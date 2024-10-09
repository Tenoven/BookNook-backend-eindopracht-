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
    private List<Book> books;
    private Image photo;

    public AuthorInputDto() {
    }
}
