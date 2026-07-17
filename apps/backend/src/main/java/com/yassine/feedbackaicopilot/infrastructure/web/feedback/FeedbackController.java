package com.yassine.feedbackaicopilot.infrastructure.web.feedback;

import com.yassine.feedbackaicopilot.application.feedback.CreateFeedbackUseCase;
import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.infrastructure.web.feedback.request.CreateFeedbackRequest;
import com.yassine.feedbackaicopilot.infrastructure.web.feedback.response.FeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final CreateFeedbackUseCase createFeedbackUseCase;

    @PostMapping
    @ResponseStatus(CREATED)
    public FeedbackResponse create(@RequestBody CreateFeedbackRequest request) {
        Feedback feedback = createFeedbackUseCase.create(
                request.content(),
                request.source(),
                request.rating()
        );

        return new FeedbackResponse(
                feedback.id(),
                feedback.content(),
                feedback.source(),
                feedback.createdAt()
        );
    }

}
