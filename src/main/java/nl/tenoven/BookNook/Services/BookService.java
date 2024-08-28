package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private Bookrepository bookrepository;

    public BookService(BookRepository bookRepository) {
        this.bookrepository = bookRepository;
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

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setDescription(updatedBook.getDescription());
        book.setPrice(updatedBook.getPrice());
        book.setCover(updatedBook.getCover());
        book.setAmountOfPages(updatedBook.getAmountOfPages());
        book.setValidated(false);

        Book savedBook= bookRepository.save(book);
        return toBookDto(savedBook);
    }

    public void deleteBook(long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book" + id + "not found");
        }
        bookRepository.deleteById(id);
    }

    public static BookDto toBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());
        dto.setCover(book.getCover());
        dto.setAmountOfPages(book.getAmountOfPages());
        dto.setValidated(false);

        return dto;
    }

    public Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setCover(bookDto.getCover());
        book.setAmountOfPages(bookDto.getAmountOfPages());
        book.setValidated(false);
        return book;
    }

}
