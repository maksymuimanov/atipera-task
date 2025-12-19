package io.maksymuimanov.atiperatask;

public record ApiError(Integer status,
                       String message) {
}
