package nl.tenoven.BookNook.Dtos.ReviewDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Comment;

import java.util.List;

@Getter
@Setter
public class ReviewDto {
    private long id;
    private String reviewer;
    private List<Comment> comments;
    private String book;
    private String Text;
    private Byte Score;

    public ReviewDto() {
    }
}
