/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.external.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSD;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import io.muenchendigital.digiwf.legacy.dms.muc.properties.DmsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.BindingProvider;

/**
 * Configuration of the Webservice Client.
 *
 * @author externer.dl.horn
 */
@Configuration
@RequiredArgsConstructor
public class WsClientConfiguration {

    private final DmsProperties properties;

    @Bean
    public LHMBAI151700GIWSDSoap dmsWsClient() {
        final LHMBAI151700GIWSDSoap client;
        final LHMBAI151700GIWSD service = new LHMBAI151700GIWSD();
        client = service.getLHMBAI151700GIWSDSoap();
        this.configure((BindingProvider) client);
        return client;
    }

    // configure target address and technical username + password
    private void configure(final BindingProvider bp) {
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.properties.getAddress());
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, this.properties.getUsername());
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, this.properties.getPassword());
    }
}
