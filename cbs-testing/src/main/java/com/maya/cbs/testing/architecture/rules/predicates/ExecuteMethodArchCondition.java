package com.maya.cbs.testing.architecture.rules.predicates;

import com.maya.cbs.testing.architecture.rules.CleanArchitectureRules;
import com.tngtech.archunit.core.domain.*;
import com.tngtech.archunit.lang.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.maya.cbs.testing.architecture.rules.CleanArchitectureRules.CQRS_EXECUTE_METHOD;

public class ExecuteMethodArchCondition extends ArchCondition<JavaClass> {

    public ExecuteMethodArchCondition () {
        super("must have exactly one public method named 'execute', annotated with @Transactional");
    }

    @Override
    public void check (JavaClass javaClass, ConditionEvents events) {
        List<JavaMethod> publicMethods = javaClass.getAllMethods()
            .stream()
            .filter(method -> !method.getOwner()
                .isEquivalentTo(Object.class))
            .filter(method -> method.getModifiers()
                .contains(JavaModifier.PUBLIC))
            .toList();

        List<JavaMethod> executeMethods = publicMethods.stream()
            .filter(method -> CQRS_EXECUTE_METHOD.equals(method.getName()))
            .toList();

        if (publicMethods.size() != 1 || executeMethods.size() != 1) {
            events.add(new SimpleConditionEvent(javaClass, false,
                javaClass + " should have exactly one public method, and it must be named '" +
                CQRS_EXECUTE_METHOD + "'."));
            return;
        }

        JavaMethod executeMethod = executeMethods.get(0);

        Optional<Transactional> transactional =
            executeMethod.tryGetAnnotationOfType(Transactional.class);

        if (transactional.isEmpty()) {
            events.add(new SimpleConditionEvent(javaClass, false,
                javaClass + "." + CQRS_EXECUTE_METHOD + "() must be annotated with " +
                "@org.springframework.transaction.annotation.Transactional."));
            return;
        }

        boolean readOnly = transactional.get()
            .readOnly();

        boolean isQuery = javaClass.getSimpleName()
            .endsWith(CleanArchitectureRules.QUERY_SUFFIX);
        boolean isCommand = javaClass.getSimpleName()
            .endsWith(CleanArchitectureRules.COMMAND_SUFFIX);

        if (isQuery && readOnly) {
            events.add(new SimpleConditionEvent(javaClass, false,
                javaClass + "." + CQRS_EXECUTE_METHOD +
                "() is a Query and must NOT be annotated @Transactional(readOnly = true) to " +
                "preserve recorded audit trail"));
        }
        else if (isCommand && readOnly) {
            events.add(new SimpleConditionEvent(javaClass, false,
                javaClass + "." + CQRS_EXECUTE_METHOD + "() is a Command and must " +
                "NOT be annotated @Transactional(readOnly = true) — commands mutate state."));
        }
    }
}
