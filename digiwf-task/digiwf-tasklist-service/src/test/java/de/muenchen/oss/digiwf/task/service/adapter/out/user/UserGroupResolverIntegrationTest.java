package de.muenchen.oss.digiwf.task.service.adapter.out.user;

import de.muenchen.oss.digiwf.task.service.TaskListApplication;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@SpringBootTest(
        classes = TaskListApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles({"itest", "embedded-kafka", "no-ldap"})
@AutoConfigureMockMvc(addFilters = false)
@EmbeddedKafka(
        partitions = 1,
        topics = {"plf_data_entries", "plf_tasks"}
)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@Disabled("activate again after migration of connector to SpringBoot 3")
public class UserGroupResolverIntegrationTest {

    @Autowired
    private UserGroupResolverPort userGroupResolverPort;

    @Test
    public void getUserReturnsOUTree() {
        final Set<String> groups = this.userGroupResolverPort.resolveGroups("123456789");
        Assertions.assertEquals(Set.of("foo", "bar", "group1"), groups);
    }

}
