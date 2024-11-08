package nl.tenoven.BookNook.Dtos.AuthorDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;


@Getter
@Setter
public class AuthorShortDto {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String description;
    private boolean validated;
    private Image photo;
}
