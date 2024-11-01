package nl.tenoven.BookNook.Dtos.ReviewDtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Comment;

import java.util.List;

@Getter
@Setter
public class ReviewInputDto {
    @NotEmpty
    private String reviewer;

    @NotEmpty
    private String reviewTitle;

    @NotNull
    @Positive
    private Long bookId;

    @NotEmpty
    private String text;

    @NotNull
    @Min(value = 0, message = "The minimum value is 0")
    @Max(value = 100, message = "The maximum value is 100")
    private Byte score;


}
