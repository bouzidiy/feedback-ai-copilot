package com.yassine.feedbackaicopilot.infrastructure.persistence.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackEntity toEntity(Feedback feedback) {
        return FeedbackEntity.builder()
                .id(feedback.id())
                .content(feedback.content())
                .source(feedback.source())
                .createdAt(feedback.createdAt())
                .build();
    }

    public Feedback toDomain(FeedbackEntity entity) {
        return new Feedback(
                entity.getId(),
                entity.getContent(),
                entity.getSource(),
                entity.getRating(),
                entity.getCreatedAt()
        );
    }
}
