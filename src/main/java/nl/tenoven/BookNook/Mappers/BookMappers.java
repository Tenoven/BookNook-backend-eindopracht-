package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Models.Author;
import nl.tenoven.BookNook.Models.Book;
import nl.tenoven.BookNook.Services.BookService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMappers {
    public static BookDto toBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorName(book.getAuthor().getName());
        dto.setDescription(book.getDescription());
        dto.setAmountOfPages(book.getAmountOfPages());
        dto.setPrice(book.getPrice());
        dto.setCover(book.getCover());
        dto.setValidated(book.isValidated());

        return dto;
    }

    public static Book toBook(BookInputDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());

        Author author = new Author();
        author.setId(bookDto.getAuthorId());
        book.setAuthor(author);

        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setCover(bookDto.getCover());
        book.setAmountOfPages(bookDto.getAmountOfPages());
        book.setValidated(false);
        return book;
    }

    public static List<BookDto> toDtoList(List<Book> books){
        List<BookDto> dtoList = new ArrayList<>();
        for (Book b : books){
            dtoList.add(toBookDto(b));
        }
        return dtoList;
    }



}
