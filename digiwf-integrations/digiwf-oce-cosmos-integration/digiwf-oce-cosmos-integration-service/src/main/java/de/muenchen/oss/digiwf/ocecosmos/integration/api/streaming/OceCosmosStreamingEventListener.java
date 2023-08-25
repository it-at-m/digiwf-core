package de.muenchen.oss.digiwf.ocecosmos.integration.api.streaming;

import com.google.common.jimfs.Jimfs;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.response.OceCosmosErrorDto;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.mapper.OceCosmosMapper;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request.FileJobRequestDto;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request.TemplateJobRequestDto;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.JobRequest;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.response.JobResponse;
import de.muenchen.oss.digiwf.ocecosmos.integration.service.JobService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OceCosmosStreamingEventListener {

    private static final String RESPONSE = "response";

    private final OceCosmosMapper oceCosmosMapper;

    private final CorrelateMessageService correlateMessageService;

    private final JobService jobService;


    /**
     * The Consumer expects an {@link TemplateJobRequestDto}.
     * <p>
     * After successfully requesting the printing job a JSON representing a {@link JobResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OceCosmosErrorDto}.
     */
    @Bean
    public Consumer<Message<TemplateJobRequestDto>> requestPrint() {
        return message -> {
            log.debug(message.toString());

            final var printRequest = message.getPayload();

            Object printJobResult;

            try(FileSystem fileSystem = Jimfs.newFileSystem()) {
                final JobRequest model = this.oceCosmosMapper.dto2Model(printRequest);
                model.setFile(convertByteArrayToFile(printRequest.getFile(), printRequest.getFileName(), fileSystem));

                printJobResult = this.jobService.submitJob(model);
            } catch (final Exception exception) {
                printJobResult = new OceCosmosErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, printJobResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link FileJobRequestDto}.
     * <p>
     * After successfully requesting the printing job a JSON representing a {@link JobResponse} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OceCosmosErrorDto}.
     */
    @Bean
    public Consumer<Message<FileJobRequestDto>> requestPDFPrint() {
        return message -> {
            log.debug(message.toString());

            final var printRequest = message.getPayload();

            Object printJobResult;

            if(printRequest.getS3Files() == null) {
                this.correlateMessageService.sendCorrelateMessage(
                        message.getHeaders(),
                        Map.of(RESPONSE, new OceCosmosErrorDto("Presigned S3 File-URLs needed"))
                );
                return;
            }

            if(printRequest.getS3Files().size() != 1) {
                this.correlateMessageService.sendCorrelateMessage(
                        message.getHeaders(),
                        Map.of(RESPONSE, new OceCosmosErrorDto("Only one file is supportet"))
                );
                return;
            }

            var s3File = printRequest.getS3Files().get(0);

            try(InputStream fileStream = new URL(s3File.getUrl()).openStream();
                FileSystem fileSystem = Jimfs.newFileSystem()) {

                // download file from s3
                final var file = fileStream.readAllBytes();
                final var fileName = StringUtils.substringAfterLast(s3File.getPath(), "/");

                final JobRequest model = this.oceCosmosMapper.dto2Model(printRequest);
                model.setFile(convertByteArrayToFile(file, fileName, fileSystem));

                printJobResult = this.jobService.submitJob(model);
            } catch (final IOException ex) {
                log.error("File could not be loaded: {}", ex.getMessage());
                printJobResult = new OceCosmosErrorDto(ex.getMessage());
            } catch (final Exception exception) {
                printJobResult = new OceCosmosErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, printJobResult)
            );

        };
    }

    private Path convertByteArrayToFile(byte[] file, String fileName, FileSystem fileSystem) throws IOException {
        //Convert Byte-Array from request to in-mem file
        Path tmpPath = fileSystem.getPath("tmp");
        Files.createDirectory(tmpPath);
        Path filePath = tmpPath.resolve(fileName);
        Files.write(filePath, file);
        return filePath;
    }


}
