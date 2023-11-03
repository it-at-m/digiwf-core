package de.muenchen.oss.digiwf.gateway.annotations;

import de.muenchen.oss.digiwf.gateway.ApiGatewayApplication;
import de.muenchen.oss.digiwf.gateway.TestConstants;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = { ApiGatewayApplication.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(TestConstants.SPRING_TEST_PROFILE)
@AutoConfigureObservability
public @interface ApiGatewayTest {
}
