# Digiwf S3 Integration

The following steps are needed to run the integration (service and client example) locally.

## Getting Started

1. Build it with `mvn clean install`
2. Run Stack using `docker-compose`
3. Open Minio (http://localhost:9000, user: `minio`, pass: `Test1234`) and create a Bucket.
4. Store the values in `S3_BUCKETNAME`, `S3_ACCESSKEY` und `S3_SECRETKEY`of `stack/local-docker.env`
5. Start `S3IntegrationApplication` (of digiwf-s3-integration-service)
   - Activate Spring Profile `local`
   - Add Environment values from `stack/local-docker.env`

## Testing functionality

1. Start `S3IntegrationClientApplication`
   - Activate Spring profile `streaming`
   - Add Environment values from `stack/local-docker.env`
2. Execute http test located in `/digiwf-integrations/digiwf-s3-integration/digiwf-s3-integration-client-example/src/main/resources/s3-client.http`
3. Inspect the console of the client 

## Additional configuration

If there is no requirement for the use of a message broker and the s3 integration is addressed exclusively via REST, the following settings can be used to deactivate the spring-cloud-stream-feature in a service which uses the `digiwf-s3-integration-starter`.

A hardcoded solution to disable the default behaviour with exclusion of specific auto-configuration classes on start up can be found down below.

```java
...
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.function.FunctionConfiguration;
...

/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication(
        ...
        // Deactivating the Spring Cloud Stream functionality that is activated by default
        exclude = { BindingServiceConfiguration.class, FunctionConfiguration.class },
        ...
)
```

An alternative to the hard coded solution is to exclude the classes via the following spring property.

```yml
spring:
  autoconfigure:
    exclude:
      # Deactivating the Spring Cloud Stream functionality that is activated by default
      - org.springframework.cloud.stream.config.BindingServiceConfiguration
      - org.springframework.cloud.stream.function.FunctionConfiguration
```

