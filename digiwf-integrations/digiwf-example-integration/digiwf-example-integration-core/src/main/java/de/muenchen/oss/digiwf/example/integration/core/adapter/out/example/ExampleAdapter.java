package de.muenchen.oss.digiwf.example.integration.core.adapter.out.example;

import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ExampleOutPort;
import de.muenchen.oss.digiwf.example.integration.core.domain.ExampleModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExampleAdapter implements ExampleOutPort {
    @Override
    public void processExampleData(ExampleModel exampleModel) {
        if (exampleModel.getSomeData() == null || exampleModel.getSomeData().isBlank()) {
            throw new BpmnError("someDataMissing", "Some data is missing");
        }
        log.info("Processing example data: {}", exampleModel);
    }
}
