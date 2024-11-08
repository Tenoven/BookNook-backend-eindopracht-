package nl.tenoven.BookNook.Controllers;

import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewInputDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewPatchDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewShortDto;
import nl.tenoven.BookNook.Services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewShortDto>> getAllReviews() {
        List<ReviewShortDto> reviews = reviewService.getReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("reviewId") Long id) {
        ReviewDto review = reviewService.getReview(id);
        return ResponseEntity.ok().body(review);
    }

    @PostMapping("/{reviewId}/addBook/{bookId}")
    public ResponseEntity<ReviewDto> assignReviewToBook(@PathVariable("reviewId") Long reviewId, @PathVariable("bookId") Long bookid) {
        ReviewDto reviewDto = reviewService.assignReviewToBook(reviewId, bookid);
        return ResponseEntity.ok().body(reviewDto);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewInputDto dto, @AuthenticationPrincipal UserDetails userDetails) {


        ReviewDto reviewDto = reviewService.addReview(dto, userDetails);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{reviewId}").buildAndExpand(reviewDto.getId()).toUri();

        return ResponseEntity.created(location).body(reviewDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReviewById(@PathVariable Long reviewId, @RequestBody ReviewPatchDto newReview) {
        ReviewDto dto = reviewService.updateReview(reviewId, newReview);
        return ResponseEntity.ok().body(dto);
    }
}
