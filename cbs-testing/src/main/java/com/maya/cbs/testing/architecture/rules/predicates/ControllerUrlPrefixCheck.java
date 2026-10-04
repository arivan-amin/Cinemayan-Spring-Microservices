package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

import java.util.*;

public class ControllerUrlPrefixCheck extends ArchCondition<JavaMethod> {

    private final Collection<String> requiredApiPrefixes;

    public ControllerUrlPrefixCheck (Collection<String> requiredApiPrefixes) {
        super("");
        this.requiredApiPrefixes = new ArrayList<>(requiredApiPrefixes);
    }

    @Override
    public void check (JavaMethod method, ConditionEvents events) {
        method.getAnnotations()
            .stream()
            .filter(annotation -> annotation.getRawType()
                .getName()
                .endsWith("Mapping"))
            .forEach(annotation -> validateMappingAnnotation(annotation, method, events));
    }

    private void validateMappingAnnotation (JavaAnnotation<JavaMethod> annotation,
                                            JavaMethod method, ConditionEvents events) {
        String[] mappings = (String[]) annotation.get("value")
            .orElse(null);
        if (mappings == null || mappings.length == 0) {
            return;
        }

        long validMappingsCount = Arrays.stream(mappings)
            .filter(mapping -> requiredApiPrefixes.stream()
                .anyMatch(mapping::contains))
            .count();

        if (validMappingsCount == 0) {
            events.add(SimpleConditionEvent.violated(method,
                String.format("Method %s does not use any of the required prefixes: %s",
                    method.getFullName(), requiredApiPrefixes)));
        }
        else if (validMappingsCount != mappings.length) {
            events.add(SimpleConditionEvent.violated(method,
                String.format("Method %s contains mappings with prefixes not recognized: %s",
                    method.getFullName(), requiredApiPrefixes)));
        }
    }
}
