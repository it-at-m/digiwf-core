package de.muenchen.oss.digiwf.example.integration;


import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfE2eTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@DigiwfE2eTest
@ActiveProfiles("itest")
class ExampleIntegrationServiceApplicationTest {


  @Test
  void shouldStartContext() {

  }
}
