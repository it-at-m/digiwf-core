/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.external.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoapService;
import de.muenchen.oss.digiwf.legacy.dms.muc.properties.DmsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;

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
        final LHMBAI151700GIWSDSoapService service = new LHMBAI151700GIWSDSoapService();
        final LHMBAI151700GIWSDSoap soapClient = service.getLHMBAI151700GIWSDSoapPort();
        ((BindingProvider) soapClient).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.properties.getAddress());
        ((BindingProvider) soapClient).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, this.properties.getUsername());
        ((BindingProvider) soapClient).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, this.properties.getPassword());
        final SOAPBinding binding = (SOAPBinding) ((BindingProvider) soapClient).getBinding();
        binding.setMTOMEnabled(true);
        return soapClient;
    }

}
