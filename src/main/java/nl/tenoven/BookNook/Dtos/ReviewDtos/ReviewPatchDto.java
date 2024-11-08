package nl.tenoven.BookNook.Dtos.ReviewDtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewPatchDto {
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String reviewTitle;
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String text;
    @Min(value = 0, message = "The minimum value is 0")
    @Max(value = 100, message = "The maximum value is 100")
    private Byte score;
}
