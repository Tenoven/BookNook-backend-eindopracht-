package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {


    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() { return authorRepository.findAll();}

    public Author getAuthor(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Author" + id + "not found"));
        return author;
    }

    public AuthorDto addAuthor(Author author) {
        Author savedAuthor = authorRepository.save(newAuthor);
        return toAuthorDto(savedAuthor);
    }

    public AuthorDto updateAuthor(long id, Author updatedAuthor) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Author" + id + "not found"));

        author.setName(updatedAuthor.getName());
        author.setDescription(updatedAuthor.getDescription());
        author.setPhoto(updatedAuthor.getPhoto());
        author.setDateOfBirth(updatedAuthor.getDateOfBirth());
        author.setValidated(false);

        Author savedAuthor = authorRepository.save(author);
        return toAuthorDto(savedAuthor);
    }

    public void delete(long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author" + id + "not found");
        }
        authorRepository.deleteById(id);
    }

    public static AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new Author();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDescription(author.getDescription());
        dto.setPhoto(author.getPhoto());
        dto.setDateOfBirth(author.getDateOfBirth());
        dto.setValidated(false);
        dto.setBooks(author.getBooks());

        return dto;
    }

    public Author toAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setDescription(authorDto.getDescription());
        author.setPhoto(authorDto.getPhoto());
        author.setDateOfBirth(authorDto.getDateOfBirth());
        author.setValidated(false);
        author.setBooks(authorDto.getBooks());

        return author;
    }
}
