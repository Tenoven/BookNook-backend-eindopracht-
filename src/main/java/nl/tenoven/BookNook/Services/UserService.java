package nl.tenoven.BookNook.Services;

import nl.tenoven.BookNook.Dtos.UserDtos.UserDto;
import nl.tenoven.BookNook.Models.Authority;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Models.User;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import nl.tenoven.BookNook.Repositories.UserRepository;
import nl.tenoven.BookNook.exceptions.RecordNotFoundException;
import nl.tenoven.BookNook.utils.RandomStingGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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


        public List<UserDto> getUsers() {
            List<UserDto> collection = new ArrayList<>();
            List<User> list = userRepository.findAll();
            for (User user : list) {
                collection.add(toUserDto(user));
            }
            return collection;
        }

        public UserDto getUser(String username) {
            UserDto dto = new UserDto();
            Optional<User> user = userRepository.findById(username);
            if (user.isPresent()){
                dto = toUserDto(user.get());
            }else {
                throw new UsernameNotFoundException(username);
            }
            return dto;
        }

        public boolean userExists(String username) {
            return userRepository.existsById(username);
        }

        public String createUser(UserDto userDto) {
            String randomString = RandomStingGenerator.generateAlphaNumeric(20);
            userDto.setApikey(randomString);
            User newUser = userRepository.save(toUser(userDto));
            return newUser.getUsername();
        }

        public void deleteUser(String username) {
            userRepository.deleteById(username);
        }

        public void updateUser(String username, UserDto newUser) {
            if (!userRepository.existsById(username)) throw new RecordNotFoundException();
            User user = userRepository.findById(username).get();
            user.setPassword(newUser.getPassword());
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
                user.setImage(picture);
                User savedUser = userRepository.save(user);
                return toUserDto(savedUser);
            } else {
                throw new RecordNotFoundException("User or picture not found");
            }
    }
}
