package de.muenchen.oss.digiwf.task.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskListApplication {
    public static void main(String... args) {
        SpringApplication.run(TaskListApplication.class, args);
    }
}
