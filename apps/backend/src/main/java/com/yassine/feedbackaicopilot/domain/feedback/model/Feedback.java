package com.yassine.feedbackaicopilot.domain.feedback.model;

import com.yassine.feedbackaicopilot.domain.feedback.validation.FeedbackValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public record Feedback(
        UUID id,
        String content,
        FeedbackSource source,
        Integer rating,
        LocalDateTime createdAt
) {
    public Feedback {
        content = normalize(content);

        FeedbackValidator.validate(
                id,
                content,
                source,
                rating,
                createdAt
        );
    }

    public static Feedback create(
            String content,
            FeedbackSource source,
            Integer rating
    ) {
        return new Feedback(
                UUID.randomUUID(),
                content,
                source,
                rating,
                LocalDateTime.now()
        );
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
