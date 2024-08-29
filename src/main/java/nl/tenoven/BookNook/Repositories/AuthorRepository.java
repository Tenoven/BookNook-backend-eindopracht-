package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
