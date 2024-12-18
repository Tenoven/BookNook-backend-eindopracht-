package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorInputDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorShortDto;
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
        if (author.getBooks() != null) {
            dto.setBooks(author.getBooks().stream().map(BookMappers::toBookDto).toList());
        }

        return dto;
    }

    public static AuthorShortDto toAuthorShortDto(Author author) {
        AuthorShortDto dto = new AuthorShortDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDescription(author.getDescription());
        dto.setPhoto(author.getPhoto());
        dto.setDateOfBirth(author.getDateOfBirth());
        dto.setValidated(author.isValidated());

        return dto;
    }

    public static Author toAuthor(AuthorInputDto dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setDescription(dto.getDescription());
        author.setPhoto(dto.getPhoto());
        author.setDateOfBirth(dto.getDateOfBirth());
        author.setValidated(false);

        return author;
    }
}
