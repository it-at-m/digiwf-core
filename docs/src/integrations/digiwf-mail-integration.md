# Digiwf Mail Integration

## Architecture

![Mail Architecture](~@source/images/platform/integrations/mail/architecture.png)

## Documentation

To send an e-mail through the eventbus, simply create
a [Mail](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-email-integration/digiwf-email-integration-core/src/main/java/io/muenchendigital/digiwf/email/integration/domain/model/Mail.java)
object, set the TYPE-Header
to [MessageProcessor.TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-email-integration/digiwf-email-integration-core/src/main/java/io/muenchendigital/digiwf/email/integration/api/streaming/MessageProcessor.java)
and send it to the corresponding kafka topic. That's it!

## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-email-integration-starter dependency.

With Maven:

```xml
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-email-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```gradle
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-email-integration-starter', version: '${digiwf.version}'
```

3. Add your preferred binder (see [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)). In this
   example, we use kafka.

Maven:

 ```xml
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-stream-binder-kafka</artifactId>
     </dependency>
```

Gradle:

```gradle
implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka'
```

4. Configure your binder.<br>
   For an example on how to configure your binder,
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-mail-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.

5. Configure your application

```yml
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
   the [example project](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-email-integration/digiwf-email-integration-example/).

## Usage

The digiwf-email-integration is a generic integration artifact to send email from processes.

To send an email through the event bus you have to send a *Mail* event with the TYPE-Header `sendMailFromEventBus`
to the event bus topic specified in `io.muenchendigital.digiwf.email.topic`.

````json
{
  "receivers": "receivers@example.com",
  "receiversCc": "receivers-on-cc@example.com",
  "receiversBcc": "receivers-on-bcc@example.com",
  "subject": "My important email",
  "body": "Some text I want to send",
  "replyTo": "replyto@example.com",
  "attachments": [
    {
      "url": "http://localhost:9000/s3-bucket/some/path/to/file/image.png",
      "path": "path/to/file/in/s3",
      "action": "GET"
    }
  ]
}
````

### Send Mail with file attachments

You can attach files from a s3 storage to the emails you are sending.
Therefore, you have to obtain presigned urls from the s3 integration artifact and pass them in the attachment section
of the *Mail* event to the digiwf-email-integration.
The email integration will download the files and attach them to the email before sending it.

**Note**: The digiwf-email-integration only supports presigned urls created with the **GET** action.
All other file action will not work and result in an error.

### Element Template

To speed up process development you can use the element template [sendMail.json](/element-template/sendMail.json) to define a call
activity that uses this integration.
