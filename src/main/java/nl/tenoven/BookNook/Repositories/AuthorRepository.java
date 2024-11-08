package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAllByValidated(boolean validated);
}
