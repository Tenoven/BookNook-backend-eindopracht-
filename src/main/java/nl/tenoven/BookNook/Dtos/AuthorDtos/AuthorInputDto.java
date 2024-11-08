package nl.tenoven.BookNook.Dtos.AuthorDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;

@Getter
@Setter
public class AuthorInputDto {
    @NotEmpty
    private String name;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of Birth must be in the format yyyy-MM-dd")
    private String dateOfBirth;
    @NotEmpty
    private String description;
    private Image photo;

    public AuthorInputDto() {
    }
}
