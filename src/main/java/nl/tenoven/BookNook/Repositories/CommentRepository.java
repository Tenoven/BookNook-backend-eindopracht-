package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment, Long> {
}
