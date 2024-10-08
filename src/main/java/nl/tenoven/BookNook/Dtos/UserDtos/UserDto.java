package nl.tenoven.BookNook.Dtos.UserDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Authority;
import nl.tenoven.BookNook.Models.Image;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    public String username;
    public String password;
    public Image image;
    public String apikey;
    public Set<Authority> authorities;
}
