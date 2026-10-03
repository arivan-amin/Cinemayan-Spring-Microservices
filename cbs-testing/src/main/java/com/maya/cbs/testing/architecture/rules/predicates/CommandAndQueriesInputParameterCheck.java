package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

import static com.maya.cbs.testing.architecture.rules.CleanArchitectureRules.COMMANDS_AND_QUERIES_METHOD_NAME;
import static com.maya.cbs.testing.architecture.rules.CleanArchitectureRules.COMMAND_QUERY_PARAMETER_NAME_SUFFIX;

public class CommandAndQueriesInputParameterCheck extends ArchCondition<JavaClass> {

    public CommandAndQueriesInputParameterCheck () {
        super("");
    }

    @Override
    public void check (JavaClass javaClass, ConditionEvents events) {
        javaClass.getMethods()
            .stream()
            .filter(method -> COMMANDS_AND_QUERIES_METHOD_NAME.equals(method.getName()))
            .filter(method -> !method.getParameters()
                .isEmpty())
            .filter(method -> isParameterNameInvalid(getParameterName(method)))
            .map(method -> createViolationEvent(javaClass, method))
            .forEach(events::add);
    }

    private boolean isParameterNameInvalid (String parameterName) {
        return !parameterName.endsWith(COMMAND_QUERY_PARAMETER_NAME_SUFFIX);
    }

    private String getParameterName (JavaMethod method) {
        return method.getParameters()
            .stream()
            .findFirst()
            .map(param -> param.getType()
                .getName())
            .orElse("");
    }

    private ConditionEvent createViolationEvent (JavaClass javaClass, JavaMethod method) {
        return SimpleConditionEvent.violated(javaClass,
            "Method %s parameter must end with *Input".formatted(method.getFullName()));
    }
}
