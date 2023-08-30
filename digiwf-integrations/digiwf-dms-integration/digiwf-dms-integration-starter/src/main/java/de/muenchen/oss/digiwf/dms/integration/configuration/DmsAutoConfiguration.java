package de.muenchen.oss.digiwf.dms.integration.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.CreateVorgangDto;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.MessageProcessor;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.DmsProperties;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftClientConfiguration;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.application.service.CreateVorgangService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Import(FabasoftClientConfiguration.class)
@EnableConfigurationProperties({DmsProperties.class})
public class DmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public VorgangRepository vorgangRepository(final DmsProperties dmsProperties, LHMBAI151700GIWSDSoap wsCleint) {
        return new FabasoftAdapter(dmsProperties, wsCleint);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateVorgangUseCase createVorgangUseCase(final VorgangRepository vorgangRepository) {
        return new CreateVorgangService(vorgangRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public Consumer<Message<CreateVorgangDto>> emailMessageProcessor(final ProcessApi processApi, final ErrorApi errorApi, final CreateVorgangUseCase createVorgangUseCase) {
        final MessageProcessor messageProcessor = new MessageProcessor(processApi, errorApi, createVorgangUseCase);
        return messageProcessor.createVorgang();
    }

}
