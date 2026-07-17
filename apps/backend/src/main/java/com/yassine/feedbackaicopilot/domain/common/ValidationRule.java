package com.yassine.feedbackaicopilot.domain.common;

import java.util.Optional;
import java.util.function.Predicate;

@FunctionalInterface
public interface ValidationRule<T> {
    Optional<Violation> validate(T target);

    static <T> ValidationRule<T> rule(
            String field,
            String message,
            Predicate<T> invalidWhen
    ) {
        return target -> invalidWhen.test(target)
                ? Optional.of(new Violation(field, message))
                : Optional.empty();
    }
}
