package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorInputDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorPatchDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorShortDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.AuthorRepository;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private Image image;
    private AuthorDto authorDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        image = new Image();
        image.setFileName("profile.jpg");

        authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
    }

    @Test
    void testGetValidatedAuthors() {
        // Arrange
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Doe");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane Smith");

        when(authorRepository.findAllByValidated(true)).thenReturn(Arrays.asList(author1, author2));

        // Act
        List<AuthorShortDto> authors = authorService.getAuthors();

        // Assert
        assertEquals(2, authors.size());
        assertEquals("John Doe", authors.get(0).getName());
        assertEquals("Jane Smith", authors.get(1).getName());
    }

    @Test
    void testGetUnvalidatedAuthors() {
        // Arrange
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Doe");
        author1.setValidated(false);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane Smith");
        author2.setValidated(false);

        when(authorRepository.findAllByValidated(false)).thenReturn(Arrays.asList(author1, author2));

        // Act
        List<AuthorShortDto> authors = authorService.getUnvalidatedAuthors();

        // Assert
        assertEquals(2, authors.size());
        assertEquals("John Doe", authors.get(0).getName());
        assertEquals("Jane Smith", authors.get(1).getName());
    }
    @Test
    void testGetAuthor() {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // Act
        AuthorDto result = authorService.getAuthor(1L);

        // Assert
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testAddAuthor() {
        // Arrange
        AuthorInputDto newAuthor = new AuthorInputDto();
        newAuthor.setName("New Author");

        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setName("New Author");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // Act
        AuthorDto result = authorService.addAuthor(newAuthor);

        // Assert
        assertEquals("New Author", result.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthor() {
        // Arrange
        List<Book> bookTitles = new ArrayList<>();

        Author author = new Author();
        author.setId(1L);
        author.setName("Old Name");


        AuthorPatchDto updatedAuthor = new AuthorPatchDto();
        updatedAuthor.setName("Updated Name");
        updatedAuthor.setDescription("test");

        updatedAuthor.setDateOfBirth("11-11-2011");


        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);

        // Act
        AuthorDto result = authorService.updateAuthor(1L, updatedAuthor);

        // Assert
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Name", author.getName());
    }

    @Test
    void testValidateAuthor() {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setValidated(false);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);

        // Act
        AuthorDto result = authorService.validateAuthor(1L);

        // Assert
        assertTrue(author.isValidated());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testDeleteAuthor() {
        // Arrange
        when(authorRepository.existsById(1L)).thenReturn(true);

        // Act
        authorService.deleteAuthor(1L);

        // Assert
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAuthorNotFound() {
        // Arrange
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> authorService.getAuthor(99L));
    }

    @Test
    void testDeleteAuthorNotFound() {
        // Arrange
        when(authorRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthor(99L));
    }

    @Test
    void testAssignPhotoToAuthor_Success() {
        // Arrange
        String fileName = "profile.jpg";
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(imageRepository.findById(fileName)).thenReturn(Optional.of(image));
        when(authorRepository.save(author)).thenReturn(author); // Mock save method

        // Act
        AuthorDto result = authorService.assignPhotoToAuthor(fileName, authorId);

        // Assert
        assertEquals(authorDto.getId(), result.getId());
        assertEquals(authorDto.getName(), result.getName());
        assertEquals(image, author.getPhoto()); // Verify that the photo was set
        verify(authorRepository).save(author); // Ensure save was called
    }


    @Test
    void testAssignPhotoToAuthor_PhotoNotFound() {
        // Arrange
        String fileName = "profile.jpg";
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(imageRepository.findById(fileName)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                authorService.assignPhotoToAuthor(fileName, authorId)
        );

        assertEquals("Author or photo not found", exception.getMessage());
        verify(authorRepository, never()).save(any(Author.class)); // Ensure save is not called
    }

}
