package nl.tenoven.BookNook.Dtos.BookDtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPatchDto {
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
    @Size(min = 10, max = 13)
    private String isbn;
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    @Positive
    private Short amountOfPages;
    @Positive
    private Float price;
    @Positive
    private Long authorId;

}