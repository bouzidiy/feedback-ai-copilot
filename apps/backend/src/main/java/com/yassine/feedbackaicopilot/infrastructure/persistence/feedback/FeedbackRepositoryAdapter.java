package com.yassine.feedbackaicopilot.infrastructure.persistence.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Feedback> findById(UUID id) {
        return feedbackJpaRepository.findById(id)
                .map(feedbackMapper::toDomain);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackJpaRepository.findAll()
                .stream()
                .map(feedbackMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        feedbackJpaRepository.deleteById(id);
    }
}
