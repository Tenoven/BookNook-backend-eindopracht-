package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorInputDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorPatchDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorShortDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.AuthorRepository;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.tenoven.BookNook.Mappers.AuthorMappers.*;


@Service
public class AuthorService {


    private final AuthorRepository authorRepository;
    private final ImageRepository imageRepository;


    public AuthorService(AuthorRepository authorRepository, ImageRepository imageRepository) {
        this.authorRepository = authorRepository;
        this.imageRepository = imageRepository;
    }

    public List<AuthorShortDto> getAuthors() {
        List<Author> authors = authorRepository.findAllByValidated(true);
        List<AuthorShortDto> authorShortDtos = new ArrayList<>();

        for (Author author : authors) {
            authorShortDtos.add(toAuthorShortDto(author));
        }
        return authorShortDtos;
    }

    public List<AuthorShortDto> getUnvalidatedAuthors() {
        List<Author> authors = authorRepository.findAllByValidated(false);
        List<AuthorShortDto> authorShortDtos = new ArrayList<>();

        for (Author author : authors) {
            authorShortDtos.add(toAuthorShortDto(author));
        }
        return authorShortDtos;
    }

    public AuthorDto getAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author" + id + "not found"));
        return toAuthorDto(author);
    }


    public AuthorDto addAuthor(AuthorInputDto newAuthor) {
        Author savedAuthor = toAuthor(newAuthor);
        Author author = authorRepository.save(savedAuthor);
        return toAuthorDto(author);
    }

    public AuthorDto updateAuthor(Long id, AuthorPatchDto updatedAuthor) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author" + id + "not found"));

        if (updatedAuthor.getName() != null) {
            author.setName(updatedAuthor.getName());
        }
        if (updatedAuthor.getDescription() != null) {
            author.setDescription(updatedAuthor.getDescription());
        }
        if (updatedAuthor.getDateOfBirth() != null) {
            author.setDateOfBirth(updatedAuthor.getDateOfBirth());
        }

        author.setValidated(false);


        Author savedAuthor = authorRepository.save(author);
        return toAuthorDto(savedAuthor);
    }

    public AuthorDto validateAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author" + id + "not found"));
        author.setValidated(true);
        Author savedAuthor = authorRepository.save(author);
        return toAuthorDto(savedAuthor);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author" + id + "not found");
        }
        authorRepository.deleteById(id);
    }

    public AuthorDto assignPhotoToAuthor(String fileName, Long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Image> optionalPhoto = imageRepository.findById(fileName);

        if (optionalAuthor.isPresent() && optionalPhoto.isPresent()) {
            Image photo = optionalPhoto.get();
            Author author = optionalAuthor.get();
            author.setPhoto(photo);
            Author savedAuthor = authorRepository.save(author);
            return toAuthorDto(savedAuthor);
        } else {
            throw new RecordNotFoundException("Author or photo not found");
        }
    }
}
