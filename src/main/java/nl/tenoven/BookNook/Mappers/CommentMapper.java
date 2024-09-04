package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.CommentDtos.CommentDto;
import nl.tenoven.BookNook.Models.Comment;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setCommenter(comment.getCommenter());
        dto.setMessage(comment.getMessage());
        dto.setDatePosted(comment.getDatePosted());
        dto.setReview(comment.getReview());


        return dto;
    }

    public Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setCommenter(commentDto.getCommenter());
        comment.setMessage(commentDto.getMessage());
        comment.setDatePosted(commentDto.getDatePosted());
        comment.setReview(commentDto.getReview());
        return comment;
    }

}
