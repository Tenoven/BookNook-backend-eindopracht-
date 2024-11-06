package nl.tenoven.BookNook.Dtos.BookDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Models.Review;

import java.util.List;

@Getter
@Setter
public class BookPutDto {
    private String title;
    private Long authorId;
    private String isbn;
    private String description;
    private Short amountOfPages;
    private Float price;
    private List<Review> review;
    private Image cover;
}