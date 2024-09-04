package nl.tenoven.BookNook.Dtos.BookDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Models.Review;

import java.util.List;

@Getter
@Setter
public class BookDto {

    private Long id;
    private String title;
    private Author author;
    private String description;
    private Short amountOfPages;
    private Float price;
    private List<Review> review;
    private Image cover;
    private Boolean validated;

    public BookDto() {
    }
    
}
