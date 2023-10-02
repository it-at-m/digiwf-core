package de.muenchen.oss.digiwf.dms.integration.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.CreateDocumentDto;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.CreateProcedureDto;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.DepositObjectDto;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.MessageProcessor;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftClientConfiguration;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftProperties;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateProcedureUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.DepositObjectUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateProcedurePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DepositObjectPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.service.CreateDocumentService;
import de.muenchen.oss.digiwf.dms.integration.application.service.CreateProcedureService;
import de.muenchen.oss.digiwf.dms.integration.application.service.DepositObjectService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
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
@EnableConfigurationProperties({FabasoftProperties.class, DmsProperties.class})
public class DmsAutoConfiguration {

    private final DmsProperties dmsProperties;

    @Bean
    @ConditionalOnMissingBean
    public FabasoftAdapter fabasoftAdapter(final FabasoftProperties dmsProperties, LHMBAI151700GIWSDSoap wsCleint) {
        return new FabasoftAdapter(dmsProperties, wsCleint);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoadFilePort loadFilePort(DocumentStorageFileRepository documentStorageFileRepository, DocumentStorageFolderRepository documentStorageFolderRepository) {
        return new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, dmsProperties.getSupportedExtensions());
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateProcedureUseCase createProcedureUseCase(final CreateProcedurePort createProcedurePort) {
        return new CreateProcedureService(createProcedurePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateDocumentUseCase createDocumentUseCase(final CreateDocumentPort createDocumentPort, LoadFilePort loadFilePort) {
        return new CreateDocumentService(createDocumentPort, loadFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public DepositObjectUseCase depositObjectUseCase(DepositObjectPort depositObjectPort) {
        return new DepositObjectService(depositObjectPort);
    }

    @Bean
    public Consumer<Message<CreateProcedureDto>> createProcedureMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.createProcedure();
    }

    @Bean
    public Consumer<Message<CreateDocumentDto>> createDocumentMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.createDocument();
    }

    @Bean
    public Consumer<Message<DepositObjectDto>> depositObjectMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.depositObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageProcessor createMessageProcessor(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final CreateProcedureUseCase createProcedureUseCase,
            final CreateDocumentUseCase createDocumentUseCase,
            final DepositObjectUseCase depositObjectUseCase) {
        return new MessageProcessor(processApi, errorApi, createProcedureUseCase, createDocumentUseCase, depositObjectUseCase);
    }

}
