package nl.tenoven.BookNook.Services;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setValidated(true);  // Ensure the book is validated
        Book book2 = new Book();
        book2.setId(2L);
        book2.setValidated(true);  // Ensure the book is validated
        Book book3 = new Book();
        book3.setId(3L);
        book3.setValidated(true);  // Ensure the book is validated
        Book book4 = new Book();
        book4.setId(4L);
        book4.setValidated(true);  // Ensure the book is validated

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        when(bookRepository.findAllByValidated(true)).thenReturn(bookList);

        // Act
        List<BookShortDto> result = bookService.getValidatedBooks();

        // Assert
        assertEquals(4, result.size());
        verify(bookRepository, times(1)).findAllByValidated(true);
    }

    @Test
    void testGetUnvalidatedBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setValidated(false);  // Ensure the book is validated
        Book book2 = new Book();
        book2.setId(2L);
        book2.setValidated(false);  // Ensure the book is validated
        Book book3 = new Book();
        book3.setId(3L);
        book3.setValidated(false);  // Ensure the book is validated
        Book book4 = new Book();
        book4.setId(4L);
        book4.setValidated(false);  // Ensure the book is validated

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        when(bookRepository.findAllByValidated(false)).thenReturn(bookList);

        // Act
        List<BookShortDto> result = bookService.getUnvalidatedBooks();

        // Assert
        assertEquals(4, result.size());
        verify(bookRepository, times(1)).findAllByValidated(false);
    }

    @Test
    void testAddAuthorToBook_Success() {
        // Arrange
        Long bookId = 1L;
        Long authorId = 1L;

        Book book = new Book();
        book.setId(bookId);

        Author author = new Author();
        author.setId(authorId);
        author.setName("Pipi Langkous");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookDto result = bookService.addAuthorToBook(bookId, authorId);

        // Assert
        assertNotNull(result);
        assertEquals("Pipi Langkous", result.getAuthorName());
        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testAddAuthorToBook_BookNotFound() {
        // Arrange
        Long bookId = 1L;
        Long authorId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                bookService.addAuthorToBook(bookId, authorId));

        assertEquals("Book or Author not found.", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, never()).findById(anyLong());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testAddAuthorToBook_AuthorNotFound() {
        // Arrange
        Long bookId = 1L;
        Long authorId = 1L;

        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                bookService.addAuthorToBook(bookId, authorId));

        assertEquals("Book or Author not found.", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, never()).save(any(Book.class));
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
        bookInputDto.setIsbn("1234567890");
        Book book = new Book();
        book.setIsbn("1234567890");

        when(bookRepository.existsByIsbn(bookInputDto.getIsbn())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        BookDto result = bookService.addBook(bookInputDto);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).existsByIsbn(bookInputDto.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testAddBook_WithExistingIsbn() {
        // Arrange
        BookInputDto bookInputDto = new BookInputDto();
        bookInputDto.setIsbn("1234567890");

        when(bookRepository.existsByIsbn(bookInputDto.getIsbn())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookService.addBook(bookInputDto)
        );

        assertEquals("A book with this ISBN already exists.", exception.getMessage());
        verify(bookRepository, times(1)).existsByIsbn(bookInputDto.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));  // Ensure save is never called
    }


    @Test
    void testUpdateBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookPatchDto bookPatchDto = new BookPatchDto();
        bookPatchDto.setTitle("Updated Title");
        bookPatchDto.setDescription("test");
        bookPatchDto.setPrice(19.99F);
        bookPatchDto.setAmountOfPages((short) 1);

        // Act
        BookDto result = bookService.updateBook(1L, bookPatchDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", book.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }


    @Test
    void testUpdateBookNotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(1L, new BookPatchDto()));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBook_WithAuthorIdUpdate() {
        // Arrange
        Long bookId = 1L;
        Long newAuthorId = 2L;

        Author author = new Author();
        author.setId(newAuthorId);
        author.setName("Manoes");

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setAuthor(author);
        updatedBook.setId(bookId);

        BookPatchDto bookPatchDto = new BookPatchDto();
        bookPatchDto.setAuthorId(newAuthorId);

        // Mock fetching the book by its ID
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        // Mock saving the book after updating
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Act
        BookDto result = bookService.updateBook(bookId, bookPatchDto);

        // Assert
        assertNotNull(result);
        assertEquals("Manoes", result.getAuthorName()); // should now match
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(existingBook);
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
