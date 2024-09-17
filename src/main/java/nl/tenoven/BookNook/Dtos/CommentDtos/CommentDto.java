package nl.tenoven.BookNook.Dtos.CommentDtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Review;

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
