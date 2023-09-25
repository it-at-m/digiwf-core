# Digiwf ALW Integration

The goal of this library is to enable async communication with the ALW System dispatched by an EventBus of your
environment.

Features:

* Can be used to dispatch requests/responses of the ALW Personeninfo Feature asynchronously through an eventbus.
* Can inform the receiver through an eventbus if the request was successful or if there was a problem.
* Performs a functional ping to the ALW System to check connectivity.

### Built With

The documentation project is built with technologies we use in our projects:

* Spring-Boot
* Spring-Cloud-Stream
* Apache Kafka

## Set up

Follow these steps to use the starter in your application:

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-alw-integration-starter dependency.

With Maven:

``` xml
   <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-alw-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

``` groovy
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-alw-integration-starter', version: '${digiwf.version}'
```

3. Add your preferred binder (see [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)). In this
   example, we use kafka.

Maven:

 ``` xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

Gradle:

``` groovy
implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka'
```

4. Configure your binder.<br>
   For an example on how to configure your binder,
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-alw-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.

5. Configure these items for your event bus:

``` properties
spring.cloud.stream.bindings.sendMessage-out-0.destination: <YOUR CUSTOM REQUEST TOPIC>
spring.cloud.stream.bindings.sendCorrelateMessage-out-0.destination: <YOUR CUSTOM RESPONSE TOPIC>
spring.cloud.stream.bindings.functionRouter-in-0.group: <YOUR GROUP>
spring.cloud.stream.bindings.functionRouter-in-0.destination: <YOUR CUSTOM REQUEST TOPIC> # For a roundtrip use the same value as in "spring.cloud.stream.bindings.sendMessage-out-0.destination" 
```

6. Configure details of your ALW System:

``` yaml
digiwf.alw.personeninfo:
  base-url: <YOUR ALW SYSTEM URL>
  rest-endpoint: <YOUR PERSONENINFO ENDPOINT>
  timeout: <YOUR CONNECTION TIMEOUT>
  username: <YOUR BASIC AUTH USER>
  password: <YOUR BASIC AUTH PASSWORD>
  functional-ping:
    enabled: true
    azr-number: <YOUR SAMPLE AZR NUMBER>
```

7. Define a map as a named resource bean (see **BEAN_ALW_SACHBEARBEITUNG**
   of <i>[SachbearbeitungMapperConfig](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-alw-integration/digiwf-alw-integration-core/src/main/java/io/muenchendigital/digiwf/alw/integration/configuration/SachbearbeitungMapperConfig.java) </i> )
   to support mapping of the ALW System responses to directory-ous.

For an example, please refer to
the [example project](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-alw-integration/digiwf-alw-integration-example).
There you can:

* Configure the example application (see above)
* Start the example application
* Make a http request to the configured test endpoints
  from <i>[ExampleController](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-alw-integration/digiwf-alw-integration-example/src/main/java/io/muenchendigital/digiwf/alw/integration/api/controller/ExampleController.java) </i>
  on http://localhost:10006/testGetAlwZustaendigkeitEventBus
* Observe the output in the console

