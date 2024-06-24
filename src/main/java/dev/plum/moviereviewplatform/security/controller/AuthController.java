package dev.plum.moviereviewplatform.security.controller;

//import com.sergio.jwt.backend.config.UserAuthenticationProvider;
//import com.sergio.jwt.backend.dtos.CredentialsDto;
//import com.sergio.jwt.backend.dtos.SignUpDto;
//import com.sergio.jwt.backend.dtos.UserDto;
//import com.sergio.jwt.backend.services.UserService;
import dev.plum.moviereviewplatform.security.config.UserAuthenticationProvider;
import dev.plum.moviereviewplatform.security.dtos.CredentialsDto;
import dev.plum.moviereviewplatform.security.dtos.SignUpDto;
import dev.plum.moviereviewplatform.security.dtos.UserDto;
import dev.plum.moviereviewplatform.security.services.UserService;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Validated CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Validated SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

}
