package de.muenchen.oss.digiwf.dms.integration.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.*;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftClientConfiguration;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftProperties;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.*;
import de.muenchen.oss.digiwf.dms.integration.application.service.*;
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
    public S3Adapter s3Adapter(DocumentStorageFileRepository documentStorageFileRepository, DocumentStorageFolderRepository documentStorageFolderRepository) {
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
    public UpdateDocumentUseCase updateDocumentUseCase(final UpdateDocumentPort updateDocumentPort, LoadFilePort loadFilePort) {
        return new UpdateDocumentService(updateDocumentPort, loadFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public DepositObjectUseCase depositObjectUseCase(DepositObjectPort depositObjectPort) {
        return new DepositObjectService(depositObjectPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CancelObjectUseCase cancelObjectUseCase(CancelObjectPort cancelObjectPort) {
        return new CancelObjectService(cancelObjectPort);
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
    public Consumer<Message<UpdateDocumentDto>> updateDocumentMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.updateDocument();
    }

    @Bean
    public Consumer<Message<DepositObjectDto>> depositObjectMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.depositObject();
    }

    @Bean
    public Consumer<Message<CancelObjectDto>> cancelObjectMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.cancelObject();
    }

    @Bean
    public Consumer<Message<ReadContentDto>> readContentMessageProcessor(final MessageProcessor messageProcessor) {
        return messageProcessor.readContent();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageProcessor createMessageProcessor(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final CreateProcedureUseCase createProcedureUseCase,
            final CreateDocumentUseCase createDocumentUseCase,
            final UpdateDocumentUseCase updateDocumentUseCase,
            final DepositObjectUseCase depositObjectUseCase,
            final CancelObjectUseCase cancelObjectUseCase,
            final ReadContentUseCase readContentUseCase) {
        return new MessageProcessor(
                processApi,
                errorApi,
                createProcedureUseCase,
                createDocumentUseCase,
                updateDocumentUseCase,
                depositObjectUseCase,
                cancelObjectUseCase,
                readContentUseCase);
    }

}
