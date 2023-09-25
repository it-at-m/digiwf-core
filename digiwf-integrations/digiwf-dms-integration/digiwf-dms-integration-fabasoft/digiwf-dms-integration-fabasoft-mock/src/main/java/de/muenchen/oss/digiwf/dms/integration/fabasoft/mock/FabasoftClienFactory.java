/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSD;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;

import javax.xml.ws.BindingProvider;

/**
 * Configuration of the Webservice Client.
 *
 * @author externer.dl.horn
 */
public class FabasoftClienFactory {


    public static LHMBAI151700GIWSDSoap dmsWsClient(String address) {
        final LHMBAI151700GIWSD service = new LHMBAI151700GIWSD();
        final LHMBAI151700GIWSDSoap soapClient = service.getLHMBAI151700GIWSDSoap();
        ((BindingProvider) soapClient).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return soapClient;
    }

}
