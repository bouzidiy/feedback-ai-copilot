package com.yassine.feedbackaicopilot.domain.feedback.port;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;

public interface FeedbackRepositoryPort {
    Feedback save(Feedback feedback);
}
