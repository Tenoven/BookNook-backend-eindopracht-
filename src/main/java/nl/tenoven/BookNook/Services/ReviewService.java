package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Models.Review;
import nl.tenoven.BookNook.Repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.tenoven.BookNook.Mappers.ReviewMappers.toReviewDto;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
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

        if (updatedReview.getReviewer() != null) {
            review.setReviewer(updatedReview.getReviewer());
        }
        if (updatedReview.getComments() != null) {
            review.setComments(updatedReview.getComments());
        }
        if (updatedReview.getBook() != null) {
            review.setBook(updatedReview.getBook());
        }
        if (updatedReview.getText() != null) {
            review.setText(updatedReview.getText());
        }
        if (updatedReview.getScore() != null) {
            review.setScore(updatedReview.getScore());
        }

        Review savedReview= reviewRepository.save(review);
        return toReviewDto(savedReview);
    }

    public void deleteReview(long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review" + id + "not found");
        }
        reviewRepository.deleteById(id);
    }



}
