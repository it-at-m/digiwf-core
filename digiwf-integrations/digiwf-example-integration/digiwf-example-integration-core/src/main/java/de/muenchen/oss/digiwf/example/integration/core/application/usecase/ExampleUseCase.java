package de.muenchen.oss.digiwf.example.integration.core.application.usecase;

import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ExampleOutPort;
import de.muenchen.oss.digiwf.example.integration.core.domain.ExampleModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExampleUseCase implements ExampleInPort {
    private final ExampleOutPort exampleOutPort;

    @Override
    public void processExampleData(final ExampleModel exampleModel) {
        exampleOutPort.processExampleData(exampleModel);
    }
}
