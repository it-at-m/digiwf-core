package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap;

import feign.FeignException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(profiles = {"itest"})
@SpringBootTest
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EasyLdapMockConfig.class})
public class EasyLdapClientTest {

    @Autowired
    private EasyLdapClient easyLdapClient;

    @Test
    public void getUser() {
        assertEquals(this.easyLdapClient.getUserById("1234").getOu(), "OU");
    }

    @Test
    public void getStatus404() {
        var exception = assertThrows(FeignException.class, () -> {
            this.easyLdapClient.getUserById("0");
        });

        assertEquals(404, exception.status());

    }
}
