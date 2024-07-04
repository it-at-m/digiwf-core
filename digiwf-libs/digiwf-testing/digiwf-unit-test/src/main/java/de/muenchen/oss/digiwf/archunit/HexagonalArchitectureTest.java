package de.muenchen.oss.digiwf.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class HexagonalArchitectureTest {
    private static final String ADAPTER = "..adapter..";
    private static final String ADAPTER_IN = "..adapter.in.(*)..";
    private static final String ADAPTER_OUT = "..adapter.out.(*)..";
    private static final String APPLICATION_PORT_IN = "..application.port.in..";
    private static final String APPLICATION_PORT_OUT = "..application.port.out..";
    private static final String APPLICATION_USE_CASE = "..application.usecase..";
    private static final String DOMAIN = "..domain..";

    @ArchTest
    public void structure(final JavaClasses classes) {
        // adapter
        classes().that().resideInAPackage(ADAPTER)
                .should().resideInAnyPackage(ADAPTER_IN, ADAPTER_OUT).check(classes);
    }

    @ArchTest
    public void packagesExist(final JavaClasses classes) {
        // adapter in
        classes().that().resideInAPackage(ADAPTER_IN)
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
        // adapter out
        classes().that().resideInAPackage(ADAPTER_OUT)
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
        // port in
        classes().that().resideInAPackage(APPLICATION_PORT_IN)
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
        // port out
        classes().that().resideInAPackage(APPLICATION_PORT_OUT)
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
        // use case
        classes().that().resideInAPackage(APPLICATION_USE_CASE)
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
    }

    @ArchTest
    public void namingConventions(final JavaClasses classes) {
        // adapter in
        classes().that().resideInAPackage(ADAPTER_IN).and().haveSimpleNameEndingWith("Adapter")
                .and().areTopLevelClasses()
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
        // adapter out
        classes().that().resideInAPackage(ADAPTER_OUT).and().haveSimpleNameEndingWith("Adapter")
                .and().areTopLevelClasses()
                .should().containNumberOfElements(DescribedPredicate.greaterThan(0)).check(classes);
        // port in
        classes().that().resideInAPackage(APPLICATION_PORT_IN)
                .and().areTopLevelClasses()
                .should().haveSimpleNameEndingWith("InPort").check(classes);
        // port out
        classes().that().resideInAPackage(APPLICATION_PORT_OUT)
                .and().areTopLevelClasses()
                .should().haveSimpleNameEndingWith("OutPort").check(classes);
        // use case
        classes().that().resideInAPackage(APPLICATION_USE_CASE)
                .and().areTopLevelClasses()
                .should().haveSimpleNameEndingWith("UseCase").check(classes);
    }

    @ArchTest
    public void dependencies(final JavaClasses classes) {
        // adapter in
        classes().that().resideInAPackage(ADAPTER_IN).and().haveSimpleNameEndingWith("Adapter")
                .should().dependOnClassesThat().resideInAPackage(APPLICATION_PORT_IN).check(classes);
        noClasses().that().resideInAPackage(ADAPTER_IN)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATION_PORT_OUT, APPLICATION_USE_CASE, ADAPTER_OUT).check(classes);
        slices().matching(ADAPTER_IN).should().notDependOnEachOther();
        // adapter out
        classes().that().resideInAPackage(ADAPTER_OUT).and().haveSimpleNameEndingWith("Adapter")
                .should().implement(JavaClass.Predicates.resideInAPackage(APPLICATION_PORT_OUT)).check(classes);
        noClasses().that().resideInAPackage(ADAPTER_OUT)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATION_PORT_IN, APPLICATION_USE_CASE, ADAPTER_IN).check(classes);
        slices().matching(ADAPTER_IN).should().notDependOnEachOther();
        // use case
        classes().that().resideInAPackage(APPLICATION_USE_CASE)
                .should().implement(JavaClass.Predicates.resideInAPackage(APPLICATION_PORT_IN)).check(classes);
        noClasses().that().resideInAPackage(APPLICATION_USE_CASE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(ADAPTER_OUT, ADAPTER_IN).check(classes);
    }
}
