package nl.tenoven.BookNook.Controllers;

import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentInputDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentPatchDto;
import nl.tenoven.BookNook.Services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<List<CommentDto>> getCommentsByReviewId(@PathVariable("reviewId") Long reviewId) {
        List<CommentDto> comments = commentService.getCommentsByReviewId(reviewId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDto> addCommentToReview(@Valid @RequestBody CommentInputDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        CommentDto commentDto = commentService.addComment(dto, userDetails);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentDto.getId()).toUri();

        return ResponseEntity.created(location).body(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("commentId") Long commentId, @RequestBody CommentPatchDto updatedComment) {
        CommentDto dto = commentService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok().body(dto);
    }
}
