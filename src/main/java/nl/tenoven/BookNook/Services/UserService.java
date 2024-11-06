package nl.tenoven.BookNook.Services;

import nl.tenoven.BookNook.Config.SpringSecurityConfig;
import nl.tenoven.BookNook.Dtos.UserDtos.UserDto;
import nl.tenoven.BookNook.Models.Authority;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Models.User;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.Repositories.UserRepository;
import nl.tenoven.BookNook.exceptions.NotAuthorizedException;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import nl.tenoven.BookNook.utils.RandomStingGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static nl.tenoven.BookNook.Mappers.UserMappers.toUser;
import static nl.tenoven.BookNook.Mappers.UserMappers.toUserDto;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public UserService(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public UserDto getUser(String username) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException("User not found: " + username);

        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = toUserDto(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public UserDto getUserByUsername (String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found: " + username));
        return toUserDto(user);

    }

    public String createUser(UserDto userDto) {
        if (userRepository.existsById(userDto.username)) {
            throw new IllegalArgumentException("user " +userDto.getUsername() + " already exists" );
        }

        String randomString = RandomStingGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        String encodedPassword = SpringSecurityConfig.passwordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser( String username, UserDto newUser, UserDetails userDetails) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException("User not found: " + username);
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!username.equals(userDetails.getUsername()) && !isAdmin) {
            throw new NotAuthorizedException("You are not authorized for this action");
        }

        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        if (newUser.getPassword() != null) {
        user.setPassword(newUser.getPassword());
        }
        if (newUser.getUsername() != null) {
            user.setUsername(newUser.getUsername());
        }
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = toUserDto(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }


    public UserDto assignPicturetoUser(String fileName, String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        Optional<Image> optionalImage = imageRepository.findById(fileName);

        if (optionalUser.isPresent() && optionalImage.isPresent()) {
            Image picture = optionalImage.get();
            User user = optionalUser.get();
            user.setPicture(picture);
            User savedUser = userRepository.save(user);
            return toUserDto(savedUser);
        } else {
            throw new RecordNotFoundException("User or picture not found");
        }
    }
}
