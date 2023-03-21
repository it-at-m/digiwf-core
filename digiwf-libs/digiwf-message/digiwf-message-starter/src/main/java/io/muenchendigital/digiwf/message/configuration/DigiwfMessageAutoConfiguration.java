package io.muenchendigital.digiwf.message.configuration;

import io.muenchendigital.digiwf.message.adapter.api.OutputAdapter;
import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import io.muenchendigital.digiwf.message.core.impl.MessageApiImpl;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.ProcessApiImpl;
import io.muenchendigital.digiwf.message.process.impl.ProcessPortImpl;
import io.muenchendigital.digiwf.message.process.impl.port.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.impl.port.out.IncidentPort;
import io.muenchendigital.digiwf.message.process.impl.port.out.StartProcessPort;
import io.muenchendigital.digiwf.message.process.impl.port.out.TechnicalErrorPort;
import io.muenchendigital.digiwf.message.properties.DigiwfMessageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

/**
 * Auto configuration for the digiwf-message library.
 */
@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.message.adapter")
@EnableConfigurationProperties(value = DigiwfMessageProperties.class)
public class DigiwfMessageAutoConfiguration {

    private final DigiwfMessageProperties digiwfMessageProperties;
    private final Sinks.Many<Message<Object>> messageSink;

    /**
     * Creates the bean for the default Implementation of {@link MessageApi}.
     *
     * @param messagePort
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageApiImpl messageApiImpl(final SendMessagePort messagePort) {
        return new MessageApiImpl(messagePort);
    }

    /**
     * Creates the bean for the default Implementation of {@link ProcessApi}.
     *
     * @param correlateMessagePort
     * @param startProcessPort
     * @param incidentPort
     * @param technicalErrorPort
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ProcessApiImpl processApiImpl(
            final CorrelateMessagePort correlateMessagePort,
            final StartProcessPort startProcessPort,
            final IncidentPort incidentPort,
            final TechnicalErrorPort technicalErrorPort
            ) {
        return new ProcessApiImpl(
                this.digiwfMessageProperties.getCorrelateMessageDestination(),
                this.digiwfMessageProperties.getStartProcessDestination(),
                this.digiwfMessageProperties.getIncidentDestination(),
                this.digiwfMessageProperties.getTechnicalErrorDestination(),
                correlateMessagePort,
                startProcessPort,
                incidentPort,
                technicalErrorPort
        );
    }

    /**
     * Creates the bean for the default Implementation of {@link ProcessApi}.
     * @param processApiImpl
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ProcessApi processApi(final ProcessApiImpl processApiImpl) {
        return processApiImpl;
    }

    /**
     * Creates the bean for the default Implementation of {@link MessageApi}.
     * @param messageApiImpl
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageApi messageApi(final MessageApiImpl messageApiImpl) {
        return messageApiImpl;
    }

    /**
     * Creates the bean for the default Implementation of {@link SendMessagePort}.
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SendMessagePort sendMessagePort() {
        return this.springCloudStreamAdapter();
    }

    /**
     * Creates the bean for the default Implementation of {@link CorrelateMessagePort}, {@link StartProcessPort}, {@link IncidentPort} and {@link TechnicalErrorPort}.
     * @param messageApi
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ProcessPortImpl processAdapter(final MessageApi messageApi) {
        return new ProcessPortImpl(messageApi);
    }

    // spring cloud stream adapter

    /**
     * Creates the default Implementation of {@link SendMessagePort}.
     * @return
     */
    public OutputAdapter springCloudStreamAdapter() {
        return new OutputAdapter(this.messageSink);
    }

}
