package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;

public class ResponseWrapperArchCondition extends ArchCondition<JavaMethod> {

    private final String apiResponseSuffix;

    public ResponseWrapperArchCondition (String apiResponseSuffix) {
        super("");
        this.apiResponseSuffix = apiResponseSuffix;
    }

    @Override
    public void check (JavaMethod method, ConditionEvents events) {
        String returnType = method.getRawReturnType()
            .getName();
        boolean matches = "void".equals(returnType) || returnType.endsWith(apiResponseSuffix);
        events.add(new SimpleConditionEvent(method, matches,
            "Method %s in %s does not return a type ending with '%s'".formatted(method.getName(),
                method.getOwner()
                    .getName(), apiResponseSuffix)));
    }
}
