package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.exception.FeedbackNotFoundException;
import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateFeedbackUseCase {

    private final FeedbackRepositoryPort feedbackRepositoryPort;

    public Feedback update(UUID id, String title, String content, FeedbackSource source, Integer rating) {
        var existingFeedback = feedbackRepositoryPort.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id));

        var updatedFeedback = existingFeedback.update(title, content, source, rating);
        return feedbackRepositoryPort.save(updatedFeedback);
    }
}
