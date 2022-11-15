package io.muenchendigital.digiwf.integration.cosys.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class DigiwfCosysExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DigiwfCosysExampleApplication.class, args);
    }

}
