package nl.tenoven.BookNook.Controllers;

import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorInputDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorPutDto;
import nl.tenoven.BookNook.Services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService)
    {this.authorService = authorService;}

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        List<AuthorDto> authors = authorService.getAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        AuthorDto author = authorService.getAuthor(id);
        return ResponseEntity.ok().body(author);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@Valid @RequestBody AuthorInputDto dto) {
        AuthorDto authorDto = authorService.addAuthor(dto);
        return  ResponseEntity.created(null).body(authorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor (@PathVariable Long id, @RequestBody AuthorPutDto newAuthor) {
        AuthorDto dto = authorService.updateAuthor(id, newAuthor);
        return ResponseEntity.ok().body(dto);
    }
}
