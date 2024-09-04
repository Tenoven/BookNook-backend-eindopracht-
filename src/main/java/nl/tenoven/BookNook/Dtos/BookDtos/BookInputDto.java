package nl.tenoven.BookNook.Dtos.BookDtos;

import jakarta.validation.constraints.NotEmpty;
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
        @NotEmpty
        private Author author;
        @NotEmpty
        private String description;
        private Short amountOfPages;

        @Positive(message = "Price must be higher then 0")
        private Float price;
        private List<Review> review;
        private Image cover;
        private Boolean validated;

            public BookInputDto(String title, Author author, String description, Short amountOfPages, Float price, List<Review> review, Image cover, Boolean validated) {
                    this.title = title;
                    this.author = author;
                    this.description = description;
                    this.amountOfPages = amountOfPages;
                    this.price = price;
                    this.review = review;
                    this.cover = cover;
                    this.validated = validated;
            }

            public BookInputDto() {
            }
    }
