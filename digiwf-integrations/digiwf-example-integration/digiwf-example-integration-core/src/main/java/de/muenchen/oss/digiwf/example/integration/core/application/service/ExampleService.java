package de.muenchen.oss.digiwf.example.integration.core.application.service;

import de.muenchen.oss.digiwf.example.integration.core.application.in.ExampleUseCase;
import de.muenchen.oss.digiwf.example.integration.core.domain.ExampleModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExampleService implements ExampleUseCase {

    @Override
    public void processExampleData(final ExampleModel exampleModel) {
        if (exampleModel.getSomeData() == null || exampleModel.getSomeData().isBlank()) {
            throw new BpmnError("someDataMissing", "Some data is missing");
        }
        log.info("Processing example data: {}", exampleModel);
    }
}
