package nl.tenoven.BookNook.Dtos.CommentDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String commenter;
    private String message;
    private String datePosted;
    private String reviewTitle;
    private Long reviewId;

    public CommentDto() {
    }
}
