package de.muenchen.oss.digiwf.s3.integration;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming.CreatePresignedUrlEvent;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import de.muenchen.oss.digiwf.spring.security.autoconfiguration.SpringSecurityAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = S3IntegrationApplication.class
)
@ActiveProfiles({"itest", "local"})
@EnableAutoConfiguration(
    exclude = {
        SpringSecurityAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        OAuth2ResourceServerAutoConfiguration.class
    }
)
@EmbeddedKafka(
    partitions = 1,
    topics = {
        "${spring.cloud.stream.bindings.functionRouter-in-0.destination}",
        "${spring.cloud.stream.bindings.sendMessage-out-0.destination}"
    }
)
public class ServiceIntegrationITest {

  @MockBean
  private UserAuthenticationProvider provider;

  @Autowired(required = false)
  private CreatePresignedUrlsInPort port;

  @Autowired(required = false)
  private Consumer<Message<CreatePresignedUrlEvent>> createPresignedUrl;

  @Test
  void starts_service() {
    assertThat(port).isNotNull();
    assertThat(createPresignedUrl).isNotNull();
  }
}
