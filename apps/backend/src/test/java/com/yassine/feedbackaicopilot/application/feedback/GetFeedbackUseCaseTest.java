package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.exception.FeedbackNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFeedbackUseCaseTest {

    @Mock
    private FeedbackRepositoryPort feedbackRepositoryPort;

    @InjectMocks
    private GetFeedbackUseCase getFeedbackUseCase;

    @Test
    @DisplayName("Should get feedback by id")
    void shouldGetFeedbackById() {
        var id = UUID.randomUUID();
        var feedback = new Feedback(
                id,
                "Navigation is clear",
                "The application is simple to use.",
                FeedbackSource.APP,
                5,
                LocalDateTime.now()
        );

        when(feedbackRepositoryPort.findById(id)).thenReturn(Optional.of(feedback));

        var result = getFeedbackUseCase.get(id);

        assertThat(result).isSameAs(feedback);
        verify(feedbackRepositoryPort).findById(id);
    }

    @Test
    @DisplayName("Should reject unknown feedback id")
    void shouldRejectUnknownFeedbackId() {
        var id = UUID.randomUUID();

        when(feedbackRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getFeedbackUseCase.get(id))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage("Feedback not found: " + id);

        verify(feedbackRepositoryPort).findById(id);
    }
}
