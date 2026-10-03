package com.maya.cbs.testing.architecture.rules.predicates;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

public class RequestBodyArchCondition extends ArchCondition<JavaMethod> {

    public RequestBodyArchCondition () {
        super("");
    }

    @Override
    public void check (JavaMethod method, ConditionEvents events) {
        var requestBodyParameter = Arrays.stream(method.reflect()
                .getParameters())
            .filter(parameter -> parameter.isAnnotationPresent(RequestBody.class))
            .findFirst()
            .orElse(null);

        if (requestBodyParameter != null &&
            !requestBodyParameter.isAnnotationPresent(Valid.class)) {
            String message = ("Method %s uses a @RequestBody parameter but is missing the @Valid " +
                "annotation").formatted(method.getFullName());
            events.add(SimpleConditionEvent.violated(method, message));
        }
    }
}
