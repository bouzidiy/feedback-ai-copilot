package com.yassine.feedbackaicopilot.infrastructure.web.feedback.request;

import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;

public record UpdateFeedbackRequest(
        String title,
        String content,
        FeedbackSource source,
        Integer rating
) {
}
