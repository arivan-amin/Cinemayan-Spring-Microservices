package com.maya.cbs.testing.architecture.rules;

import com.maya.cbs.testing.architecture.rules.predicates.*;
import com.maya.cbs.testing.architecture.rules.predicates.*;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.*;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.maya.cbs.core.domain.config.CoreApplicationConfig.BASE_PACKAGE;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.ProxyRules.no_classes_should_directly_call_other_methods_declared_in_the_same_class_that_are_annotated_with;

public interface CleanArchitectureRules {

    String DOMAIN_PACKAGE = "..domain..";

    String DOMAIN_LAYER = "Domain";

    String INFRASTRUCTURE_PACKAGE = "..infrastructure..";

    String INFRASTRUCTURE_LAYER = "Infrastructure";

    String APPLICATION_PACKAGE = "..application..";

    String APPLICATION_LAYER = "Controllers";

    String COMMAND_PACKAGE = "..command..";

    String COMMAND_SUFFIX = "Command";

    String QUERY_PACKAGE = "..query..";

    String QUERY_SUFFIX = "Query";

    String COMMAND_QUERY_PARAMETER_NAME_SUFFIX = "Input";

    String COMMAND_QUERY_RETURN_TYPE_NAME_SUFFIX = "Output";

    String CONTROLLER_PACKAGE = "..endpoints..";

    String COMMANDS_AND_QUERIES_METHOD_NAME = "execute";

    String CONTROLLER_SUFFIX = "Controller";

    String SCHEDULER_SUFFIX = "Scheduler";

    String STORAGE_SUFFIX = "Storage";

    String REPOSITORY_SUFFIX = "Repository";

    String API_RESPONSE_SUFFIX = "Response";

    String API_RESPONSE_METHOD_NAME = "of";

    String SPRING_FRAMEWORK_PACKAGES = "org.springframework..";

    String[] PERSISTENCE_PACKAGES =
        { "..jakarta.persistence..", "..jakarta.validation..", "..jakarta.transaction..",
            "java.sql..", "..javax.persistence..", "..javax.validation..", "..javax.transaction..",
            "..org.hibernate..", "..org.springframework.data.." };

    Set<String> PROHIBITED_JPA_METHODS = Set.of("equals", "hashCode");

    Collection<String> REQUIRED_API_PREFIXES = List.of("/public/", "/protected/");

    Collection<String> API_VERSIONING_PATTERNS =
        List.of("^/[a-z]+/protected/v[1-9]/.*", "^/[a-z]+/public/v[1-9]/.*");

    @ArchTest
    ArchRule DOMAIN_SHOULD_NOT_DEPEND_ON_ANY_PERSISTENCE_MECHANISM = noClasses().that()
        .resideInAPackage(DOMAIN_PACKAGE)
        .should()
        .accessClassesThat()
        .resideInAnyPackage(PERSISTENCE_PACKAGES)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule DOMAIN_SHOULD_NOT_DEPEND_ON_INFRASTRUCTURE_LAYER_OR_APPLICATION_LAYER =
        noClasses().that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
            .accessClassesThat()
            .resideInAnyPackage(INFRASTRUCTURE_PACKAGE, APPLICATION_PACKAGE)
            .allowEmptyShould(true);

    @ArchTest
    ArchRule DOMAIN_SHOULD_NOT_ACCESS_APPLICATION_LAYER = noClasses().that()
        .resideInAPackage(DOMAIN_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(INFRASTRUCTURE_PACKAGE, APPLICATION_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule DOMAIN_SHOULD_NOT_DEPEND_ON_SPRING = noClasses().that()
        .resideInAPackage(DOMAIN_PACKAGE)
        .should()
        .accessClassesThat()
        .resideInAPackage(SPRING_FRAMEWORK_PACKAGES)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INFRASTRUCTURE_LAYER_SHOULD_NOT_ACCESS_APPLICATION_LAYER = noClasses().that()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .should()
        .accessClassesThat()
        .resideInAPackage(APPLICATION_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INFRASTRUCTURE_LAYER_SHOULD_NOT_DEPEND_ON_APPLICATION_LAYER = noClasses().that()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(APPLICATION_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INFRASTRUCTURE_LAYER_SHOULD_ONLY_BE_ACCESSED_BY_APPLICATION_LAYER = classes().that()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .should()
        .onlyBeAccessed()
        .byAnyPackage(APPLICATION_PACKAGE, INFRASTRUCTURE_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INTERFACES_MUST_NOT_BE_PLACED_IN_IMPLEMENTATION_PACKAGES = noClasses().that()
        .resideInAPackage(APPLICATION_PACKAGE)
        .should()
        .beInterfaces()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INFRASTRUCTURE_CLASSES_SHOULD_BE_IN_INFRASTRUCTURE_PACKAGE = classes().that()
        .haveSimpleNameContaining("Jpa")
        .and()
        .haveSimpleNameEndingWith(STORAGE_SUFFIX)
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .allowEmptyShould(true)
        .as("Classes that handle Persistence should only be in storage package");

    @ArchTest
    ArchRule JPA_ENTITIES_SHOULD_BE_IN_INFRASTRUCTURE_PACKAGE = classes().that()
        .areAnnotatedWith(Entity.class)
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .allowEmptyShould(true)
        .as("Entities should be in storage package");

    @ArchTest
    ArchRule JPA_REPOSITORY_SHOULD_BE_IN_INFRASTRUCTURE_PACKAGE = classes().that()
        .areAssignableTo(Repository.class)
        .should()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .andShould()
        .haveSimpleNameEndingWith(REPOSITORY_SUFFIX)
        .allowEmptyShould(true)
        .as("Entities should be in storage package");

    @ArchTest
    ArchRule LAYER_DEPENDENCIES_ARE_RESPECTED = layeredArchitecture().consideringAllDependencies()
        .layer(APPLICATION_LAYER)
        .definedBy(BASE_PACKAGE + APPLICATION_PACKAGE)
        .layer(DOMAIN_LAYER)
        .definedBy(BASE_PACKAGE + DOMAIN_PACKAGE)
        .layer(INFRASTRUCTURE_LAYER)
        .definedBy(BASE_PACKAGE + INFRASTRUCTURE_PACKAGE)
        .whereLayer(APPLICATION_LAYER)
        .mayNotBeAccessedByAnyLayer()
        .ignoreDependency(annotatedWith(SpringBootApplication.class),
            JavaClass.Predicates.resideInAPackage(APPLICATION_PACKAGE))
        .whereLayer(INFRASTRUCTURE_LAYER)
        .mayOnlyBeAccessedByLayers(APPLICATION_LAYER)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule ALL_PUBLIC_METHODS_IN_THE_CONTROLLER_LAYER_SHOULD_RETURN_RESPONSE_WRAPPERS =
        methods().that()
            .areDeclaredInClassesThat()
            .resideInAPackage(CONTROLLER_PACKAGE)
            .and()
            .arePublic()
            .should(new ResponseWrapperArchCondition(API_RESPONSE_SUFFIX))
            .allowEmptyShould(true)
            .because("we do not want to couple the api directly to the return types of the " +
                "domain module");

    @ArchTest
    ArchRule COMMAND_SHOULD_BE_SUFFIXED = classes().that()
        .resideInAPackage(COMMAND_PACKAGE)
        .and()
        .haveSimpleNameNotEndingWith(COMMAND_QUERY_PARAMETER_NAME_SUFFIX)
        .and()
        .haveSimpleNameNotEndingWith(COMMAND_QUERY_RETURN_TYPE_NAME_SUFFIX)
        .should()
        .haveSimpleNameEndingWith(COMMAND_SUFFIX)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule QUERY_SHOULD_BE_SUFFIXED = classes().that()
        .resideInAPackage(QUERY_PACKAGE)
        .and()
        .haveSimpleNameNotEndingWith(COMMAND_QUERY_PARAMETER_NAME_SUFFIX)
        .and()
        .haveSimpleNameNotEndingWith(COMMAND_QUERY_RETURN_TYPE_NAME_SUFFIX)
        .should()
        .haveSimpleNameEndingWith(QUERY_SUFFIX)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule COMMANDS_AND_QUERIES_SHOULD_BE_IN_DOMAIN_PACKAGE = classes().that()
        .haveSimpleNameEndingWith(COMMAND_SUFFIX)
        .or()
        .haveSimpleNameEndingWith(QUERY_SUFFIX)
        .should()
        .resideInAPackage(DOMAIN_PACKAGE)
        .allowEmptyShould(true)
        .because("Commands and queries contain business rules and should be in domain.");

    @ArchTest
    ArchRule COMMANDS_AND_QUERIES_SHOULD_BE_PUBLIC = classes().that()
        .resideInAPackage(DOMAIN_PACKAGE)
        .and()
        .haveSimpleNameEndingWith(COMMAND_SUFFIX)
        .or()
        .haveSimpleNameEndingWith(QUERY_SUFFIX)
        .and()
        .areNotNestedClasses()
        .should()
        .bePublic()
        .allowEmptyShould(true)
        .because("Commands and queries must be public to be used by other layers.");

    @ArchTest
    ArchRule COMMANDS_AND_QUERIES_SHOULD_HAVE_EXACTLY_ONE_PUBLIC_METHOD_NAMED_EXECUTE =
        classes().that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .and()
            .haveSimpleNameEndingWith(COMMAND_SUFFIX)
            .or()
            .haveSimpleNameEndingWith(QUERY_SUFFIX)
            .and()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .should(new ExecuteMethodArchCondition(COMMANDS_AND_QUERIES_METHOD_NAME))
            .allowEmptyShould(true)
            .because(
                "Commands and queries should adhere to the single responsibility principle and " +
                    "expose only the 'execute' method.");

    @ArchTest
    ArchRule COMMANDS_AND_QUERIES_SHOULD_HAVE_HAVE_SINGLE_PARAMETER_NAMED_INPUT = classes().that()
        .resideInAPackage(DOMAIN_PACKAGE)
        .and()
        .haveSimpleNameEndingWith(COMMAND_SUFFIX)
        .or()
        .haveSimpleNameEndingWith(QUERY_SUFFIX)
        .and()
        .doNotHaveModifier(JavaModifier.ABSTRACT)
        .should(new CommandAndQueriesInputParameterCheck())
        .allowEmptyShould(true)
        .because("Commands and Queries must have one parameter with a name that ends with Input");

    @ArchTest
    ArchRule COMMANDS_AND_QUERIES_RETURN_TYPE_NAME_MUST_END_WITH_OUTPUT = classes().that()
        .resideInAPackage(DOMAIN_PACKAGE)
        .and()
        .haveSimpleNameEndingWith(COMMAND_SUFFIX)
        .or()
        .haveSimpleNameEndingWith(QUERY_SUFFIX)
        .and()
        .doNotHaveModifier(JavaModifier.ABSTRACT)
        .should(new CommandAndQueriesOutputCheck())
        .allowEmptyShould(true)
        .because("Commands and Queries should return a type with a name that ends with Output");

    @ArchTest
    ArchRule CONTROLLERS_SHOULD_BE_SUFFIXED = classes().that()
        .resideInAPackage(APPLICATION_PACKAGE)
        .and()
        .areAnnotatedWith(RestController.class)
        .or()
        .areAssignableTo(Controller.class)
        .should()
        .haveSimpleNameEndingWith(CONTROLLER_SUFFIX)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule CONTROLLERS_SHOULD_BE_IN_ENDPOINTS_PACKAGE = classes().that()
        .areAnnotatedWith(RestController.class)
        .or()
        .areAssignableTo(Controller.class)
        .should()
        .resideInAPackage(CONTROLLER_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule CLASSES_NAMED_CONTROLLER_SHOULD_BE_IN_A_CONTROLLER_PACKAGE = classes().that()
        .haveSimpleNameEndingWith(CONTROLLER_SUFFIX)
        .should()
        .resideInAPackage(APPLICATION_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule DO_NOT_ANNOTATE_REST_CONTROLLER_WITH_REQUEST_MAPPING = noClasses().that()
        .areAnnotatedWith(RestController.class)
        .or()
        .areMetaAnnotatedWith(RestController.class)
        .should()
        .beAnnotatedWith(RequestMapping.class)
        .orShould()
        .beMetaAnnotatedWith(RequestMapping.class)
        .allowEmptyShould(true)
        .because("Controller method mapping is preferred to class mapping, use base path property");

    @ArchTest
    ArchRule VALIDATE_REST_CONTROLLER_METHODS_REQUEST_BODY_PARAMETER = methods().that()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .or()
        .areMetaAnnotatedWith(RestController.class)
        .should(new RequestBodyArchCondition())
        .because("Request body parameters must always be validated using @Valid annotation.")
        .allowEmptyShould(true);

    @ArchTest
    ArchRule VALIDATE_REST_CONTROLLER_METHODS_URL_PREFIXES = methods().that()
        .arePublic()
        .and()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .or()
        .areMetaAnnotatedWith(RestController.class)
        .should(new ControllerUrlPrefixCheck(REQUIRED_API_PREFIXES))
        .allowEmptyShould(true)
        .because(
            "URLs must be recognized based on their path, whether they are public or require " +
                "authentication");

    @ArchTest
    ArchRule SCHEDULER_CLASSES_SHOULD_BE_SUFFIXED_WITH_CORRECT_NAME = classes().that()
        .containAnyMethodsThat(new DescribedPredicate<>("are annotated with @Scheduled ") {
            @Override
            public boolean test (JavaMethod method) {
                return method.isAnnotatedWith(Scheduled.class);
            }
        })
        .should()
        .resideInAPackage(APPLICATION_PACKAGE)
        .andShould()
        .haveSimpleNameEndingWith(SCHEDULER_SUFFIX)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule STATIC_METHOD_NAMED_OF_IN_RESPONSE_CLASSES = classes().that()
        .haveSimpleNameEndingWith(API_RESPONSE_SUFFIX)
        .and()
        .resideInAPackage(APPLICATION_PACKAGE)
        .should(new StaticMethodArchCondition(API_RESPONSE_METHOD_NAME))
        .allowEmptyShould(true);

    @ArchTest
    ArchRule AVOID_BEAN_ANNOTATION_WITH_QUALIFIER = methods().that()
        .areAnnotatedWith(Bean.class)
        .should()
        .notBeAnnotatedWith(Qualifier.class)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule BEAN_ANNOTATION_SHOULD_BE_ON_PUBLIC_METHODS = methods().that()
        .areAnnotatedWith(Bean.class)
        .should()
        .bePublic()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule CONFIGURATION_AND_COMPONENT_CLASSES_SHOULD_NOT_BE_PUBLIC = classes().that()
        .areAnnotatedWith(Configuration.class)
        .or()
        .areAnnotatedWith(Component.class)
        .should()
        .notBePublic()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule ONLY_DECLARE_BEANS_IN_APPLICATION_CLASSES = classes().that()
        .resideOutsideOfPackages(APPLICATION_PACKAGE)
        .should()
        .notBeAnnotatedWith(Component.class)
        .andShould()
        .notBeAnnotatedWith(Configuration.class)
        .andShould()
        .notBeAnnotatedWith(Service.class);

    @ArchTest
    ArchRule REST_CONTROLLERS_SHOULD_NOT_BE_PUBLIC = classes().that()
        .areAnnotatedWith(RestController.class)
        .should()
        .notBePublic()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule REST_CONTROLLERS_METHODS_SHOULD_BE_PUBLIC = methods().that()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .and(annotatedWith(GetMapping.class).or(annotatedWith(PostMapping.class))
            .or(annotatedWith(DeleteMapping.class))
            .or(annotatedWith(PutMapping.class))
            .or(annotatedWith(PatchMapping.class)))
        .should()
        .bePublic()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule DOCUMENTED_REST_CONTROLLERS = classes().that()
        .areAnnotatedWith(RestController.class)
        .should()
        .beAnnotatedWith(Tag.class)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule DOCUMENT_REST_CONTROLLER_METHODS_WITH_OPERATION = methods().that()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .and(annotatedWith(GetMapping.class).or(annotatedWith(PostMapping.class))
            .or(annotatedWith(DeleteMapping.class))
            .or(annotatedWith(PutMapping.class))
            .or(annotatedWith(PatchMapping.class)))
        .should()
        .beAnnotatedWith(Operation.class)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule AVOID_REQUEST_MAPPING_IN_REST_CONTROLLER_METHODS = methods().that()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .should()
        .notBeAnnotatedWith(RequestMapping.class)
        .allowEmptyShould(true)
        .because("Use Get, Post, Delete or Put Mapping instead.");

    @ArchTest
    ArchRule NO_BYPASS_OF_PROXY_LOGIC =
        no_classes_should_directly_call_other_methods_declared_in_the_same_class_that_are_annotated_with(
            Async.class).allowEmptyShould(true);

    @ArchTest
    ArchRule ENTITY_CLASSES_SHOULD_NOT_IMPLEMENT_EQUALS_HASHCODE = classes().that()
        .areAnnotatedWith(Entity.class)
        .or()
        .areMetaAnnotatedWith(Entity.class)
        .and()
        .resideInAPackage(INFRASTRUCTURE_PACKAGE)
        .should(new JpaProhibitedMethodsCheck(PROHIBITED_JPA_METHODS))
        .allowEmptyShould(true);

    @ArchTest
    ArchRule CONTROLLER_ENDPOINTS_SHOULD_BE_VERSIONED = methods().that()
        .areNotPrivate()
        .and()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .or()
        .areMetaAnnotatedWith(RestController.class)
        .should(new ApiVersioningCheck(API_VERSIONING_PATTERNS))
        .allowEmptyShould(true)
        .because("All endpoints should be versioned");
}
