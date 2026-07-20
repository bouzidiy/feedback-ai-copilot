package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFeedbackUseCase {

    private final FeedbackRepositoryPort feedBackRepositoryPort;

    public Feedback create(String title, String content, FeedbackSource source, Integer rating) {
        var feedback = Feedback.create(title, content, source, rating);
        return feedBackRepositoryPort.save(feedback);
    }
}
