package nl.tenoven.BookNook.Dtos.CommentDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInputDto {
    @NotEmpty
    private String message;
    @NotEmpty
    private String datePosted;
    @NotNull
    private Long reviewId;

}
