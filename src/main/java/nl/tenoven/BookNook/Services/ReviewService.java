package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewInputDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewPutDto;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Review;
import nl.tenoven.BookNook.Models.User;
import nl.tenoven.BookNook.Repositories.BookRepository;
import nl.tenoven.BookNook.Repositories.ReviewRepository;
import nl.tenoven.BookNook.Repositories.UserRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.ReviewMappers.toReview;
import static nl.tenoven.BookNook.Mappers.ReviewMappers.toReviewDto;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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

    public ReviewDto addReview(ReviewInputDto newReview, UserDetails userDetails) {
        Review savedReview = reviewRepository.save(toReview(newReview));
        Optional<User> user = userRepository.findById(userDetails.getUsername());
        if(user.isPresent() & userDetails.isAccountNonExpired()){
            savedReview.setUser(user.get());
        }
        return toReviewDto(savedReview);
    }

    public ReviewDto assignReviewToBook(Long reviewId, Long bookId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty() || optionalReview.isEmpty()) {
            throw new RecordNotFoundException("Book or Review not found.");
        }

        Book book = optionalBook.get();
        Review review = optionalReview.get();

        review.setBook(book);
        Review updatedReview = reviewRepository.save(review);

        return toReviewDto(updatedReview);
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
