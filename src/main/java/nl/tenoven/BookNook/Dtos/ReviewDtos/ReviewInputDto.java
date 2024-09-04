package nl.tenoven.BookNook.Dtos.ReviewDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Comment;

import java.util.List;

@Getter
@Setter
public class ReviewInputDto {
    @NotEmpty
    private String reviewer;
    private List<Comment> comments;
    @NotEmpty
    private String book;
    @NotEmpty
    private String Text;
    @NotNull
    @Size(max = 100, message = "The maximum value is 100")
    private Byte Score;

    public ReviewInputDto(String reviewer, List<Comment> comments, String book, String text, Byte score) {
        this.reviewer = reviewer;
        this.comments = comments;
        this.book = book;
        Text = text;
        Score = score;
    }
}
