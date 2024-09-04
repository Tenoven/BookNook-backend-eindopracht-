package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Models.Author;

public class AuthorMappers {
    public static AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDescription(author.getDescription());
        dto.setPhoto(author.getPhoto());
        dto.setDateOfBirth(author.getDateOfBirth());
        dto.setValidated(author.isValidated());
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
