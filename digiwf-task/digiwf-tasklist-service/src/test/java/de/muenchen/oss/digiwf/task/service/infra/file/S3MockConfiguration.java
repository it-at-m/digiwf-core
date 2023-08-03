package de.muenchen.oss.digiwf.task.service.infra.file;

import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3MockConfiguration {

    @Bean
    public PresignedUrlRepository presignedUrlRepository() {
        return Mockito.mock(PresignedUrlRepository.class);
    }

    @Bean
    public DocumentStorageFolderRepository documentStorageFolderRepository() {
        return Mockito.mock(DocumentStorageFolderRepository.class);
    }

}
