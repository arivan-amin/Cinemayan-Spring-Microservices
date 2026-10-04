package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

import java.util.*;
import java.util.stream.Stream;

public class ApiVersioningCheck extends ArchCondition<JavaMethod> {

    private final Collection<String> apiVersioningPatterns;

    public ApiVersioningCheck (Collection<String> apiVersioningPatterns) {
        super("");
        this.apiVersioningPatterns = new ArrayList<>(apiVersioningPatterns);
    }

    @Override
    public void check (JavaMethod method, ConditionEvents events) {
        method.getAnnotations()
            .stream()
            .filter(annotation -> annotation.getRawType()
                .getName()
                .endsWith("Mapping"))
            .forEach(annotation -> validateVersioning(annotation, method, events));
    }

    private void validateVersioning (JavaAnnotation<JavaMethod> annotation, JavaMethod method,
                                     ConditionEvents events) {
        String[] urlPatterns = Stream.of("value", "path")
            .map(annotation::get)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(value -> (String[]) value)
            .flatMap(Arrays::stream)
            .toArray(String[]::new);

        boolean isNotVersioned = Arrays.stream(urlPatterns)
            .noneMatch(url -> apiVersioningPatterns.stream()
                .anyMatch(url::matches));
        if (isNotVersioned) {
            String message = "Method %s should be versioned".formatted(method.getFullName());
            events.add(SimpleConditionEvent.violated(method, message));
        }
    }
}
