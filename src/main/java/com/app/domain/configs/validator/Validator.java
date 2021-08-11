package com.app.domain.configs.validator;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Validator interface
 * @param <T>
 */
public interface Validator<T> {

    Map<String, String> validate(T t);

    static <T> void validate(Validator<T> validator, T t) {
        var errors = validator.validate(t);

        if (!errors.isEmpty()) {
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(joining(", "));
            // TODO
        }
    }
}