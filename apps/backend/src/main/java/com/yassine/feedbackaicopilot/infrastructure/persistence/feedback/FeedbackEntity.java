package com.yassine.feedbackaicopilot.infrastructure.persistence.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackSource source;

    private Integer rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
