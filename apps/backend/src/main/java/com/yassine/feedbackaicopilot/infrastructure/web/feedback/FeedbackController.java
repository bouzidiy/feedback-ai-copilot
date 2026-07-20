package com.yassine.feedbackaicopilot.infrastructure.web.feedback;

import com.yassine.feedbackaicopilot.application.feedback.CreateFeedbackUseCase;
import com.yassine.feedbackaicopilot.application.feedback.DeleteFeedbackUseCase;
import com.yassine.feedbackaicopilot.application.feedback.GetFeedbackUseCase;
import com.yassine.feedbackaicopilot.application.feedback.ListFeedbacksUseCase;
import com.yassine.feedbackaicopilot.application.feedback.UpdateFeedbackUseCase;
import com.yassine.feedbackaicopilot.domain.feedback.model.Feedback;
import com.yassine.feedbackaicopilot.infrastructure.web.feedback.request.CreateFeedbackRequest;
import com.yassine.feedbackaicopilot.infrastructure.web.feedback.request.UpdateFeedbackRequest;
import com.yassine.feedbackaicopilot.infrastructure.web.feedback.response.FeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final CreateFeedbackUseCase createFeedbackUseCase;
    private final ListFeedbacksUseCase listFeedbacksUseCase;
    private final GetFeedbackUseCase getFeedbackUseCase;
    private final UpdateFeedbackUseCase updateFeedbackUseCase;
    private final DeleteFeedbackUseCase deleteFeedbackUseCase;

    @GetMapping
    public List<FeedbackResponse> list() {
        return listFeedbacksUseCase.list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public FeedbackResponse get(@PathVariable UUID id) {
        return toResponse(getFeedbackUseCase.get(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public FeedbackResponse create(@RequestBody CreateFeedbackRequest request) {
        Feedback feedback = createFeedbackUseCase.create(
                request.title(),
                request.content(),
                request.source(),
                request.rating()
        );

        return toResponse(feedback);
    }

    @PutMapping("/{id}")
    public FeedbackResponse update(
            @PathVariable UUID id,
            @RequestBody UpdateFeedbackRequest request
    ) {
        Feedback feedback = updateFeedbackUseCase.update(
                id,
                request.title(),
                request.content(),
                request.source(),
                request.rating()
        );

        return toResponse(feedback);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        deleteFeedbackUseCase.delete(id);
    }

    private FeedbackResponse toResponse(Feedback feedback) {
        return new FeedbackResponse(
                feedback.id(),
                feedback.title(),
                feedback.content(),
                feedback.source(),
                feedback.rating(),
                feedback.createdAt()
        );
    }

}
