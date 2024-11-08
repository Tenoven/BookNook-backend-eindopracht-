package nl.tenoven.BookNook.Dtos.CommentDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPatchDto {
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String message;
    @NotEmpty
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of Birth must be in the format yyyy-MM-dd")
    private String datePosted;
}
