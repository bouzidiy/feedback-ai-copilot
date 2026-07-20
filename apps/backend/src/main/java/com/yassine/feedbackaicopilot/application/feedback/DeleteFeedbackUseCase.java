package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.exception.FeedbackNotFoundException;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteFeedbackUseCase {

    private final FeedbackRepositoryPort feedbackRepositoryPort;

    public void delete(UUID id) {
        feedbackRepositoryPort.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id));

        feedbackRepositoryPort.deleteById(id);
    }
}
