package com.cinemayan.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.*;

public class StaticMethodArchCondition extends ArchCondition<JavaClass> {

    private final String apiResponseMethodName;

    public StaticMethodArchCondition (String methodName) {
        super("have a static method named 'of'");
        apiResponseMethodName = methodName;
    }

    @Override
    public void check (JavaClass javaClass, ConditionEvents events) {
        boolean hasStaticMethodNamedOf = javaClass.getMethods()
            .stream()
            .anyMatch(method -> method.getName()
                .equals(apiResponseMethodName) && method.getModifiers()
                .contains(JavaModifier.STATIC));

        String message = "Class %s %s a static method named 'of'".formatted(javaClass.getName(),
            hasStaticMethodNamedOf ? "contains" : "does not contain");

        events.add(new SimpleConditionEvent(javaClass, hasStaticMethodNamedOf, message));
    }
}
