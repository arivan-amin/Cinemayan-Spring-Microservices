package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

import java.util.HashSet;
import java.util.Set;

public class JpaProhibitedMethodsCheck extends ArchCondition<JavaClass> {

    private final Set<String> prohibitedJpaMethods;

    public JpaProhibitedMethodsCheck (Set<String> prohibitedJpaMethods) {
        super("");
        this.prohibitedJpaMethods = new HashSet<>(prohibitedJpaMethods);
    }

    @Override
    public void check (JavaClass javaClass, ConditionEvents conditionEvents) {
        for (JavaMethod method : javaClass.getMethods()) {
            if (isProhibitedMethodImplemented(javaClass, method)) {
                String message = "Class %s implements %s method".formatted(javaClass.getName(),
                    method.getName());
                conditionEvents.add(SimpleConditionEvent.violated(javaClass, message));
            }
        }
    }

    private boolean isProhibitedMethodImplemented (JavaClass javaClass, JavaMethod method) {
        return prohibitedJpaMethods.contains(method.getName()) && method.getOwner()
            .equals(javaClass);
    }
}
