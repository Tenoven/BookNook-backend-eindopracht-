package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

        private Commentrepository commentrepository;

        public CommentService(CommentRepository commentRepository) {
            this.commentrepository = commentRepository;
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

            comment.setTitle(updatedComment.getTitle());
            comment.setAuthor(updatedComment.getAuthor());
            comment.setDescription(updatedComment.getDescription());
            comment.setPrice(updatedComment.getPrice());
            comment.setCover(updatedComment.getCover());
            comment.setAmountOfPages(updatedComment.getAmountOfPages());
            comment.setValidated(false);

            Comment savedComment= commentRepository.save(comment);
            return toCommentDto(savedComment);
        }

        public void deleteComment(long id) {
            if (!commentRepository.existsById(id)) {
                throw new EntityNotFoundException("Comment" + id + "not found");
            }
            commentRepository.deleteById(id);
        }

        public static CommentDto toCommentDto(Comment comment) {
            CommentDto dto = new CommentDto();
            dto.setId(comment.getId());
            dto.setTitle(comment.getTitle());
            dto.setAuthor(comment.getAuthor());
            dto.setDescription(comment.getDescription());
            dto.setPrice(comment.getPrice());
            dto.setCover(comment.getCover());
            dto.setAmountOfPages(comment.getAmountOfPages());
            dto.setValidated(false);

            return dto;
        }

        public Comment toComment(CommentDto commentDto) {
            Comment comment = new Comment();
            comment.setId(commentDto.getId());
            comment.setTitle(commentDto.getTitle());
            comment.setAuthor(commentDto.getAuthor());
            comment.setDescription(commentDto.getDescription());
            comment.setPrice(commentDto.getPrice());
            comment.setCover(commentDto.getCover());
            comment.setAmountOfPages(commentDto.getAmountOfPages());
            comment.setValidated(false);
            return comment;
        }

    }
}
