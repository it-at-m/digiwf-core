package de.muenchen.oss.digiwf.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/gateway/session")
@Slf4j
public class SessionTimeoutController {

    @Value("${spring.session.timeout}")
    private Integer timeout;

    @GetMapping
    public Map<String, Integer> getSessionTimeout () {
        return Map.of("timeoutInSeconds", timeout);
    }
}
