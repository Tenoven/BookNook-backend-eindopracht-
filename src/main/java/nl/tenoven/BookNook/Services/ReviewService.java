package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewInputDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewPutDto;
import nl.tenoven.BookNook.Models.Review;
import nl.tenoven.BookNook.Repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static nl.tenoven.BookNook.Mappers.ReviewMappers.toReview;
import static nl.tenoven.BookNook.Mappers.ReviewMappers.toReviewDto;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getReviews() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> dtos = new ArrayList<>();

        for (Review review : reviews) {
            dtos.add(toReviewDto(review));
        }
        return dtos;
    }

    public ReviewDto getReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review" + id + "not found"));
        return toReviewDto(review);
    }

    public ReviewDto addReview(ReviewInputDto newReview) {
        Review savedReview = reviewRepository.save(toReview(newReview));
        return toReviewDto(savedReview);
    }

    public ReviewDto updateReview(Long id, ReviewPutDto updatedReview) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review" + id + "not found"));

        if (updatedReview.getText() != null) {
            review.setText(updatedReview.getText());
        }
        if (updatedReview.getScore() != null) {
            review.setScore(updatedReview.getScore());
        }

        Review savedReview = reviewRepository.save(review);
        return toReviewDto(savedReview);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review" + id + "not found");
        }
        reviewRepository.deleteById(id);
    }

}
