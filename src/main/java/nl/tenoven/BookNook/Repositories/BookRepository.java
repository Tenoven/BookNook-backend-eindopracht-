package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}
