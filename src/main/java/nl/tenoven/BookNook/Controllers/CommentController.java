package nl.tenoven.BookNook.Controllers;

import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentInputDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentPutDto;
import nl.tenoven.BookNook.Services.CommentService;
import nl.tenoven.BookNook.Services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews/{reviewId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final ReviewService reviewService;

    public CommentController(CommentService commentService, ReviewService reviewService) {
        this.commentService = commentService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByReviewId(@PathVariable("reviewId") Long reviewId) {
        List<CommentDto> comments = commentService.getCommentsByReviewId(reviewId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentInputDto dto) {
        CommentDto commentDto = commentService.addComment(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentDto.getId()).toUri();

        return ResponseEntity.created(location).body(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentPutDto updatedComment) {
        CommentDto dto = commentService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok().body(dto);
    }
}
