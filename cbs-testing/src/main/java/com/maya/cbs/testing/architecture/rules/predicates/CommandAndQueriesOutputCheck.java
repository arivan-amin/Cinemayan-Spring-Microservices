package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

import static com.maya.cbs.testing.architecture.rules.CleanArchitectureRules.COMMAND_QUERY_RETURN_TYPE_NAME_SUFFIX;
import static com.maya.cbs.testing.architecture.rules.CleanArchitectureRules.CQRS_EXECUTE_METHOD;

public class CommandAndQueriesOutputCheck extends ArchCondition<JavaClass> {

    public CommandAndQueriesOutputCheck () {
        super("");
    }

    @Override
    public void check (JavaClass javaClass, ConditionEvents events) {

        javaClass.getMethods()
            .stream()
            .filter(CommandAndQueriesOutputCheck::isNotVoidMethod)
            .filter(method -> CQRS_EXECUTE_METHOD.equals(method.getName()))
            .filter(this::isReturnTypeInvalid)
            .map(method -> createViolationEvent(javaClass, method))
            .forEach(events::add);
    }

    private static boolean isNotVoidMethod (JavaMethod method) {
        return !method.getRawReturnType()
            .isEquivalentTo(void.class);
    }

    private boolean isReturnTypeInvalid (JavaMethod method) {
        return !getReturnTypeName(method).endsWith(COMMAND_QUERY_RETURN_TYPE_NAME_SUFFIX);
    }

    private ConditionEvent createViolationEvent (JavaClass javaClass, JavaMethod method) {
        return SimpleConditionEvent.violated(javaClass,
            "Method %s in %s must return a type ending with 'Output'".formatted(
                method.getFullName(), javaClass.getName()));
    }

    private String getReturnTypeName (JavaMethod method) {
        return method.getReturnType()
            .getName();
    }
}
