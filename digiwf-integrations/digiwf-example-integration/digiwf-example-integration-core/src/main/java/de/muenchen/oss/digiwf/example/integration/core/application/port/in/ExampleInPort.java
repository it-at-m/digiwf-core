package de.muenchen.oss.digiwf.example.integration.core.application.port.in;

import de.muenchen.oss.digiwf.example.integration.core.domain.ExampleModel;

public interface ExampleInPort {

    void processExampleData(final ExampleModel exampleModel);

}
