package de.muenchen.oss.digiwf;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ProcessConfigApi processConfigApi;

    @GetMapping("/test/{processInstanceId}")
    public ProcessConfigTO test(@PathVariable String processInstanceId) {
        return processConfigApi.getProcessConfig(processInstanceId);
    }

}
