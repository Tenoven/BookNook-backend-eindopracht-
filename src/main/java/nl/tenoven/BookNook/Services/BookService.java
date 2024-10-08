package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookPutDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.BookRepository;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.BookMappers.toBookDto;
import static nl.tenoven.BookNook.Mappers.BookMappers.toBook;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;

    public BookService(BookRepository bookRepository, ImageRepository imageRepository) {
        this.bookRepository = bookRepository;
        this.imageRepository = imageRepository;
    }

    public List<BookDto> getBooks() {

       List<Book> books = bookRepository.findAll();
       List<BookDto> bookDtos = new ArrayList<>();

        for (Book book: books) {
            bookDtos.add(toBookDto(book));
        }
        return bookDtos;
    }

    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book " + id + " not found"));
        return toBookDto(book);
    }

    public BookDto addBook(BookInputDto newBook) {
        Book savedBook = toBook(newBook);
        Book book = bookRepository.save(savedBook);
        return toBookDto(book);
    }

    public BookDto updateBook(Long id, BookPutDto updatedBook) {
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

    public BookDto validateBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book" + id + "not found"));
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
            Book book  = optionalBook.get();
            book.setCover(cover);
            Book savedBook = bookRepository.save(book);
            return toBookDto(savedBook);
        } else {
            throw new RecordNotFoundException("Book or cover not found");
        }
    }

}
