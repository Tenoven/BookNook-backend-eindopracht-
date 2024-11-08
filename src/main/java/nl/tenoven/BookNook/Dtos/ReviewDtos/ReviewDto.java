package nl.tenoven.BookNook.Dtos.ReviewDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentDto;

import java.util.List;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String usernameReviewer;
    private String reviewTitle;
    private List<CommentDto> comments;
    private String bookName;
    private String Text;
    private Byte Score;

    public ReviewDto() {
    }
}
