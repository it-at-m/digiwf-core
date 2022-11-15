## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-email-integration-starter dependency.

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-email-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-email-integration-starter', version: '${digiwf.version}'
```

3. Add your preferred binder (see [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)). In this
   example, we use kafka.

Maven:

 ```
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-stream-binder-kafka</artifactId>
     </dependency>
```

Gradle:

```
implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka'
```

4. Configure your binder.<br>
   For an example on how to configure your binder,
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-mail-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.

5. Configure your application

```
spring:
  mail:
    host: mail.example.com
    port: 587
    username: mymail@example.de
    password: yourExcellentPassword
    properties:
      mail.debug: false
      mail.tls: true
      mail.transport.protocol: smtp
      mail.smtp.host: mail.example.com
      mail.smtp.port: 587
      mail.smtp.connectiontimeout: 10000
      mail.smtp.timeout: 10000
      mail.smtp.auth: true
      mail.smtp.ssl.trust: "*"
      mail.smtp.ssl.checkserveridentity: true
      mail.smtp.socketFactory.fallback: true
      mail.smtp.starttls.enable: true
```

You can also use digiwf.mail.fromAddress to define a mail address when not using smtp.auth.

6. Define a RestTemplate. For an example, please refer to
   the [example project](https://github.com/it-at-m/digiwf-email-integration/tree/dev/example-digiwf-email-integration).