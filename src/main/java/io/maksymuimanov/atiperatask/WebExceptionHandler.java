package io.maksymuimanov.atiperatask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(UserReposNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserReposNotFoundException(UserReposNotFoundException exception) {
        System.out.println("I am here :DD");
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
