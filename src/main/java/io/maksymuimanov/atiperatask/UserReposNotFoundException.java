package io.maksymuimanov.atiperatask;

public class UserReposNotFoundException extends RuntimeException {
    public static final String NOT_FOUND_REASON = "User repos are not found! Check if the username is valid!";

    public UserReposNotFoundException() {
        super(NOT_FOUND_REASON);
    }
}