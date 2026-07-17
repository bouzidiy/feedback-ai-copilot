package com.yassine.feedbackaicopilot.infrastructure.web.common;

import com.yassine.feedbackaicopilot.domain.feedback.exception.InvalidFeedbackException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFeedbackException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleInvalidFeedbackException(InvalidFeedbackException exception) {
        return new ApiError(
                "INVALID_FEEDBACK",
                exception.getMessage(),
                Instant.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception exception) {
        return new ApiError(
                "INTERNAL_SERVER_ERROR",
                exception.getMessage(),
                Instant.now()
        );
    }

    public record ApiError(
            String code,
            String message,
            Instant timestamp
    ) {
    }
}
