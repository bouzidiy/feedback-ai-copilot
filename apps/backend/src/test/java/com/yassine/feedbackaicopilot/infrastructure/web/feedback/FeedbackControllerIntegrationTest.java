package com.yassine.feedbackaicopilot.infrastructure.web.feedback;

import com.yassine.feedbackaicopilot.TestcontainersConfiguration;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(statements = "DELETE FROM feedbacks")
class FeedbackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should list all created feedbacks")
    void shouldListAllCreatedFeedbacks() throws Exception {
        createFeedback("""
                {
                  "title": "Navigation is clear",
                  "content": "The application is simple to use and the main workflow is easy to follow.",
                  "source": "APP",
                  "rating": 5
                }
                """);

        createFeedback("""
                {
                  "title": "Support was helpful",
                  "content": "The support team answered quickly and clearly.",
                  "source": "SUPPORT",
                  "rating": 4
                }
                """);

        mockMvc.perform(get("/api/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder(
                        "Navigation is clear",
                        "Support was helpful"
                )))
                .andExpect(jsonPath("$[*].source", containsInAnyOrder("APP", "SUPPORT")))
                .andExpect(jsonPath("$[*].rating", containsInAnyOrder(5, 4)));
    }

    @Test
    @DisplayName("Should get feedback by id")
    void shouldGetFeedbackById() throws Exception {
        var id = createFeedback("""
                {
                  "title": "Navigation is clear",
                  "content": "The application is simple to use and the main workflow is easy to follow.",
                  "source": "APP",
                  "rating": 5
                }
                """);

        mockMvc.perform(get("/api/feedbacks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("Navigation is clear"))
                .andExpect(jsonPath("$.content").value("The application is simple to use and the main workflow is easy to follow."))
                .andExpect(jsonPath("$.source").value("APP"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    @DisplayName("Should return validation error when title is missing")
    void shouldReturnValidationErrorWhenTitleIsMissing() throws Exception {
        mockMvc.perform(post("/api/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "content": "The application is simple to use.",
                                  "source": "APP",
                                  "rating": 5
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_FEEDBACK"))
                .andExpect(jsonPath("$.message").value("Feedback title is required"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should return validation error when content is missing")
    void shouldReturnValidationErrorWhenContentIsMissing() throws Exception {
        mockMvc.perform(post("/api/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Navigation is clear",
                                  "source": "APP",
                                  "rating": 5
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_FEEDBACK"))
                .andExpect(jsonPath("$.message").value("Feedback content is required"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should update feedback")
    void shouldUpdateFeedback() throws Exception {
        var id = createFeedback("""
                {
                  "title": "Navigation is clear",
                  "content": "The application is simple to use.",
                  "source": "APP",
                  "rating": 4
                }
                """);

        mockMvc.perform(put("/api/feedbacks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Navigation is excellent",
                                  "content": "The application is simple to use and the updated workflow is clear.",
                                  "source": "SURVEY",
                                  "rating": 5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("Navigation is excellent"))
                .andExpect(jsonPath("$.content").value("The application is simple to use and the updated workflow is clear."))
                .andExpect(jsonPath("$.source").value("SURVEY"))
                .andExpect(jsonPath("$.rating").value(5));

        mockMvc.perform(get("/api/feedbacks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Navigation is excellent"))
                .andExpect(jsonPath("$.source").value("SURVEY"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    @DisplayName("Should delete feedback")
    void shouldDeleteFeedback() throws Exception {
        var id = createFeedback("""
                {
                  "title": "Navigation is clear",
                  "content": "The application is simple to use.",
                  "source": "APP",
                  "rating": 4
                }
                """);

        mockMvc.perform(delete("/api/feedbacks/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/feedbacks/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("FEEDBACK_NOT_FOUND"));
    }

    @Test
    @DisplayName("Should return not found when feedback does not exist")
    void shouldReturnNotFoundWhenFeedbackDoesNotExist() throws Exception {
        var id = UUID.randomUUID();

        mockMvc.perform(get("/api/feedbacks/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("FEEDBACK_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Feedback not found: " + id));
    }

    private UUID createFeedback(String requestBody) throws Exception {
        var result = mockMvc.perform(post("/api/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        return UUID.fromString(id);
    }
}
