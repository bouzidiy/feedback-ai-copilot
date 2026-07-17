package com.yassine.feedbackaicopilot.infrastructure.persistence.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryAdapter implements FeedbackRepositoryPort {

    private final FeedbackJpaRepository feedbackJpaRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public Feedback save(Feedback feedback) {
        var entity = feedbackMapper.toEntity(feedback);
        var savedEntity = feedbackJpaRepository.save(entity);
        return feedbackMapper.toDomain(savedEntity);
    }
}
