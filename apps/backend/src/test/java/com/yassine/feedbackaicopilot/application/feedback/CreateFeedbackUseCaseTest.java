package com.yassine.feedbackaicopilot.application.feedback;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFeedbackUseCaseTest {

    @Mock
    private FeedbackRepositoryPort feedbackRepositoryPort;

    @InjectMocks
    private CreateFeedbackUseCase createFeedbackUseCase;

    @Test
    @DisplayName("Should reject feedback when content is null")
    void shouldRejectFeedbackWhenContentIsNull() {
        FeedbackSource source = FeedbackSource.values()[0];

        assertThatThrownBy(() -> createFeedbackUseCase.create(null, source, 4))
                .isInstanceOf(InvalidFeedbackException.class)
                .hasMessage("Feedback content is required");

        verifyNoInteractions(feedbackRepositoryPort);
    }

    @Test
    @DisplayName("Should reject feedback when rating is lower than one")
    void shouldRejectFeedbackWhenRatingIsLowerThanOne() {
        FeedbackSource source = FeedbackSource.values()[0];

        assertThatThrownBy(() ->
                createFeedbackUseCase.create(
                        "Application simple à utiliser",
                        source,
                        0
                )
        )
                .isInstanceOf(InvalidFeedbackException.class)
                .hasMessage("Feedback rating must be between 1 and 5");

        verifyNoInteractions(feedbackRepositoryPort);
    }

    @Test
    @DisplayName("Should reject feedback when rating is greater than five")
    void shouldRejectFeedbackWhenRatingIsGreaterThanFive() {
        FeedbackSource source = FeedbackSource.values()[0];

        assertThatThrownBy(() ->
                createFeedbackUseCase.create(
                        "Application simple à utiliser",
                        source,
                        6
                )
        )
                .isInstanceOf(InvalidFeedbackException.class)
                .hasMessage("Feedback rating must be between 1 and 5");

        verifyNoInteractions(feedbackRepositoryPort);
    }

    @Test
    @DisplayName("Should create and save a feedback")
    void shouldCreateAndSaveFeedback() {
        // Given
        var content = "L'application est simple à utiliser";
        var source = FeedbackSource.values()[0];
        var rating = 5;

        when(feedbackRepositoryPort.save(any(Feedback.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        var result = createFeedbackUseCase.create(content, source, rating);

        // Then
        var feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);

        verify(feedbackRepositoryPort).save(feedbackCaptor.capture());

        var feedbackSentToRepository = feedbackCaptor.getValue();

        assertThat(feedbackSentToRepository.content()).isEqualTo(content);

        assertThat(feedbackSentToRepository.source()).isEqualTo(source);

        assertThat(feedbackSentToRepository.rating()).isEqualTo(rating);

        assertThat(feedbackSentToRepository.createdAt()).isNotNull();

        assertThat(result).isSameAs(feedbackSentToRepository);

        verifyNoMoreInteractions(feedbackRepositoryPort);
    }
}