package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookPutDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Repositories.AuthorRepository;
import nl.tenoven.BookNook.Repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.BookMappers.toBookDto;
import static nl.tenoven.BookNook.Mappers.BookMappers.toBook;


@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getBooks() {

       List<Book> Books = bookRepository.findAll();
       List<BookDto> bookDto = new ArrayList<>();

        for (Book book: Books) {
            bookDto.add(toBookDto(book));
        }
        return bookDto;
    }

    public BookDto getBook(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book " + id + " not found"));
        return toBookDto(book);
    }

    public BookDto addBook(BookInputDto newBook) {
        Book savedBook = toBook(newBook);
        Book book = bookRepository.save(savedBook);
        return toBookDto(book);
    }

    public BookDto updateBook(long id, BookPutDto updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book" + id + "not found"));

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthorId() != null) {
            Author author = new Author();
            author.setId(updatedBook.getAuthorId());
            book.setAuthor(author);
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
