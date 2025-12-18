package io.maksymuimanov.atiperatask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = UserReposNotFoundException.NOT_FOUND_REASON)
public class UserReposNotFoundException extends RuntimeException {
    public static final String NOT_FOUND_REASON = "User repos are not found! Check if the username is valid!";
}