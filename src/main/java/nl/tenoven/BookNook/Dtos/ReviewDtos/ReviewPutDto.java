package nl.tenoven.BookNook.Dtos.ReviewDtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewPutDto {
    private String text;
    @Min(value = 0, message = "The minimum value is 0")
    @Max(value = 100, message = "The maximum value is 100")
    private Byte score;
}
