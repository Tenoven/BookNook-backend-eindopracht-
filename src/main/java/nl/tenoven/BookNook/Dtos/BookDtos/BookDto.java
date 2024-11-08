package nl.tenoven.BookNook.Dtos.BookDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Models.Image;

import java.util.List;

@Getter
@Setter
public class BookDto {

    private Long id;
    private String title;
    private String authorName;
    private String description;
    private Short amountOfPages;
    private String isbn;
    private Float price;
    private List<ReviewDto> reviews;
    private Image cover;
    private Boolean validated;

    public BookDto() {
    }

}
