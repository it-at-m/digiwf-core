package de.muenchen.oss.digiwf.address.integration.adapter.out;

import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.client.api.AddressGermanyApi;
import de.muenchen.oss.digiwf.address.integration.client.api.AddressMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.api.StreetsMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressClientOutAdapter implements AddressClientOutPort {

    private final AddressGermanyApi addressGermanyApi;
    private final AddressMunichApi addressMunichApi;
    private final StreetsMunichApi streetsMunichApi;

    @Override
    public BundesweiteAdresseResponse searchAddresses(SearchAddressesGermanyModel searchAddressesGermanyModel) throws BpmnError, IncidentError {
        try {
            return this.addressGermanyApi.searchAddresses(searchAddressesGermanyModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public MuenchenAdresse checkAddress(CheckAddressesModel checkAddressesModel) throws BpmnError, IncidentError {
        try {
            return this.addressMunichApi.checkAddress(checkAddressesModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public MuenchenAdresseResponse listAddresses(ListAddressesModel listAddressesModel) throws BpmnError, IncidentError {
        try {
            return this.addressMunichApi.listAddresses(listAddressesModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public AenderungResponse listChanges(ListAddressChangesModel listAddressChangesModel) throws BpmnError, IncidentError {
        try {
            return this.addressMunichApi.listChanges(listAddressChangesModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public MuenchenAdresseResponse searchAddresses(SearchAddressesModel searchAddressesModel) throws BpmnError, IncidentError {
        try {
            return this.addressMunichApi.searchAddresses(searchAddressesModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public AddressDistancesModel searchAddressesGeo(SearchAddressesGeoModel searchAddressesGeoModel) throws BpmnError, IncidentError {
        try {
            return this.addressMunichApi.searchAddressesGeo(searchAddressesGeoModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public Strasse findStreetsById(long streetId) throws BpmnError, IncidentError {
        try {
            return this.streetsMunichApi.findStreetsById(streetId);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public StrasseResponse listStreets(ListStreetsModel listStreetsModel) throws BpmnError, IncidentError {
        try {
            return this.streetsMunichApi.listStreets(listStreetsModel);
        } catch (final AddressServiceIntegrationClientErrorException exception) {
            throw new BpmnError("ADDRESS_SERVICE_CLIENT_ERROR", exception.getMessage());
        } catch (final AddressServiceIntegrationException | AddressServiceIntegrationServerErrorException exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

}
