package de.muenchen.oss.digiwf.process.api.config.impl;

import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "${feign.client.config.digiwf-process-config.name:digiwf-process-api}",
        url = "${feign.client.config.digiwf-process-config.url:${feign.client.config.default.url:http://localhost:39146}}",
        configuration = FeignConfig.class
)
public interface ProcessConfigClient {

    @RequestMapping(method = RequestMethod.GET, value = "/rest/processconfig/{key}", consumes = "application/json")
    ProcessConfigTO getProcessConfig(@PathVariable("key") final String engine);

}
