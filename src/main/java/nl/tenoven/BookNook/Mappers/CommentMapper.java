package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.CommentDtos.CommentDto;
import nl.tenoven.BookNook.Dtos.CommentDtos.CommentInputDto;
import nl.tenoven.BookNook.Models.Comment;
import nl.tenoven.BookNook.Models.Review;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setCommenter(comment.getCommenter());
        dto.setMessage(comment.getMessage());
        dto.setDatePosted(comment.getDatePosted());
        dto.setReviewId(comment.getReview().getId());


        return dto;
    }

    public static Comment toComment(CommentInputDto commentDto) {
        Comment comment = new Comment();
        comment.setCommenter(commentDto.getCommenter());
        comment.setMessage(commentDto.getMessage());
        comment.setDatePosted(commentDto.getDatePosted());

        Review review = new Review();
        review.setId(commentDto.getReviewId());
        comment.setReview(review);

        return comment;
    }

}
