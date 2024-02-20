package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out.processdefinition;

import de.muenchen.oss.digiwf.spring.security.client.DigiwfFeignOauthClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "${feign.client.config.digiwf-process-instance.name:digiwf-process-instance}",
        url = "${feign.client.config.digiwf-process-instance.url}rest/service/instance",
        configuration = DigiwfFeignOauthClientConfig.class
)
public interface ProcessInstanceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/root/{id}")
    ServiceInstanceTO getRootProcessInstanceDetail(@PathVariable("id") final String id);

}
