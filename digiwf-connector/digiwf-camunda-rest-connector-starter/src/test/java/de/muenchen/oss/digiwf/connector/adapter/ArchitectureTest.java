package de.muenchen.oss.digiwf.connector.adapter;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.muenchen.oss.digiwf.archunit.HexagonalArchitecture;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {


    @Test
    void validateArchitecture() {
        HexagonalArchitecture.basePackage("de.muenchen.oss.digiwf.connector")
                .withAdaptersLayer("adapter.camunda.rest")
                .incoming("in")
                .outgoing("out")
                .and()
                .withApplicationLayer("core.application")
                .incomingPorts("port.in")
                .outgoingPorts("port.out")
                .and()
                .check(new ClassFileImporter()
                        .importPackages("de.muenchen.oss.digiwf.connector.."));
    }
}
