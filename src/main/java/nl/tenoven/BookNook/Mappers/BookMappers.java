package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookShortDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMappers {
    public static BookDto toBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }
        dto.setDescription(book.getDescription());
        dto.setAmountOfPages(book.getAmountOfPages());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setCover(book.getCover());
        dto.setValidated(book.isValidated());
        if (book.getReviews() != null) {
            dto.setReviews(book.getReviews().stream().map(ReviewMappers::toReviewDto).toList());
        }

        return dto;
    }

    public static BookShortDto toBookShortDto(Book book) {
        BookShortDto dto = new BookShortDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }
        dto.setDescription(book.getDescription());
        dto.setAmountOfPages(book.getAmountOfPages());

        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setCover(book.getCover());
        dto.setValidated(book.isValidated());

        return dto;
    }

    public static Book toBook(BookInputDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());

        if (bookDto.getAuthorId() != null) {
            Author author = new Author();
            author.setId(bookDto.getAuthorId());
            book.setAuthor(author);
        }

        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setCover(bookDto.getCover());
        book.setAmountOfPages(bookDto.getAmountOfPages());
        book.setValidated(false);
        return book;
    }

}
