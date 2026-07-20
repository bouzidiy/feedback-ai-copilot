package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.exception.FeedbackNotFoundException;
import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetFeedbackUseCase {

    private final FeedbackRepositoryPort feedbackRepositoryPort;

    public Feedback get(UUID id) {
        return feedbackRepositoryPort.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id));
    }
}
