package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMappers {

    public static BookDto toBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setAmountOfPages(book.getAmountOfPages());
        dto.setPrice(book.getPrice());
        dto.setCover(book.getCover());
        dto.setValidated(book.isValidated());

        return dto;
    }

    public Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setCover(bookDto.getCover());
        book.setAmountOfPages(bookDto.getAmountOfPages());
        book.setValidated(false);
        return book;
    }

}
