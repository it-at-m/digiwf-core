# DigiWF ALW Integration

![](https://img.shields.io/badge/Integration_Name-alwIntegration-informational?style=flat&logoColor=white&color=2c73d2)

The goal of this library is to enable asynchronous communication with the ALW System dispatched by an EventBus of your
environment.

Features:

* Can be used to dispatch requests/responses of the ALW Personeninfo Feature asynchronously through an eventbus.
* Can inform the receiver through an eventbus if the request was successful or if there was a problem.
* Performs a functional ping to the ALW System to check connectivity.

### Built With

The documentation project is built with technologies we use in our projects:

* Spring Boot
* Spring Cloud Stream
* Apache Kafka

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN-Errors und Incident-Errors unterschieden.
BPMN-Errors können im Prozess behandelt werden, während Incident-Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen.

Nachfolgend sind die BPMN-Errors aufgeführt, die von der ALW-Integration geworfen werden können:

#### BPMN Error

| Error Code                 | Error Message                                                                                                                                                     | Beschreibung                                                                                                                                                                                     | Handlungsempfehlung                                                                                                                                 | 
|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `RESPONSIBILITY_NOT_FOUND` | Responsible `<found respnsible>` for azr `<given AZR-Number>` does not match any known responsibility! / Could not find ALW responsible for `<given AZR-Number>`. | Die verantwortliche Organisationseinheit kann nicht gefunden werden, entweder weil sie von ALW nicht zurückgegeben wurde oder die zurückgegebene nicht einer bekannten Verantwortung entspricht. | Der Vorgang wird ohne verantwortliche Organisationseinheit weiter bearbeitet oder abgebrochen.                                                      |
| `VALIDATION_ERROR_CODE`    | AZR-Number is invalid; it must contain 12 digits.The AZR-Number is required and cannot be null or empty./                                                         | Die übergebene AZR-Nummer (Nummer Ausländerzentralregister) ist nicht valide. Sie muss aus genau 12 Ziffern bestehen.                                                                            | Stellen Sie sicher, dass eine gültige AZR-Nummer übergeben wird.                                                                                    | 
| `UNEXPECTED_ERROR`         | `{ALW-ERROR-CODE-MESSAGE}`                                                                                                                                        | Bei ALW ist ein unerwarteter Fehler aufgetreten, außer `404 Not found`.                                                                                                                          | Es ist ein Fehler aufgetreten und ALW konnte die Anfrage nicht verarbeiten. Eine Überprüfung der Anfrage sowie des Status von ALW ist erforderlich. | 

## Set up

Follow these steps to use the starter in your application:

1. Use the Spring Initializr and create a Spring Boot application with `Spring Web`
   dependencies. [https://start.spring.io](https://start.spring.io)
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
   example, we use Kafka.

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
   topics you want to read/send messages from/to.

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