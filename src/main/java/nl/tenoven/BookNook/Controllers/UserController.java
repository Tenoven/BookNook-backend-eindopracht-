package nl.tenoven.BookNook.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import nl.tenoven.BookNook.Dtos.UserDtos.UserDto;
import nl.tenoven.BookNook.Services.ImageService;
import nl.tenoven.BookNook.Services.UserService;
import nl.tenoven.BookNook.exceptions.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

//@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {

        UserDto optionalUser = userService.getUser(userDetails.getUsername());


        return ResponseEntity.ok().body(optionalUser);

    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        UserDto userDto= userService.getUserByUsername(username);
        return userDto;
    }

    @GetMapping(value = "/{username}/picture")
    public ResponseEntity<Resource> getPicture(@PathVariable("username") String username, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        UserDto dto = userService.getUser(username);
        String filename = dto.getPicture().getFileName();
        Resource resource = imageService.getImage(filename);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileNAme" + resource.getFilename()).body(resource);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {

        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{username}/picture")
    public ResponseEntity<UserDto> addPictureToUser(@PathVariable("username") String username, @RequestBody MultipartFile picture) throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/").path(Objects.requireNonNull(username)).path("/picture").toUriString();
        String fileName = imageService.addImage(picture);
        UserDto user = userService.assignPicturetoUser(fileName, username);

        return ResponseEntity.created(URI.create(url)).body(user);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto dto, @AuthenticationPrincipal UserDetails userDetails) {

        userService.updateUser(username, dto, userDetails);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
