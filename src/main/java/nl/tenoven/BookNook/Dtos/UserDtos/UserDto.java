package nl.tenoven.BookNook.Dtos.UserDtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Authority;
import nl.tenoven.BookNook.Models.Image;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    @Size(max = 30, message = "Username cannot exceed 30 characters")
    public String username;
    @Size(max = 30, message = "Password cannot exceed 30 characters")
    public String password;
    public Image picture;
    public String apikey;
    public Set<Authority> authorities;
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Please enter a valid email address"
    )
    private String email;
}
