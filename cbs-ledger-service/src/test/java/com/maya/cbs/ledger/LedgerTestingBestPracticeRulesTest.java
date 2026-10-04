package com.maya.cbs.ledger;

import com.maya.cbs.testing.architecture.rules.TestingBestPracticeRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;

import static com.maya.cbs.core.domain.config.CoreApplicationConfig.BASE_PACKAGE;

@AnalyzeClasses (packages = BASE_PACKAGE, importOptions = ImportOption.OnlyIncludeTests.class)
class LedgerTestingBestPracticeRulesTest implements TestingBestPracticeRules {

}
