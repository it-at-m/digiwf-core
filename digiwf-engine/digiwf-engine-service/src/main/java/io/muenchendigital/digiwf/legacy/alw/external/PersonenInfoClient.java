/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.alw.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.muenchendigital.digiwf.legacy.alw.properties.AlwProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

/**
 * Client for Personeninfo EAI
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PersonenInfoClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AlwProperties properties;

    /**
     * Get the responsible Ldap group for the given AZR number.
     *
     * @param azr AZR number
     * @return the responsible Ldap group
     * @throws JsonProcessingException
     */
    public String getResponsibleLdapGroup(final String azr) throws JsonProcessingException {

        //KVR_EAI
        final String restCallFullUrl = this.properties.getEaiUrl() + "camel/foreigners/azr/" + azr + "/responsible-ldap-group";
        log.info("calling getResponsibleLdapGroup: {}", restCallFullUrl);
        final String resultJSON = this.restTemplate.getForObject(restCallFullUrl, String.class);
        @SuppressWarnings("unchecked") final HashMap<String, Object> resultMAP = new ObjectMapper().readValue(resultJSON, HashMap.class);

        return Optional.ofNullable(resultMAP.get("group"))
                .map(Object::toString)
                .orElse(null);
    }

}
