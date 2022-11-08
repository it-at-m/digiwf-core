package io.muenchendigital.digiwf.s3.integration.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class S3IntegrationClientApplication {

    public static void main(final String[] args) {
        SpringApplication.run(S3IntegrationClientApplication.class, args);
    }

}
