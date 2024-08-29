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

        review.setTitle(updatedReview.getTitle());
        review.setAuthor(updatedReview.getAuthor());
        review.setDescription(updatedReview.getDescription());
        review.setPrice(updatedReview.getPrice());
        review.setCover(updatedReview.getCover());
        review.setAmountOfPages(updatedReview.getAmountOfPages());
        review.setValidated(false);

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
        dto.setTitle(review.getTitle());
        dto.setAuthor(review.getAuthor());
        dto.setDescription(review.getDescription());
        dto.setPrice(review.getPrice());
        dto.setCover(review.getCover());
        dto.setAmountOfPages(review.getAmountOfPages());
        dto.setValidated(false);

        return dto;
    }

    public Review toReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setAuthor(reviewDto.getAuthor());
        review.setDescription(reviewDto.getDescription());
        review.setPrice(reviewDto.getPrice());
        review.setCover(reviewDto.getCover());
        review.setAmountOfPages(reviewDto.getAmountOfPages());
        review.setValidated(false);
        return review;
    }

}
