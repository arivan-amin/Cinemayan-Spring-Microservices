package com.maya.cbs.testing.architecture.rules;

import com.maya.cbs.testing.architecture.bases.BaseUnitTest;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

public interface CommonBestPracticeRules extends BaseUnitTest {

    String TEST_DATA_CLASSES_NAME = ".*TestData";

    @ArchTest
    ArchRule INTERFACES_SHOULD_NOT_HAVE_NAMES_ENDING_WITH_THE_WORD_INTERFACE = noClasses().that()
        .areInterfaces()
        .should()
        .haveNameMatching(".*Interface")
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INTERFACES_SHOULD_NOT_HAVE_SIMPLE_CLASS_NAMES_CONTAINING_THE_WORD_INTERFACE =
        noClasses().that()
            .areInterfaces()
            .should()
            .haveSimpleNameContaining("Interface")
            .allowEmptyShould(true);

    @ArchTest
    ArchRule NO_ACCESS_TO_STANDARD_STREAMS =
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.allowEmptyShould(true);

    @ArchTest
    ArchRule NO_GENERIC_EXCEPTIONS =
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.allowEmptyShould(true);

    @ArchTest
    ArchRule NO_JAVA_UTIL_LOGGING = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    ArchRule LOGGERS_SHOULD_BE_PRIVATE_STATIC_FINAL = fields().that()
        .haveRawType(Logger.class)
        .should()
        .bePrivate()
        .andShould()
        .beStatic()
        .andShould()
        .beFinal()
        .allowEmptyShould(true)
        .because("we agreed on this convention");

    @ArchTest
    ArchRule NO_JODA_TIME = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    ArchRule NO_FIELD_INJECTION_IN_PRODUCTION_CODE = noFields().that()
        .areDeclaredInClassesThat()
        .doNotHaveSimpleName("BaseDatabaseTest")
        .should()
        .beAnnotatedWith(Autowired.class)
        .because("always use constructor injection");

    @ArchTest
    ArchRule AVOID_DEPRECATED_API = DEPRECATED_API_SHOULD_NOT_BE_USED.allowEmptyShould(true);

    @ArchTest
    ArchRule TEST_DATA_CLASSES_SHOULD_ONLY_BE_USED_IN_TESTS = noClasses().that()
        .haveNameNotMatching(TEST_DATA_CLASSES_NAME)
        .should()
        .dependOnClassesThat()
        .haveNameMatching(TEST_DATA_CLASSES_NAME);
}
