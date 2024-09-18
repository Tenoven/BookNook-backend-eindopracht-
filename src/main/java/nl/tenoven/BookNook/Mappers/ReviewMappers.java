package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewInputDto;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Review;

public class ReviewMappers {
    public static ReviewDto toReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setReviewer(review.getReviewer());
        if (review.getComments() != null){
            dto.setComments(review.getComments().stream().map(CommentMapper::toCommentDto).toList());
        }
        dto.setReviewTitle(review.getReviewTitle());
        dto.setBookName(review.getBook().getTitle());
        dto.setText(review.getText());
        dto.setScore(review.getScore());

        return dto;
    }

    public static Review toReview(ReviewInputDto reviewDto) {
        Review review = new Review();
        review.setReviewer(reviewDto.getReviewer());
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setComments(reviewDto.getComments());

        Book book = new Book();
        book.setId(reviewDto.getBookId());
        review.setBook(book);

        review.setText(reviewDto.getText());
        review.setScore(reviewDto.getScore());
        return review;
    }
}
