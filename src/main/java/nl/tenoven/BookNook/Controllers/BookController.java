package nl.tenoven.BookNook.Controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookPutDto;
import nl.tenoven.BookNook.Services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id")Long id) {
        BookDto book = bookService.getBook(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookInputDto dto) {
        BookDto bookDto = bookService.addBook(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookDto.getId()).toUri();

        return ResponseEntity.created(location).body(bookDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id,@RequestBody BookPutDto newBook ) {
        BookDto dto = bookService.updateBook(id, newBook);
        return ResponseEntity.ok().body(dto);
    }
}
