package de.muenchen.oss.digiwf.process.api.config.impl;

import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import de.muenchen.oss.digiwf.spring.security.client.DigiwfFeignOauthClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ProcessConfigClient is the feign client to obtain the process configuration.
 */
@FeignClient(
        name = "${feign.client.config.digiwf-process-config.name:digiwf-process-api}",
        url = "${feign.client.config.digiwf-process-config.url}",
        configuration = DigiwfFeignOauthClientConfig.class
)
public interface ProcessConfigClient {

    /**
     * Feign Client to obtain the process configuration from digiwf-engine.
     *
     * @param processDefinitionId the process definition id
     * @return the process configuration
     */
    @RequestMapping(method = RequestMethod.GET, value = "/rest/processconfig/{key}")
    ProcessConfigTO getProcessConfig(@PathVariable("key") final String processDefinitionId);

}
