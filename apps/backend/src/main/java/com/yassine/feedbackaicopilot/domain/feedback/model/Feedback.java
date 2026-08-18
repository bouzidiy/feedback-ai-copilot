package com.yassine.feedbackaicopilot.domain.feedback.model;

import com.yassine.feedbackaicopilot.domain.feedback.validation.FeedbackValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public record Feedback(
        UUID id,
        String title,
        String content,
        FeedbackSource source,
        Integer rating,
        LocalDateTime createdAt
) {
    public Feedback {
        title = normalize(title);
        content = normalize(content);
        FeedbackValidator.validate(id, title, content, source, rating, createdAt);
    }

    public static Feedback create(String title, String content, FeedbackSource source, Integer rating) {
        return new Feedback(UUID.randomUUID(), title, content, source, rating, LocalDateTime.now());
    }

    public Feedback update(String title, String content, FeedbackSource source, Integer rating) {
        return new Feedback(id, title, content, source, rating, createdAt);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
