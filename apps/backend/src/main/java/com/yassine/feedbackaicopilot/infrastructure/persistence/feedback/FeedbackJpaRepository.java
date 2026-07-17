package com.yassine.feedbackaicopilot.infrastructure.persistence.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackJpaRepository extends JpaRepository<FeedbackEntity, UUID> {
}
