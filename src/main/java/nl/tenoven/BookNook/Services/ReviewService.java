package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private Reviewrepository reviewrepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewrepository = reviewRepository;
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public Review getReview(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Review" + id + "not found"));
        return review;
    }

    public ReviewDto addReview(Review newReview) {
        Review savedReview = reviewRepository.save(newReview);
        return toReviewDto(savedReview);
    }

    public ReviewDto updateReview(long id, Review updatedReview) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Review" + id + "not found"));

        review.setReviewer(updatedReview.getReviewer());
        review.setComments(updatedReview.getComments());
        review.setBook(updatedReview.getBook());
        review.setText(updatedReview.getText());
        review.setScore(updatedReview.getScore());

        Review savedReview= reviewRepository.save(review);
        return toReviewDto(savedReview);
    }

    public void deleteReview(long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review" + id + "not found");
        }
        reviewRepository.deleteById(id);
    }

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
