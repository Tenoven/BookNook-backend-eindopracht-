package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Models.Review;

public class ReviewMappers {
    public static ReviewDto toReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setReviewer(review.getReviewer());
        dto.setComments(review.getComments());
        dto.setBook(review.getBook());
        dto.setText(review.getText());
        dto.setScore(review.getScore());

        return dto;
    }

    public Review toReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setReviewer(reviewDto.getReviewer());
        review.setComments(reviewDto.getComments());
        review.setBook(reviewDto.getBook());
        review.setText(reviewDto.getText());
        review.setScore(reviewDto.getScore());
        return review;
    }
}
