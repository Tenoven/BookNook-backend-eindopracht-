package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.tenoven.BookNook.Mappers.BookMappers.toBookDto;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book" + id + "not found"));
        return book;
    }

    public BookDto addBook(Book newBook) {
        Book savedBook = bookRepository.save(newBook);
        return toBookDto(savedBook);
    }

    public BookDto updateBook(long id, Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book" + id + "not found"));

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            book.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getDescription() != null) {
            book.setDescription(updatedBook.getDescription());
        }
        if (updatedBook.getPrice() != null) {
            book.setPrice(updatedBook.getPrice());
        }
        if (updatedBook.getCover() != null) {
            book.setCover(updatedBook.getCover());
        }
        if (updatedBook.getAmountOfPages() != null) {
            book.setAmountOfPages(updatedBook.getAmountOfPages());
        }

        book.setValidated(false);


        Book savedBook= bookRepository.save(book);
        return toBookDto(savedBook);
    }

    public BookDto validateBook(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book" + id + "not found"));
        book.setValidated(!book.isValidated());
        Book savedBook = bookRepository.save(book);
        return toBookDto(savedBook);
    }

    public void deleteBook(long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book" + id + "not found");
        }
        bookRepository.deleteById(id);
    }

}
