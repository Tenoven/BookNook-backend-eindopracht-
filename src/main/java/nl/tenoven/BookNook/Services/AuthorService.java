package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.tenoven.BookNook.Mappers.AuthorMappers.toAuthorDto;

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

    public AuthorDto addAuthor(Author newAuthor) {
        Author savedAuthor = authorRepository.save(newAuthor);
        return toAuthorDto(savedAuthor);
    }

    public AuthorDto updateAuthor(long id, AuthorDto updatedAuthor) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Author" + id + "not found"));

        if(updatedAuthor.getName() != null ) {
            author.setName(updatedAuthor.getName());
        }
        if(updatedAuthor.getDescription() != null ) {
            author.setDescription(updatedAuthor.getDescription());
        }
        if(updatedAuthor.getPhoto() != null ) {
            author.setPhoto(updatedAuthor.getPhoto());
        }
        if(updatedAuthor.getDateOfBirth() != null ) {
            author.setDateOfBirth(updatedAuthor.getDateOfBirth());
        }

        author.setValidated(false);


        Author savedAuthor = authorRepository.save(author);
        return toAuthorDto(savedAuthor);
    }

    public AuthorDto validateAthor(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Author" + id + "not found"));
            author.setValidated(!author.isValidated());
        Author savedAuthor = authorRepository.save(author);
        return toAuthorDto(savedAuthor);
    }

    public void delete(long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author" + id + "not found");
        }
        authorRepository.deleteById(id);
    }

}
