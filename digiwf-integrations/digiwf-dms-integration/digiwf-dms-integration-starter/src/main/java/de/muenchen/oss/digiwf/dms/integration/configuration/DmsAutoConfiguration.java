package de.muenchen.oss.digiwf.dms.integration.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.CreateProcedureDto;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.MessageProcessor;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftClientConfiguration;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftProperties;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateProcedureUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.application.service.CreateProcedureService;
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
@EnableConfigurationProperties({FabasoftProperties.class})
public class DmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ProcedureRepository procedureRepository(final FabasoftProperties dmsProperties, LHMBAI151700GIWSDSoap wsCleint) {
        return new FabasoftAdapter(dmsProperties, wsCleint);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateProcedureUseCase createProcedureUseCase(final ProcedureRepository vorgangRepository) {
        return new CreateProcedureService(vorgangRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public Consumer<Message<CreateProcedureDto>> createProcedureMessageProcessor(final ProcessApi processApi, final ErrorApi errorApi, final CreateProcedureUseCase createProcedureUseCase) {
        final MessageProcessor messageProcessor = new MessageProcessor(processApi, errorApi, createProcedureUseCase);
        return messageProcessor.createProcedure();
    }

}
