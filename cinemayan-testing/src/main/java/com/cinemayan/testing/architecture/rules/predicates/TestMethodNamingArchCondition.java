package com.cinemayan.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

import java.util.regex.Pattern;

public class TestMethodNamingArchCondition extends ArchCondition<JavaMethod> {

    private static final Pattern PATTERN = Pattern.compile("^[^_]+_should[A-Z][^_]*_when[A-Z].*$");

    public TestMethodNamingArchCondition () {
        super(
            "Follow naming convention methodBeingTested_should{ExpectedBehavior}_when{Condition}");
    }

    @Override
    public void check (JavaMethod method, ConditionEvents events) {
        String methodName = method.getName();
        boolean matches = PATTERN.matcher(methodName)
            .matches();
        if (!matches) {
            String message =
                "Method '%s' in '%s' does not follow the naming convention".formatted(methodName,
                    method.getOwner()
                        .getFullName());
            events.add(SimpleConditionEvent.violated(method, message));
        }
    }
}
