package de.muenchen.oss.digiwf.dms.integration.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.*;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.auth.DmsUserAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.auth.MockDmsUserAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftClientConfiguration;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftProperties;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.*;
import de.muenchen.oss.digiwf.dms.integration.application.usecase.*;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
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
    public LHMBAI151700GIWSDSoap wsCleint(final FabasoftClientConfiguration fabasoftClientConfiguration) {
        return fabasoftClientConfiguration.dmsWsClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public S3Adapter s3Adapter(DocumentStorageFileRepository documentStorageFileRepository, DocumentStorageFolderRepository documentStorageFolderRepository) {
        return new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, dmsProperties.getSupportedExtensions());
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateFileInPort createFileInPort(final CreateFileOutPort createFileOutPort) {
        return new CreateFileUseCase(createFileOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateProcedureInPort createProcedureInPort(final CreateProcedureOutPort createProcedureOutPort) {
        return new CreateProcedureUseCase(createProcedureOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateDocumentInPort createDocumentInPort(final CreateDocumentOutPort createDocumentOutPort, LoadFileOutPort loadFileOutPort) {
        return new CreateDocumentUseCase(createDocumentOutPort, loadFileOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public UpdateDocumentInPort updateDocumentInPort(final UpdateDocumentOutPort updateDocumentOutPort, LoadFileOutPort loadFileOutPort) {
        return new UpdateDocumentUseCase(updateDocumentOutPort, loadFileOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public DepositObjectInPort depositObjectInPort(DepositObjectOutPort depositObjectOutPort) {
        return new DepositObjectUseCase(depositObjectOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CancelObjectInPort cancelObjectInPort(CancelObjectOutPort cancelObjectOutPort) {
        return new CancelObjectUseCase(cancelObjectOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadContentInPort readContentInPort(ReadContentOutPort readContentOutPort, TransferContentOutPort transferContentOutPort) {
        return new ReadContentUseCase(transferContentOutPort, readContentOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public SearchFileInPort searchFileInPort(SearchFileOutPort searchFileOutPort) {
        return new SearchFileUseCase(searchFileOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public SearchSubjectAreaInPort searchSubjectAreaInPort(SearchSubjectAreaOutPort searchSubjectAreaOutPort) {
        return new SearchSubjectAreaUseCase(searchSubjectAreaOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadMetadataInPort readMetadataInPort(ReadMetadataOutPort readMetadataOutPort, DmsUserOutPort dmsUserOutPort) {
        return new ReadMetadataUseCase(readMetadataOutPort, dmsUserOutPort);
    }

    @Bean
    public Consumer<Message<CreateFileDto>> createFile(final MessageProcessor messageProcessor) {
        return messageProcessor.createFile();
    }

    @Bean
    public Consumer<Message<CreateProcedureDto>> createProcedure(final MessageProcessor messageProcessor) {
        return messageProcessor.createProcedure();
    }

    @Bean
    public Consumer<Message<CreateDocumentDto>> createDocument(final MessageProcessor messageProcessor) {
        return messageProcessor.createDocument();
    }

    @Bean
    public Consumer<Message<UpdateDocumentDto>> updateDocument(final MessageProcessor messageProcessor) {
        return messageProcessor.updateDocument();
    }

    @Bean
    public Consumer<Message<DepositObjectDto>> depositObject(final MessageProcessor messageProcessor) {
        return messageProcessor.depositObject();
    }

    @Bean
    public Consumer<Message<CancelObjectDto>> cancelObject(final MessageProcessor messageProcessor) {
        return messageProcessor.cancelObject();
    }

    @Bean
    public Consumer<Message<ReadContentDto>> readContent(final MessageProcessor messageProcessor) {
        return messageProcessor.readContent();
    }

    @Bean
    public Consumer<Message<SearchObjectDto>> searchFile(final MessageProcessor messageProcessor) {
        return messageProcessor.searchFile();
    }

    @Bean
    public Consumer<Message<SearchObjectDto>> searchSubjectArea(final MessageProcessor messageProcessor) {
        return messageProcessor.searchSubjectArea();
    }

    @Profile("!local")
    @Bean
    @ConditionalOnMissingBean
    public DmsUserAdapter dmsUserAdapter(final UserAuthenticationProvider userAuthenticationProvider) {
        return new DmsUserAdapter(userAuthenticationProvider);
    }

    @Profile("local")
    @Bean
    @ConditionalOnMissingBean
    public MockDmsUserAdapter mockDmsUserAdapter() {
        return new MockDmsUserAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageProcessor createMessageProcessor(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final CreateFileInPort createFileInPort,
            final CreateProcedureInPort createProcedureInPort,
            final CreateDocumentInPort createDocumentInPort,
            final UpdateDocumentInPort updateDocumentInPort,
            final DepositObjectInPort depositObjectInPort,
            final CancelObjectInPort cancelObjectInPort,
            final ReadContentInPort readContentInPort,
            final SearchFileInPort searchFileInPort,
            final SearchSubjectAreaInPort searchSubjectAreaInPort
    ) {
        return new MessageProcessor(
                processApi,
                errorApi,
                createFileInPort,
                createProcedureInPort,
                createDocumentInPort,
                updateDocumentInPort,
                depositObjectInPort,
                cancelObjectInPort,
                readContentInPort,
                searchFileInPort,
                searchSubjectAreaInPort);
    }

}
