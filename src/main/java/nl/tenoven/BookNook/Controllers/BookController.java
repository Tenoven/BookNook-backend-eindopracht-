package nl.tenoven.BookNook.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.BookDtos.BookDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookInputDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookPatchDto;
import nl.tenoven.BookNook.Dtos.BookDtos.BookShortDto;
import nl.tenoven.BookNook.Services.BookService;
import nl.tenoven.BookNook.Services.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ImageService imageService;

    public BookController(BookService bookService, ImageService imageService) {
        this.bookService = bookService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<BookShortDto>> getValidatedBooks() {
        List<BookShortDto> books = bookService.getValidatedBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/unvalidated")
    public ResponseEntity<List<BookShortDto>> getUnvalidatedBooks() {
        List<BookShortDto> books = bookService.getUnvalidatedBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        BookDto book = bookService.getBook(id);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping("/{id}/cover")
    public ResponseEntity<Resource> getBookCoverByID(@PathVariable("id") Long bookId, HttpServletRequest request) {
        BookDto dto = bookService.getBook(bookId);
        String fileName = dto.getCover().getFileName();
        Resource resource = imageService.getImage(fileName);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookInputDto dto) {
        BookDto bookDto = bookService.addBook(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookDto.getId()).toUri();

        return ResponseEntity.created(location).body(bookDto);
    }

    @PostMapping("/{id}/cover")
    public ResponseEntity<BookDto> addCoverToBookByID(@PathVariable("id") Long bookId, @RequestBody MultipartFile cover) throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/books/").path(Objects.requireNonNull(bookId.toString())).path("/cover").toUriString();
        String fileName = imageService.addImage(cover);
        BookDto book = bookService.assignCoverToBook(fileName, bookId);

        return ResponseEntity.created(URI.create(url)).body(book);
    }

    @PostMapping("/{bookId}/addAuthor/{authorId}")
    public ResponseEntity<BookDto> addAuthorToBookById(@PathVariable("bookId") Long bookId, @PathVariable("authorId") Long authorID) {

        BookDto book = bookService.addAuthorToBook(bookId, authorID);

        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDto> updateBookById(@PathVariable Long id, @RequestBody BookPatchDto newBook) {
        BookDto dto = bookService.updateBook(id, newBook);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<BookDto> validateBookDataByID(@PathVariable Long id) {
        BookDto dto = bookService.validateBook(id);
        return ResponseEntity.ok().body(dto);
    }
}
