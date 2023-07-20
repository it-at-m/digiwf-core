package io.muenchendigital.digiwf.s3.integration.example.client.controller;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import io.muenchendigital.digiwf.s3.integration.example.client.controller.dto.FileActionDto;
import io.muenchendigital.digiwf.s3.integration.example.client.controller.dto.PresignedUrlDto;
import io.muenchendigital.digiwf.s3.integration.example.client.streaming.events.CreatePresignedUrlEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Example controller to test the asyncapi endpoint that creates presigned urls
 *
 * @author ext.dl.moesle
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/presignedurl")
public class PresignedUrlController {

  private final MessageApi messageApi;
  private final S3FileTransferRepository s3FileTransferRepository;


  /**
   * Create a presigned url by sending a {@link CreatePresignedUrlEvent} to the event bus
   *
   * @param presignedUrlDto
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  public void createPresignedUrl(@RequestBody final PresignedUrlDto presignedUrlDto) {
    final CreatePresignedUrlEvent createPresignedUrlEvent = new CreatePresignedUrlEvent(presignedUrlDto.getAction(), presignedUrlDto.getPath());
    this.messageApi.sendMessage(createPresignedUrlEvent, "createPresignedUrl");
  }

  /**
   * Use a valid presigned url to perform change, get or create the file
   *
   * @param fileActionDto
   * @throws IOException
   * @throws DocumentStorageException
   * @throws DocumentStorageClientErrorException
   * @throws DocumentStorageServerErrorException
   */
  @PostMapping("/use")
  @ResponseStatus(HttpStatus.OK)
  public void usePresignedUrls(@RequestBody final FileActionDto fileActionDto) throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
    if (fileActionDto.getAction().equalsIgnoreCase("DELETE")) {
      this.s3FileTransferRepository.deleteFile(fileActionDto.getPresignedUrl());
      return;
    } else if (fileActionDto.getAction().equalsIgnoreCase("GET")) {
      this.s3FileTransferRepository.getFile(fileActionDto.getPresignedUrl());
      return;
    }

    final File file = ResourceUtils.getFile("classpath:files/" + fileActionDto.getFile());
    final byte[] binaryFile = Files.readAllBytes(file.toPath());

    if (fileActionDto.getAction().equalsIgnoreCase("POST")) {
      this.s3FileTransferRepository.saveFile(fileActionDto.getPresignedUrl(), binaryFile);
    } else if (fileActionDto.getAction().equalsIgnoreCase("PUT")) {
      this.s3FileTransferRepository.updateFile(fileActionDto.getPresignedUrl(), binaryFile);
    }
  }

}
