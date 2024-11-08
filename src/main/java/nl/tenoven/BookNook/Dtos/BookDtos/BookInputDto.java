package nl.tenoven.BookNook.Dtos.BookDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;


@Getter
@Setter
public class BookInputDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    @Size(min = 10, max = 13)
    private String isbn;
    @Positive
    private Short amountOfPages;
    @Positive(message = "Price must be higher then 0")
    private Float price;
    private Image cover;
    private Long authorId;

}
