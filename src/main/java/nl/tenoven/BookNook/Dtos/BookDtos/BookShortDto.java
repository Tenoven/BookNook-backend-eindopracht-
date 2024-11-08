package nl.tenoven.BookNook.Dtos.BookDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;


@Getter
@Setter
public class BookShortDto {
    private Long id;
    private String title;
    private String authorName;
    private String description;
    private Short amountOfPages;
    private String isbn;
    private Float price;
    private Image cover;
    private Boolean validated;
}
