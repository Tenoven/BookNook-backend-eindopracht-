package nl.tenoven.BookNook.Dtos.CommentDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Review;

@Getter
@Setter
public class CommentInputDto {
    @NotEmpty
    private String commenter;
    @NotEmpty
    private String message;
    @NotEmpty
    private String datePosted;
    @NotNull
    private Long reviewId;

}
