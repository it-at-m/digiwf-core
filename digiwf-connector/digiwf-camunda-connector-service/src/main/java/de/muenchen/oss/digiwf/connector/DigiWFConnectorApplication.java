package de.muenchen.oss.digiwf.connector;

import org.camunda.community.rest.EnableCamundaRestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableCamundaRestClient
public class DigiWFConnectorApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DigiWFConnectorApplication.class, args);
    }

}
