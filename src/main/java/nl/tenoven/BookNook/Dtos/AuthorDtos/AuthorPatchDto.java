package nl.tenoven.BookNook.Dtos.AuthorDtos;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorPatchDto {
    private String name;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of Birth must be in the format yyyy-MM-dd")
    private String dateOfBirth;

    private String description;
}
