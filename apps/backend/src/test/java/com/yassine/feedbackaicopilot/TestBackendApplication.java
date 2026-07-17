package com.yassine.feedbackaicopilot;

import org.springframework.boot.SpringApplication;

public class TestBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(FeedbackAiCopilotApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
