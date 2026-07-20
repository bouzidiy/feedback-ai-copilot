package com.yassine.feedbackaicopilot.domain.feedback.port;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepositoryPort {
    Feedback save(Feedback feedback);

    Optional<Feedback> findById(UUID id);

    List<Feedback> findAll();

    void deleteById(UUID id);
}
