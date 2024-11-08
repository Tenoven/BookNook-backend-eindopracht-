package nl.tenoven.BookNook.Dtos.ReviewDtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewShortDto {
    private Long id;
    private String usernameReviewer;
    private String reviewTitle;
    private String bookName;
    private String Text;
    private Byte Score;
}
