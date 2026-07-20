package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListFeedbacksUseCase {

    private final FeedbackRepositoryPort feedbackRepositoryPort;

    public List<Feedback> list() {
        return feedbackRepositoryPort.findAll();
    }
}
