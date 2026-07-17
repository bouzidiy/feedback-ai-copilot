package com.yassine.feedbackaicopilot.domain.feedback.exception;

import com.yassine.feedbackaicopilot.domain.common.Violation;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidFeedbackException extends RuntimeException {

    private final List<Violation> violations;

    public InvalidFeedbackException(List<Violation> violations) {
        super(buildMessage(violations));
        this.violations = violations;
    }

    public List<Violation> violations() {
        return violations;
    }

    private static String buildMessage(List<Violation> violations) {
        return violations.stream()
                .map(Violation::message)
                .collect(Collectors.joining(", "));
    }

}
