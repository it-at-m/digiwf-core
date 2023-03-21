package io.muenchendigital.digiwf.message.configuration;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import io.muenchendigital.digiwf.message.core.impl.MessageApiImpl;
import io.muenchendigital.digiwf.message.core.impl.MessagePortImpl;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.api.out.BpmnErrorPort;
import io.muenchendigital.digiwf.message.process.api.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.api.out.IncidentPort;
import io.muenchendigital.digiwf.message.process.api.out.StartProcessPort;
import io.muenchendigital.digiwf.message.process.impl.ProcessApiImpl;
import io.muenchendigital.digiwf.message.process.impl.ProcessPortImpl;
import io.muenchendigital.digiwf.message.properties.DigiwfMessageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

/**
 * Autoconfiguration for the digiwf-message library.
 */
@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.message.infra")
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
    public MessageApi messageApi(final SendMessagePort messagePort) {
        return new MessageApiImpl(messagePort);
    }

    /**
     * Creates the bean for the default Implementation of {@link ProcessApi}.
     *
     * @param correlateMessagePort
     * @param startProcessPort
     * @param incidentPort
     * @param bpmnErrorPort
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ProcessApi processApi(
            final CorrelateMessagePort correlateMessagePort,
            final StartProcessPort startProcessPort,
            final IncidentPort incidentPort,
            final BpmnErrorPort bpmnErrorPort
            ) {
        return new ProcessApiImpl(
                this.digiwfMessageProperties.getCorrelateMessageDestination(),
                this.digiwfMessageProperties.getStartProcessDestination(),
                this.digiwfMessageProperties.getIncidentDestination(),
                this.digiwfMessageProperties.getBpmnErrorDestination(),
                correlateMessagePort,
                startProcessPort,
                incidentPort,
                bpmnErrorPort
        );
    }

    /**
     * Creates the bean for the default Implementation of {@link SendMessagePort}.
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SendMessagePort sendMessagePort() {
        return new MessagePortImpl(this.messageSink);
    }

    @Bean
    @ConditionalOnMissingBean
    public CorrelateMessagePort correlateMessagePort(final MessageApi messageApi) {
        return new ProcessPortImpl(messageApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public StartProcessPort startProcessPort(final MessageApi messageApi) {
        return new ProcessPortImpl(messageApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public IncidentPort incidentPort(final MessageApi messageApi) {
        return new ProcessPortImpl(messageApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public BpmnErrorPort bpmnErrorPort(final MessageApi messageApi) {
        return new ProcessPortImpl(messageApi);
    }

}
