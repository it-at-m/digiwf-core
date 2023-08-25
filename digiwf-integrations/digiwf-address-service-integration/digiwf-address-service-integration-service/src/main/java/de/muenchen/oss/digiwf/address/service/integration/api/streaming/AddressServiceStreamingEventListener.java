package de.muenchen.oss.digiwf.address.service.integration.api.streaming;

import de.muenchen.oss.digiwf.address.service.integration.api.dto.response.AddressDistancesDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.response.AddressServiceErrorDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.AddressServiceEventDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.CheckAdresseMuenchenDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.ListAdressenMuenchenDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.ListAenderungenMuenchenDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.ListStrassenDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.SearchAdressenBundesweitDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.SearchAdressenGeoMuenchenDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.SearchAdressenMuenchenDto;
import de.muenchen.oss.digiwf.address.service.integration.api.dto.request.StrassenIdDto;
import de.muenchen.oss.digiwf.address.service.integration.api.mapper.AddressServiceMapper;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.service.integration.model.request.CheckAdresseMuenchenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.ListAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.ListAenderungenMuenchenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.ListStrassenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.SearchAdressenBundesweitModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.SearchAdressenGeoMuenchenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.SearchAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.StrassenIdModel;
import de.muenchen.oss.digiwf.address.service.integration.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.address.service.integration.service.AdressenBundesweitService;
import de.muenchen.oss.digiwf.address.service.integration.service.AdressenMuenchenService;
import de.muenchen.oss.digiwf.address.service.integration.service.StrassenMuenchenService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
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

    private final AdressenBundesweitService adressenBundesweitService;

    private final AdressenMuenchenService adressenMuenchenService;

    private final StrassenMuenchenService strassenMuenchenService;

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
                final SearchAdressenBundesweitModel model = this.addressServiceMapper.dto2Model(searchAdressenBundesweit);
                addressServiceResult = this.adressenBundesweitService.searchAdressen(model);
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
                final CheckAdresseMuenchenModel model = this.addressServiceMapper.dto2Model(checkAdresseMuenchen);
                addressServiceResult = this.adressenMuenchenService.checkAdresse(model);
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
                final ListAdressenMuenchenModel model = this.addressServiceMapper.dto2Model(listAdressenMuenchen);
                addressServiceResult = this.adressenMuenchenService.listAdressen(model);
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
                final ListAenderungenMuenchenModel model = this.addressServiceMapper.dto2Model(listAenderungenMuenchen);
                addressServiceResult = this.adressenMuenchenService.listAenderungen(model);
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
                final SearchAdressenMuenchenModel model = this.addressServiceMapper.dto2Model(searchAdressenMuenchen);
                addressServiceResult = this.adressenMuenchenService.searchAdressen(model);
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
                final SearchAdressenGeoMuenchenModel requestModel = this.addressServiceMapper.dto2Model(searchAdressenGeoMuenchen);
                final AddressDistancesModel resultModel = this.adressenMuenchenService.searchAdressenGeo(requestModel);
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
                final StrassenIdModel model = this.addressServiceMapper.dto2Model(strassenId);
                addressServiceResult = this.strassenMuenchenService.findStrasseById(model);
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
                final ListStrassenModel model = this.addressServiceMapper.dto2Model(listStrassen);
                addressServiceResult = this.strassenMuenchenService.listStrassen(model);
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
