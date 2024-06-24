package dev.plum.moviereviewplatform.security.services;

//import com.sergio.jwt.backend.dtos.CredentialsDto;
//import com.sergio.jwt.backend.dtos.SignUpDto;
//import com.sergio.jwt.backend.dtos.UserDto;
//import com.sergio.jwt.backend.entites.User;
//import com.sergio.jwt.backend.exceptions.AppException;
//import com.sergio.jwt.backend.mappers.UserMapper;
//import com.sergio.jwt.backend.repositories.UserRepository;
import dev.plum.moviereviewplatform.security.dtos.CredentialsDto;
import dev.plum.moviereviewplatform.security.dtos.SignUpDto;
import dev.plum.moviereviewplatform.security.dtos.UserDto;
import dev.plum.moviereviewplatform.security.entities.User;
import dev.plum.moviereviewplatform.security.exceptions.AppException;
import dev.plum.moviereviewplatform.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = signUpDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return toUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return toUserDto(user);
    }

    private static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .build();
    }

    private static User signUpDtoToUser(SignUpDto signUpDto) {
        return User.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .login(signUpDto.getLogin())
                .build();
    }

    //returns ObjectId associated with a username
    public String findUserId(String login){
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        return user.getId();
    }

}