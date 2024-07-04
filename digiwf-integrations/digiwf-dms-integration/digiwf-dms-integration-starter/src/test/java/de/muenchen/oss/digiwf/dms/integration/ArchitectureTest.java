package de.muenchen.oss.digiwf.dms.integration;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import de.muenchen.oss.digiwf.archunit.HexagonalArchitectureTest;

@AnalyzeClasses
public class ArchitectureTest {
    @ArchTest
    static final ArchTests hexagonalArchitecture = ArchTests.in(HexagonalArchitectureTest.class);
}
