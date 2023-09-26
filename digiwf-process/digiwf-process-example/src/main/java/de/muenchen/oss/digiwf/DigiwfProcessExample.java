package de.muenchen.oss.digiwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DigiwfProcessExample {

    public static void main(final String[] args) {
        SpringApplication.run(DigiwfProcessExample.class, args);
    }

}
