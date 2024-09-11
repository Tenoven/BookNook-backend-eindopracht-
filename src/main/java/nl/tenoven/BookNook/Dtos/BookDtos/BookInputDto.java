package nl.tenoven.BookNook.Dtos.BookDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Models.Review;

import java.util.List;

    @Getter
    @Setter
public class BookInputDto {
        @NotEmpty
        private String title;
        @NotNull
        private Long authorId;
        @NotEmpty
        private String description;
        private Short amountOfPages;

        @Positive(message = "Price must be higher then 0")
        private Float price;
        private List<Review> review;
        private Image cover;

    }
