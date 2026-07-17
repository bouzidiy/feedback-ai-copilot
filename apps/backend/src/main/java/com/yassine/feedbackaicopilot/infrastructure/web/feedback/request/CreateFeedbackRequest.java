package com.yassine.feedbackaicopilot.infrastructure.web.feedback.request;

import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;

public record CreateFeedbackRequest(
        String content,
        FeedbackSource source,
        Integer rating
) {
}
