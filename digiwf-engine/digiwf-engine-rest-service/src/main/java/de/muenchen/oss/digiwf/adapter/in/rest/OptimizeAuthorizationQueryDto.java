package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.camunda.bpm.engine.rest.dto.authorization.AuthorizationQueryDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Authorization query retrieved from Optimize.
 */
public class OptimizeAuthorizationQueryDto extends AuthorizationQueryDto {

    public OptimizeAuthorizationQueryDto(ObjectMapper objectMapper, MultivaluedHashMap<String, String> kvMultivaluedHashMap) {
        super(objectMapper, kvMultivaluedHashMap);
    }

    public List<String> getUserIdIn() {
        if (userIdIn == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(userIdIn);
    }

    public List<String> getGroupIdIn() {
        if (groupIdIn == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(groupIdIn);
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }
}
