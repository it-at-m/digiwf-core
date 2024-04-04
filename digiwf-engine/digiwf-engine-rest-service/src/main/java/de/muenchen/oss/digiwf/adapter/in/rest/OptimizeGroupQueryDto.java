package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.camunda.bpm.engine.rest.dto.identity.GroupQueryDto;

/**
 * Group query retrieved from optimize.
 */
public class OptimizeGroupQueryDto extends GroupQueryDto {

    public OptimizeGroupQueryDto(ObjectMapper objectMapper, MultivaluedHashMap<String, String> kvMultivaluedHashMap) {
        super(objectMapper, kvMultivaluedHashMap);
    }

    public String getMember() {
        return member;
    }
}
