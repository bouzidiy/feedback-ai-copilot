package com.yassine.feedbackaicopilot.domain.feedback.exception;

import java.util.UUID;

public class FeedbackNotFoundException extends RuntimeException {

    public FeedbackNotFoundException(UUID id) {
        super("Feedback not found: " + id);
    }
}
