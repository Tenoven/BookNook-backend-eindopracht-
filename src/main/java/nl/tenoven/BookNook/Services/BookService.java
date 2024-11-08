package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookPatchDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookShortDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.AuthorRepository;
import nl.tenoven.BookNook.Repositories.BookRepository;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.BookMappers.*;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, ImageRepository imageRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.imageRepository = imageRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookShortDto> getValidatedBooks() {

        List<Book> books = bookRepository.findAllByValidated(true);
        List<BookShortDto> bookShortDtos = new ArrayList<>();


        for (Book book : books) {
            bookShortDtos.add(toBookShortDto(book));
        }
        return bookShortDtos;
    }

    public List<BookShortDto> getUnvalidatedBooks() {

        List<Book> books = bookRepository.findAllByValidated(false);

        List<BookShortDto> bookShortDtos = new ArrayList<>();

        for (Book book : books) {
            bookShortDtos.add(toBookShortDto(book));
        }
        return bookShortDtos;
    }

    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));
        return toBookDto(book);
    }


    public BookDto addBook(BookInputDto newBook) {
        Book savedBook = toBook(newBook);

        if (bookRepository.existsByIsbn(savedBook.getIsbn())) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        Book book = bookRepository.save(savedBook);
        return toBookDto(book);
    }


    public BookDto addAuthorToBook(Long bookId, Long authorId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new RecordNotFoundException("Book or Author not found.");
        }
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);

        if (optionalAuthor.isEmpty()) {
            throw new RecordNotFoundException("Book or Author not found.");
        }
        Book book = optionalBook.get();
        Author author = optionalAuthor.get();
        book.setAuthor(author);
        Book updatedBook = bookRepository.save(book);
        return toBookDto(updatedBook);
    }

    public BookDto updateBook(Long id, BookPatchDto updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book" + id + "not found"));

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
        if (updatedBook.getAmountOfPages() != null) {
            book.setAmountOfPages(updatedBook.getAmountOfPages());
        }

        book.setValidated(false);

        Book savedBook = bookRepository.save(book);
        return toBookDto(savedBook);
    }

    public BookDto validateBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book" + id + "not found"));
        book.setValidated(true);
        Book savedBook = bookRepository.save(book);
        return toBookDto(savedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book" + id + "not found");
        }
        bookRepository.deleteById(id);
    }

    public BookDto assignCoverToBook(String fileName, Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Image> optionalCover = imageRepository.findById(fileName);

        if (optionalBook.isPresent() && optionalCover.isPresent()) {
            Image cover = optionalCover.get();
            Book book = optionalBook.get();
            book.setCover(cover);
            Book savedBook = bookRepository.save(book);
            return toBookDto(savedBook);
        } else {
            throw new RecordNotFoundException("Book or cover not found");
        }
    }
}
