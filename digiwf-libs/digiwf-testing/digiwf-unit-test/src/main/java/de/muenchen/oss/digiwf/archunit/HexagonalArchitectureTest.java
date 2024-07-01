package de.muenchen.oss.digiwf.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.ArchTest;
import lombok.val;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static java.util.stream.Collectors.groupingBy;

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
        testSubpackagesNotDependOnEachOther(classes.that(JavaClass.Predicates.resideInAPackage(ADAPTER_IN)));
        // adapter out
        classes().that().resideInAPackage(ADAPTER_OUT).and().haveSimpleNameEndingWith("Adapter")
                .should().implement(JavaClass.Predicates.resideInAPackage(APPLICATION_PORT_OUT)).check(classes);
        noClasses().that().resideInAPackage(ADAPTER_OUT)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATION_PORT_IN, APPLICATION_USE_CASE, ADAPTER_IN).check(classes);
        testSubpackagesNotDependOnEachOther(classes.that(JavaClass.Predicates.resideInAPackage(ADAPTER_OUT)));
        // use case
        classes().that().resideInAPackage(APPLICATION_USE_CASE)
                .should().implement(JavaClass.Predicates.resideInAPackage(APPLICATION_PORT_IN)).check(classes);
        noClasses().that().resideInAPackage(APPLICATION_USE_CASE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(ADAPTER_OUT, ADAPTER_IN).check(classes);
    }

    void testSubpackagesNotDependOnEachOther(final JavaClasses classes) {
        val classesByPackage = classes.stream().collect(groupingBy(JavaClass::getPackageName));
        for (val pkg : classesByPackage.entrySet()) {
            for (val pkg2 : classesByPackage.entrySet()) {
                if (!pkg.getKey().equals(pkg2.getKey())) {
                    noClasses()
                            .that()
                            .resideInAPackage(pkg.getKey())
                            .should()
                            .dependOnClassesThat()
                            .resideInAnyPackage(pkg2.getKey())
                            .check(classes);
                }
            }
        }
    }
}
