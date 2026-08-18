package com.yassine.feedbackaicopilot.infrastructure.web.feedback.request;

import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import jakarta.validation.constraints.NotBlank;

public record CreateFeedbackRequest(
        @NotBlank(message = "Feedback title is required")
        String title,
        @NotBlank(message = "Feedback content is required")
        String content,
        FeedbackSource source,
        Integer rating
) {
}
