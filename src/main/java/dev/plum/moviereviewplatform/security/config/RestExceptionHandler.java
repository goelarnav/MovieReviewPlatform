package dev.plum.moviereviewplatform.security.config;

//import com.plum.jwt.backend.dtos.ErrorDto;
//import com.sergio.jwt.backend.exceptions.AppException;
import dev.plum.moviereviewplatform.security.dtos.ErrorDto;
import dev.plum.moviereviewplatform.security.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorDto.builder().message(ex.getMessage()).build());
    }
}