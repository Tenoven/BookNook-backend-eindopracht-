package nl.tenoven.BookNook.Dtos.CommentDtos;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private Review review;

    public CommentInputDto(String commenter, String message, String datePosted, Review review) {
        this.commenter = commenter;
        this.message = message;
        this.datePosted = datePosted;
        this.review = review;
    }
}
