package nl.tenoven.BookNook.Services;

import jdk.jfr.Name;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookPutDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.BookRepository;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.BookMappers.toBookDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooks() {
        // Arrange
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<BookDto> result = bookService.getBooks();

        // Assert
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        BookDto result = bookService.getBook(1L);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookNotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.getBook(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testAddBook() {
        // Arrange
        BookInputDto bookInputDto = new BookInputDto();
        Book book = new Book();
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        BookDto result = bookService.addBook(bookInputDto);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookPutDto bookPutDto = new BookPutDto();
        bookPutDto.setTitle("Updated Title");
        bookPutDto.setAuthorId(1L);
        bookPutDto.setDescription("test");
        bookPutDto.setPrice(19.99F);
        Image cover = new Image();
        bookPutDto.setCover(cover);
        bookPutDto.setAmountOfPages((short) 1);

        // Act
        BookDto result = bookService.updateBook(1L, bookPutDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", book.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @Name("noAuthorId")
    void testUpdateBookNoAuthorId() {
        //Arrange

        // Act

        // Assert
    }

    @Test
    void testUpdateBookNotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(1L, new BookPutDto()));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testValidateBook() {
        // Arrange
        Book book = new Book();
        book.setValidated(false);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookDto result = bookService.validateBook(1L);

        // Assert
        assertTrue(book.isValidated());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testValidateBookNotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.validateBook(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteBook() {
        // Arrange
        when(bookRepository.existsById(1L)).thenReturn(true);

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookNotFound() {
        // Arrange
        when(bookRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).existsById(1L);
    }

    @Test
    void testAssignCoverToBook() {
        // Arrange
        Book book = new Book();
        Image image = new Image();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(imageRepository.findById("cover.jpg")).thenReturn(Optional.of(image));
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookDto result = bookService.assignCoverToBook("cover.jpg", 1L);

        // Assert
        assertNotNull(result);
        assertEquals(image, book.getCover());
        verify(bookRepository, times(1)).findById(1L);
        verify(imageRepository, times(1)).findById("cover.jpg");
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testAssignCoverToBookNotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        when(imageRepository.findById("cover.jpg")).thenReturn(Optional.of(new Image()));

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> bookService.assignCoverToBook("cover.jpg", 1L));
    }
}
