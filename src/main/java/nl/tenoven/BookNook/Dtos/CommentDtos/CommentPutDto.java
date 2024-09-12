package nl.tenoven.BookNook.Dtos.CommentDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Review;

@Getter
@Setter
public class CommentPutDto {
    private String commenter;
    private String message;
    private String datePosted;
    private Review review;
}
