package nl.tenoven.BookNook.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorInputDto;
import nl.tenoven.BookNook.Dtos.AuthorDtos.AuthorPutDto;
import nl.tenoven.BookNook.Services.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ImageService imageService;

    public AuthorController(AuthorService authorService, ImageService imageService) {
        this.authorService = authorService;
        this.imageService = imageService;
    }

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

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getAuthorPhoto(@PathVariable("id") Long authorId, HttpServletRequest request){
        AuthorDto dto = authorService.getAuthor(authorId);
        String fileName = dto.getPhoto().getFileName();
        Resource resource = imageService.getImage(fileName);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            /*
            "application/octet-steam" is de generieke mime type voor byte data.
            Het is beter om een specifiekere mimetype te gebruiken, zoals "image/jpeg".
            Mimetype is nodig om de frontend te laten weten welke soort data het is.
            Met de juiste MimeType en Content-Disposition, kun je de plaatjes of PDFs die je upload
            zelfs in de browser weergeven.
             */
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@Valid @RequestBody AuthorInputDto dto) {
        AuthorDto authorDto = authorService.addAuthor(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(authorDto.getId()).toUri();

        return  ResponseEntity.created(location).body(authorDto);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<AuthorDto> addPhotoToAuthor(@PathVariable("id") Long authorId,
                                                     @RequestBody MultipartFile photo)
            throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/Authors/")
                .path(Objects.requireNonNull(authorId.toString()))
                .path("/photo")
                .toUriString();
        String fileName = imageService.addImage(photo);
        AuthorDto author = authorService.assignPhotoToAuthor(fileName, authorId);

        return ResponseEntity.created(URI.create(url)).body(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor (@PathVariable Long id, @RequestBody AuthorPutDto newAuthor) {
        AuthorDto dto = authorService.updateAuthor(id, newAuthor);
        return ResponseEntity.ok().body(dto);
    }
}
