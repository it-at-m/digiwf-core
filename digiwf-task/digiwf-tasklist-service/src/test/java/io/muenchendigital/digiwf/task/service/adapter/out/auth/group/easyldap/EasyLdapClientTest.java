package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyldap;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.muenchendigital.digiwf.task.service.infra.security.WithKeycloakUser;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = {"internal","itest"})
@SpringBootTest
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class EasyLdapClientTest {

    @Autowired
    private EasyLdapClient easyLdapClient;

    @Autowired
    private WireMockServer wireMockServer;

    @Test
    @WithKeycloakUser
    public void getUser () {
        WireMock.stubFor(WireMock.get("${easyldap.client.request}")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                //.withBodyFile("files/easy-ldap-response.json")
                                .withBody("{}"))
        );

        System.out.println("Hallo");


        assertEquals(this.easyLdapClient.getUserById("1234").getOu(),"OU");

    }
}
