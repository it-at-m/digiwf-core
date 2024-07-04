package de.muenchen.oss.digiwf.example.integration.core.application.port.out;

import de.muenchen.oss.digiwf.example.integration.core.domain.ExampleModel;

public interface ExampleOutPort {

    void processExampleData(final ExampleModel exampleModel);
}
