package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Comment;

import nl.tenoven.BookNook.Repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.tenoven.BookNook.Mappers.CommentMapper.toCommentDto;

@Service
public class CommentService {

        private CommentRepository commentRepository;

        public CommentService(CommentRepository commentRepository) {
            this.commentRepository = commentRepository;
        }

        public List<Comment> getComments() {
            return commentRepository.findAll();
        }

        public Comment getComment(long id) {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Comment" + id + "not found"));
            return comment;
        }

        public CommentDto addComment(Comment newComment) {
            Comment savedComment = commentRepository.save(newComment);
            return toCommentDto(savedComment);
        }

        public CommentDto updateComment(long id, Comment updatedComment) {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Comment" + id + "not found"));

            if (updatedComment.getCommenter() != null) {
                comment.setCommenter(updatedComment.getCommenter());
            }
            if (updatedComment.getMessage() != null) {
                comment.setMessage(updatedComment.getMessage());
            }
            if (updatedComment.getDatePosted() != null) {
                comment.setDatePosted(updatedComment.getDatePosted());
            }
            if (updatedComment.getReview() != null) {
                comment.setReview(updatedComment.getReview());
            }

            Comment savedComment= commentRepository.save(comment);
            return toCommentDto(savedComment);
        }

        public void deleteComment(long id) {
            if (!commentRepository.existsById(id)) {
                throw new EntityNotFoundException("Comment" + id + "not found");
            }
            commentRepository.deleteById(id);
        }

    }
}
