package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListFeedbacksUseCaseTest {

    @Mock
    private FeedbackRepositoryPort feedbackRepositoryPort;

    @InjectMocks
    private ListFeedbacksUseCase listFeedbacksUseCase;

    @Test
    @DisplayName("Should list all feedbacks")
    void shouldListAllFeedbacks() {
        var feedbacks = List.of(
                new Feedback(
                        UUID.randomUUID(),
                        "Navigation is clear",
                        "The application is simple to use.",
                        FeedbackSource.APP,
                        5,
                        LocalDateTime.now()
                ),
                new Feedback(
                        UUID.randomUUID(),
                        "Support was helpful",
                        "The support team answered quickly.",
                        FeedbackSource.SUPPORT,
                        4,
                        LocalDateTime.now()
                )
        );

        when(feedbackRepositoryPort.findAll()).thenReturn(feedbacks);

        var result = listFeedbacksUseCase.list();

        assertThat(result).isEqualTo(feedbacks);
        verify(feedbackRepositoryPort).findAll();
    }
}
