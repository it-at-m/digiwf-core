package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyldap;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockEasyLdapServer() {
        return new WireMockServer(9561);
    }
}



