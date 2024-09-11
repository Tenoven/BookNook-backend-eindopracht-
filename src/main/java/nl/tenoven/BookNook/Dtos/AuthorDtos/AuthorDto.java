package nl.tenoven.BookNook.Dtos.AuthorDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;

import java.util.List;

@Getter
@Setter
public class AuthorDto {
    private long id;
    private String name;
    private String dateOfBirth;
    private String description;
    private boolean validated;
    private List<BookDto> books;
    private Image photo;

    public AuthorDto() {
    }

}
