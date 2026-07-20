package com.yassine.feedbackaicopilot.infrastructure.web.feedback.response;

import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeedbackResponse(
        UUID id,
        String title,
        String content,
        FeedbackSource source,
        Integer rating,
        LocalDateTime createdAt
) {
}
