package de.muenchen.oss.digiwf.address.integration.configuration;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.AddressMapper;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.adapter.out.AddressClientOutAdapter;
import de.muenchen.oss.digiwf.address.integration.adapter.out.IntegrationOutAdapter;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.address.integration.application.usecase.AddressesGermanyUseCase;
import de.muenchen.oss.digiwf.address.integration.application.usecase.AddressesMunichUseCase;
import de.muenchen.oss.digiwf.address.integration.application.usecase.StreetsMunichUseCase;
import de.muenchen.oss.digiwf.address.integration.client.api.AddressGermanyApi;
import de.muenchen.oss.digiwf.address.integration.client.api.AddressMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.api.StreetsMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.ApiClient;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.AdressenBundesweitApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.AdressenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.StraenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.client.impl.AddressGermanyImpl;
import de.muenchen.oss.digiwf.address.integration.client.impl.AddressesMunichImpl;
import de.muenchen.oss.digiwf.address.integration.client.impl.StreetsMunichImpl;
import de.muenchen.oss.digiwf.address.integration.properties.AddressServiceIntegrationProperties;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.messaging.Message;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;


@Configuration
@RequiredArgsConstructor
@ComponentScan(
        basePackages = "de.muenchen.oss.digiwf.address.integration",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to avoid multiple instantiation of beans with same name.
                                 * This class is instantiated in {@link AddressServiceIntegrationAutoConfiguration}
                                 * to give the bean another name.
                                 */
                                ApiClient.class,
                                AdressenBundesweitApi.class,
                                AdressenMnchenApi.class,
                                StraenMnchenApi.class
                        }
                )
        }
)
@EnableConfigurationProperties(AddressServiceIntegrationProperties.class)
public class AddressServiceIntegrationAutoConfiguration {

    public final AddressServiceIntegrationProperties addressServiceIntegrationProperties;

    /**
     * Provides a correct configured {@link ApiClient}.
     *
     * @return a configured {@link ApiClient}.
     */
    public ApiClient addressServiceApiClient() {
        ApiClient client = new ApiClient(WebClient.create(addressServiceIntegrationProperties.getUrl()));
        client.setBasePath(addressServiceIntegrationProperties.getUrl());
        return client;
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @return a bean of type {@link AdressenBundesweitApi} named by method name.
     */
    @Bean
    public AdressenBundesweitApi addressServiceAdressenBundesweitApi() {
        final ApiClient apiClient = this.addressServiceApiClient();
        return new AdressenBundesweitApi(apiClient);
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @return a bean of type {@link AdressenMnchenApi} named by method name.
     */
    @Bean
    public AdressenMnchenApi addressServiceAdressenMnchenApi() {
        final ApiClient apiClient = this.addressServiceApiClient();
        return new AdressenMnchenApi(apiClient);
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @return a bean of type {@link StraenMnchenApi} named by method name.
     */
    @Bean
    public StraenMnchenApi addressServiceStraenMnchenApi() {
        final ApiClient apiClient = this.addressServiceApiClient();
        return new StraenMnchenApi(apiClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public AddressClientOutPort addressClientOutPort(
            final AddressGermanyApi addressGermanyApi,
            final AddressMunichApi addressMunichApi,
            final StreetsMunichApi streetsMunichApi
    ) {
        return new AddressClientOutAdapter(addressGermanyApi, addressMunichApi, streetsMunichApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public IntegrationOutPort integrationOutPort(final ProcessApi processApi, final ErrorApi errorApi) {
        return new IntegrationOutAdapter(processApi, errorApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public AddressGermanyInPort addressGermanyInPort(final AddressClientOutPort addressClientOutPort) {
        return new AddressesGermanyUseCase(addressClientOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public AddressMunichInPort addressMunichInPort(final AddressClientOutPort addressClientOutPort) {
        return new AddressesMunichUseCase(addressClientOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public StreetsMunichInPort streetsMunichInPort(final AddressClientOutPort addressClientOutPort) {
        return new StreetsMunichUseCase(addressClientOutPort);
    }

    // client api

    @ConditionalOnMissingBean
    @Bean
    public AddressGermanyApi addressGermanyApi(final AdressenBundesweitApi apiClient) {
        return new AddressGermanyImpl(apiClient);
    }

    @ConditionalOnMissingBean
    @Bean
    public AddressMunichApi addressMunichApi(final AdressenMnchenApi apiClient) {
        return new AddressesMunichImpl(apiClient);
    }

    @ConditionalOnMissingBean
    @Bean
    public StreetsMunichApi munichStreetApi(final StraenMnchenApi apiClient) {
        return new StreetsMunichImpl(apiClient);
    }

    // streaming adapter in

    @ConditionalOnMissingBean
    @Bean
    public MessageProcessor messageProcessor(
            final AddressGermanyInPort addressGermanyInPort,
            final AddressMunichInPort addressMunichInPort,
            final StreetsMunichInPort streetsMunichInPort,
            final IntegrationOutPort integrationOutPort,
            final AddressMapper addressServiceMapper
    ) {
        return new MessageProcessor(
                addressGermanyInPort,
                addressMunichInPort,
                streetsMunichInPort,
                integrationOutPort,
                addressServiceMapper
        );
    }

    @Bean
    public Consumer<Message<SearchAdressenDeutschlandDto>> searchAddressesGermany(final MessageProcessor messageProcessor) {
        return messageProcessor.searchAddressesGermany();
    }

    @Bean
    public Consumer<Message<CheckAdresseMuenchenDto>> checkAddressMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.checkAddressMunich();
    }

    @Bean
    public Consumer<Message<ListAdressenMuenchenDto>> listAddressesMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.listAddressesMunich();
    }

    @Bean
    public Consumer<Message<ListAenderungenMuenchenDto>> listChangesMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.listChangesMunich();
    }

    @Bean
    public Consumer<Message<SearchAdressenMuenchenDto>> searchAddressesMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.searchAddressesMunich();
    }

    @Bean
    public Consumer<Message<SearchAdressenGeoMuenchenDto>> searchAddressesGeoMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.searchAddressesGeoMunich();
    }

    @Bean
    public Consumer<Message<StrassenIdDto>> findStreetByIdMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.findStreetByIdMunich();
    }

    @Bean
    public Consumer<Message<ListStrassenDto>> listStreetMunich(final MessageProcessor messageProcessor) {
        return messageProcessor.listStreetMunich();
    }
}
