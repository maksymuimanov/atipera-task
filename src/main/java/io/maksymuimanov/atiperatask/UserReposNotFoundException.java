package io.maksymuimanov.atiperatask;

public class UserReposNotFoundException extends RuntimeException {
    public static final String NOT_FOUND_REASON = "User [%s] repos are not found! Check if the username is valid!";

    public UserReposNotFoundException(String username) {
        super(NOT_FOUND_REASON.formatted(username));
    }
}