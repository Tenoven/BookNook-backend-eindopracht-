package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentInputDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentPutDto;
import nl.tenoven.BookNook.Mappers.CommentMapper;
import nl.tenoven.BookNook.Models.Comment;
import nl.tenoven.BookNook.Models.Review;
import nl.tenoven.BookNook.Models.User;
import nl.tenoven.BookNook.Repositories.CommentRepository;
import nl.tenoven.BookNook.Repositories.ReviewRepository;
import nl.tenoven.BookNook.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.CommentMapper.toComment;
import static nl.tenoven.BookNook.Mappers.CommentMapper.toCommentDto;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDto> getCommentsByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review " + reviewId + " not found"));
        List<Comment> comments = review.getComments();
        List<CommentDto> commentDtos = comments.stream().map(CommentMapper::toCommentDto).toList();

        return commentDtos;
    }

    public CommentDto addComment(CommentInputDto newComment, UserDetails userDetails) {
        Comment savedComment = commentRepository.save(toComment(newComment));
        Optional<User> user = userRepository.findById(userDetails.getUsername());
        if(user.isPresent() & userDetails.isAccountNonExpired()){
            savedComment.setUser(user.get());
        }
        return toCommentDto(savedComment);
    }

    public CommentDto updateComment(Long id, CommentPutDto updatedComment) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment" + id + "not found"));

        if (updatedComment.getMessage() != null) {
            comment.setMessage(updatedComment.getMessage());
        }
        if (updatedComment.getDatePosted() != null) {
            comment.setDatePosted(updatedComment.getDatePosted());
        }
        if (updatedComment.getReview() != null) {
            comment.setReview(updatedComment.getReview());
        }

        Comment savedComment = commentRepository.save(comment);
        return toCommentDto(savedComment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment" + id + "not found");
        }
        commentRepository.deleteById(id);
    }
}

