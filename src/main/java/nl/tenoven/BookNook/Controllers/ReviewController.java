package nl.tenoven.BookNook.Controllers;

import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewInputDto;
import nl.tenoven.BookNook.Dtos.ReviewDtos.ReviewPutDto;
import nl.tenoven.BookNook.Services.ReviewService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReviewDto>> getReviews() {
        List<ReviewDto> reviews = reviewService.getReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("id") Long id) {
        ReviewDto review = reviewService.getReview(id);
        return ResponseEntity.ok().body(review);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewInputDto dto) {
        ReviewDto reviewDto = reviewService.addReview(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(reviewDto.getId()).toUri();

        return ResponseEntity.created(location).body(reviewDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @RequestBody ReviewPutDto newReview) {
        ReviewDto dto = reviewService.updateReview(id, newReview);
        return ResponseEntity.ok().body(dto);
    }
}
