package de.muenchen.oss.digiwf.address.integration.api.streaming;

import de.muenchen.oss.digiwf.address.integration.api.AddressGermanyApi;
import de.muenchen.oss.digiwf.address.integration.api.AddressMunichApi;
import de.muenchen.oss.digiwf.address.integration.api.StreetsMunichApi;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.*;
import de.muenchen.oss.digiwf.address.integration.api.dto.response.AddressDistancesDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.response.AddressServiceErrorDto;
import de.muenchen.oss.digiwf.address.integration.api.mapper.AddressServiceMapper;
import de.muenchen.oss.digiwf.address.integration.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.model.request.*;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AddressServiceStreamingEventListener {

    private static final String RESPONSE = "response";

    private final AddressServiceMapper addressServiceMapper;

    private final CorrelateMessageService correlateMessageService;
    private final AddressGermanyApi addressGermanyApi;
    private final AddressMunichApi addressMunichApi;
    private final StreetsMunichApi streetsMunichApi;

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link SearchAdressenBundesweitDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link BundesweiteAdresseResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> searchAdressenBundesweit() {
        return message -> {
            log.debug(message.toString());

            final var searchAdressenBundesweit = (SearchAdressenBundesweitDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final SearchAdressesGermanyModel model = this.addressServiceMapper.dto2Model(searchAdressenBundesweit);
                addressServiceResult = this.addressGermanyApi.searchAddresses(model);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link CheckAdresseMuenchenDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link MuenchenAdresse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> checkAdresseMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var checkAdresseMuenchen = (CheckAdresseMuenchenDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final CheckAddressesModel model = this.addressServiceMapper.dto2Model(checkAdresseMuenchen);
                addressServiceResult = this.addressMunichApi.checkAddress(model);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link ListAdressenMuenchenDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link MuenchenAdresseResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> listAdressenMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var listAdressenMuenchen = (ListAdressenMuenchenDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final ListAddressesModel model = this.addressServiceMapper.dto2Model(listAdressenMuenchen);
                addressServiceResult = this.addressMunichApi.listAddresses(model);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link ListAenderungenMuenchenDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link AenderungResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> listAenderungenMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var listAenderungenMuenchen = (ListAenderungenMuenchenDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final ListAddressChangesModel model = this.addressServiceMapper.dto2Model(listAenderungenMuenchen);
                addressServiceResult = this.addressMunichApi.listChanges(model);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link SearchAdressenMuenchenDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link MuenchenAdresseResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> searchAdressenMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var searchAdressenMuenchen = (SearchAdressenMuenchenDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final SearchAddressesModel model = this.addressServiceMapper.dto2Model(searchAdressenMuenchen);
                addressServiceResult = this.addressMunichApi.searchAddresses(model);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link SearchAdressenGeoMuenchenDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link AddressDistancesDto} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> searchAdressenGeoMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var searchAdressenGeoMuenchen = (SearchAdressenGeoMuenchenDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final SearchAddressesGeoModel requestModel = this.addressServiceMapper.dto2Model(searchAdressenGeoMuenchen);
                final AddressDistancesModel resultModel = this.addressMunichApi.searchAddressesGeo(requestModel);
                addressServiceResult = this.addressServiceMapper.model2Dto(resultModel);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link StrassenIdDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link Strasse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> findStrasseByIdMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var strassenId = (StrassenIdDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                addressServiceResult = this.streetsMunichApi.findStreetsById(strassenId.getStrasseId());
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link AddressServiceEventDto} which represents an {@link ListStrassenDto}.
     * <p>
     * After successfully requesting the address service a JSON representing a {@link StrasseResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link AddressServiceErrorDto}.
     */
    @Bean
    public Consumer<Message<AddressServiceEventDto>> listStrassenMuenchen() {
        return message -> {
            log.debug(message.toString());

            final var listStrassen = (ListStrassenDto) message.getPayload().getRequest();

            Object addressServiceResult;
            try {
                final ListStreetsModel model = this.addressServiceMapper.dto2Model(listStrassen);
                addressServiceResult = this.streetsMunichApi.listStreets(model);
            } catch (final Exception exception) {
                addressServiceResult = new AddressServiceErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, addressServiceResult)
            );
        };
    }

}
