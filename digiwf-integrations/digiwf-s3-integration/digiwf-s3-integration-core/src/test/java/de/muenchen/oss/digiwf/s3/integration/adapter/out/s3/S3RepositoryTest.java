package de.muenchen.oss.digiwf.s3.integration.adapter.out.s3;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import io.minio.messages.Contents;
import io.minio.messages.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link S3Repository} creating presigned urls correctly and using a proxy if enabled
 *
 * @author ext.dl.moesle
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class S3RepositoryTest {

  private S3Repository s3Repository;
  @Mock
  private MinioClient client;

  private final String s3Url = "http://localhost:9000";
  private final String s3ProxyUrl = "http://127.0.0.1:9000";
  private final String filePath = "test/image.png";
  private final List<Method> actions = List.of(Method.GET, Method.POST, Method.PUT, Method.DELETE);
  private final int expiresInMinutes = 5;

  @BeforeEach
  public void beforeEach() throws FileSystemAccessException {
    this.s3Repository = new S3Repository("test-bucket", this.s3Url, this.client, false, Optional.empty());
  }

  @Test
  public void testGetPresignedUrlWithoutProxy() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, FileSystemAccessException {
    when(this.client.getPresignedObjectUrl(Mockito.any(GetPresignedObjectUrlArgs.class))).thenReturn(this.s3Url + "/some-url/...");
    for (final Method action : this.actions) {
      final String presignedUrl = this.s3Repository.getPresignedUrl(this.filePath, action, this.expiresInMinutes);
      Assertions.assertTrue(presignedUrl.contains(this.s3Url));
      Assertions.assertFalse(presignedUrl.contains(this.s3ProxyUrl));
    }
  }

  @Test
  public void testGetPresignedUrlWithProxy() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, FileSystemAccessException {
    this.s3Repository = new S3Repository("test-bucket", this.s3Url, this.client, false, Optional.of(this.s3ProxyUrl));
    when(this.client.getPresignedObjectUrl(Mockito.any(GetPresignedObjectUrlArgs.class))).thenReturn(this.s3Url + "/some-url/...");
    for (final Method action : this.actions) {
      final String presignedUrl = this.s3Repository.getPresignedUrl(this.filePath, action, this.expiresInMinutes);
      Assertions.assertTrue(presignedUrl.contains(this.s3ProxyUrl));
      Assertions.assertFalse(presignedUrl.contains(this.s3Url));
    }
  }

  @Test
  public void testGetFileSizesFromFolder() throws Exception {
    String folder = "test-folder";
    Item item1 = mock(Item.class);
    when(item1.objectName()).thenReturn("test-folder/file1.txt");
    when(item1.size()).thenReturn(1024L);
    Item item2 = mock(Item.class);
    when(item2.objectName()).thenReturn("test-folder/file2.txt");
    when(item2.size()).thenReturn(2048L);
    Result<Item> result = new Result<>(item1);
    Result<Item> result2 = new Result<>(item2);

    when(client.listObjects(any(ListObjectsArgs.class))).thenReturn(List.of(result, result2));

    Map<String, Long> fileSizes = s3Repository.getFileSizesFromFolder(folder);

    assertEquals(2, fileSizes.size());
    assertEquals(1024L, fileSizes.get("test-folder/file1.txt"));
    assertEquals(2048L, fileSizes.get("test-folder/file2.txt"));
    verify(client, times(1)).listObjects(any(ListObjectsArgs.class));
  }

  @Test
  public void testGetFileSize() throws Exception {
    String pathToFile = "test-path";

    StatObjectResponse statObjectResponse = mock(StatObjectResponse.class);

    long expectedSize = 512L;
    when(client.statObject(any(StatObjectArgs.class))).thenReturn(statObjectResponse);
    when(statObjectResponse.size()).thenReturn(expectedSize);

    long size = s3Repository.getFileSize(pathToFile);

    assertEquals(expectedSize, size);
    verify(client, times(1)).statObject(any(StatObjectArgs.class));
  }

}
