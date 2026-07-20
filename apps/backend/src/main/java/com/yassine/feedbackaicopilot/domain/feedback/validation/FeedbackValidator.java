package com.yassine.feedbackaicopilot.domain.feedback.validation;

import com.yassine.feedbackaicopilot.domain.common.ValidationRule;
import com.yassine.feedbackaicopilot.domain.feedback.exception.InvalidFeedbackException;
import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.yassine.feedbackaicopilot.domain.common.ValidationRule.rule;
import static org.springframework.util.StringUtils.hasText;

public final class FeedbackValidator {

    private static final int MIN_CONTENT_LENGTH = 3;
    private static final int MAX_CONTENT_LENGTH = 2000;
    private static final int MIN_TITLE_LENGTH = 3;
    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;


    private static final List<ValidationRule<FeedbackValidationData>> RULES = List.of(
            rule(
                    "id",
                    "Feedback id is required",
                    data -> data.id() == null
            ),
            rule(
                    "title",
                    "Feedback title is required",
                    data -> data.title() == null || data.title().isBlank()
            ),
            rule(
                    "title",
                    "Feedback title must contain at least 3 characters",
                    data -> hasText(data.title()) && data.title().length() < MIN_TITLE_LENGTH
            ),
            rule(
                    "title",
                    "Feedback title must not exceed 255 characters",
                    data -> hasText(data.title()) && data.title().length() > MAX_TITLE_LENGTH
            ),
            rule(
                    "content",
                    "Feedback content is required",
                    data -> data.content() == null || data.content().isBlank()
            ),
            rule(
                    "content",
                    "Feedback content must contain at least 3 characters",
                    data -> hasText(data.content()) && data.content().length() < MIN_CONTENT_LENGTH
            ),
            rule(
                    "content",
                    "Feedback content must not exceed 2000 characters",
                    data -> hasText(data.content()) && data.content().length() > MAX_CONTENT_LENGTH
            ),
            rule(
                    "source",
                    "Feedback source is required",
                    data -> data.source() == null
            ),
            rule(
                    "rating",
                    "Feedback rating must be between 1 and 5",
                    data -> data.rating() != null
                            && (data.rating() < MIN_RATING || data.rating() > MAX_RATING)
            ),
            rule(
                    "createdAt",
                    "Feedback creation date is required",
                    data -> data.createdAt() == null
            )
    );

    private FeedbackValidator() {
    }

    public static void validate(
            UUID id,
            String title,
            String content,
            FeedbackSource source,
            Integer rating,
            LocalDateTime createdAt
    ) {
        var data = new FeedbackValidationData(
                id,
                title,
                content,
                source,
                rating,
                createdAt
        );

        var violations = RULES.stream()
                .map(rule -> rule.validate(data))
                .flatMap(Optional::stream)
                .toList();

        if (!violations.isEmpty()) {
            throw new InvalidFeedbackException(violations);
        }
    }

    @Builder
    private record FeedbackValidationData(
            UUID id,
            String title,
            String content,
            FeedbackSource source,
            Integer rating,
            LocalDateTime createdAt
    ) {
    }

}
