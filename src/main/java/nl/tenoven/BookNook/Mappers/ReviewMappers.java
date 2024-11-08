package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewInputDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewShortDto;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Review;
import nl.tenoven.BookNook.Models.User;
import nl.tenoven.BookNook.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

public class ReviewMappers {


    public static ReviewDto toReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        if (review.getUser() != null) {
            dto.setUsernameReviewer(review.getUser().getUsername());
        }

        if (review.getComments() != null) {
            dto.setComments(review.getComments().stream().map(CommentMapper::toCommentDto).toList());
        }
        dto.setReviewTitle(review.getReviewTitle());

        if (review.getBook() != null) {
            dto.setBookName(review.getBook().getTitle());
        }
        dto.setText(review.getText());
        dto.setScore(review.getScore());

        return dto;
    }

    public static ReviewShortDto toReviewShortDto(Review review) {
        ReviewShortDto dto = new ReviewShortDto();
        dto.setId(review.getId());
        if (review.getUser() != null) {
            dto.setUsernameReviewer(review.getUser().getUsername());
        }

        dto.setReviewTitle(review.getReviewTitle());

        if (review.getBook() != null) {
            dto.setBookName(review.getBook().getTitle());
        }
        dto.setText(review.getText());
        dto.setScore(review.getScore());

        return dto;
    }

    public static Review toReview(ReviewInputDto reviewDto) {
        Review review = new Review();
        review.setReviewTitle(reviewDto.getReviewTitle());

        if (review.getBook() != null) {
            Book book = new Book();
            book.setId(reviewDto.getBookId());
            review.setBook(book);
        }

        review.setText(reviewDto.getText());
        review.setScore(reviewDto.getScore());
        return review;
    }
}
