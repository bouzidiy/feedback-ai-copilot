package com.yassine.feedbackaicopilot.application.feedback;

import com.yassine.feedbackaicopilot.domain.feedback.exception.FeedbackNotFoundException;
import com.yassine.feedbackaicopilot.domain.feedback.exception.InvalidFeedbackException;
import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.domain.feedback.model.FeedbackSource;
import com.yassine.feedbackaicopilot.domain.feedback.port.FeedbackRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateFeedbackUseCaseTest {

    @Mock
    private FeedbackRepositoryPort feedbackRepositoryPort;

    @InjectMocks
    private UpdateFeedbackUseCase updateFeedbackUseCase;

    @Test
    @DisplayName("Should update feedback")
    void shouldUpdateFeedback() {
        var id = UUID.randomUUID();
        var createdAt = LocalDateTime.now().minusDays(1);
        var existingFeedback = new Feedback(
                id,
                "Old title",
                "Old content",
                FeedbackSource.APP,
                3,
                createdAt
        );

        when(feedbackRepositoryPort.findById(id)).thenReturn(Optional.of(existingFeedback));
        when(feedbackRepositoryPort.save(any(Feedback.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateFeedbackUseCase.update(
                id,
                "Updated title",
                "Updated content",
                FeedbackSource.SUPPORT,
                5
        );

        var feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepositoryPort).save(feedbackCaptor.capture());

        var savedFeedback = feedbackCaptor.getValue();
        assertThat(savedFeedback.id()).isEqualTo(id);
        assertThat(savedFeedback.title()).isEqualTo("Updated title");
        assertThat(savedFeedback.content()).isEqualTo("Updated content");
        assertThat(savedFeedback.source()).isEqualTo(FeedbackSource.SUPPORT);
        assertThat(savedFeedback.rating()).isEqualTo(5);
        assertThat(savedFeedback.createdAt()).isEqualTo(createdAt);
        assertThat(result).isSameAs(savedFeedback);
    }

    @Test
    @DisplayName("Should reject update when feedback does not exist")
    void shouldRejectUpdateWhenFeedbackDoesNotExist() {
        var id = UUID.randomUUID();

        when(feedbackRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateFeedbackUseCase.update(
                id,
                "Updated title",
                "Updated content",
                FeedbackSource.SUPPORT,
                5
        ))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage("Feedback not found: " + id);

        verify(feedbackRepositoryPort).findById(id);
        verifyNoMoreInteractions(feedbackRepositoryPort);
    }

    @Test
    @DisplayName("Should reject update when feedback is invalid")
    void shouldRejectUpdateWhenFeedbackIsInvalid() {
        var id = UUID.randomUUID();
        var existingFeedback = new Feedback(
                id,
                "Old title",
                "Old content",
                FeedbackSource.APP,
                3,
                LocalDateTime.now()
        );

        when(feedbackRepositoryPort.findById(id)).thenReturn(Optional.of(existingFeedback));

        assertThatThrownBy(() -> updateFeedbackUseCase.update(
                id,
                "",
                "Updated content",
                FeedbackSource.SUPPORT,
                5
        ))
                .isInstanceOf(InvalidFeedbackException.class)
                .hasMessage("Feedback title is required");

        verify(feedbackRepositoryPort).findById(id);
        verifyNoMoreInteractions(feedbackRepositoryPort);
    }
}
