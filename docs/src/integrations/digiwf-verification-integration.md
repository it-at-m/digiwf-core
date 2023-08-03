# Digiwf Verification Integration


I can request a verification link from within a business process.
This verification link has a certain validity, if "Expiry time" has been set. After the expiration of this period, the link can no longer be called.
This means that clicking on the link does not send a message to a waiting process instance and the user is informed that the validity of the link has already expired.
If the token passed by the link cannot be assigned (via DB lookup), then a generic error message is issued to the user.
The verification link is constructed in such a way that no user data (e-mail, correlation key, etc.) is given to the outside. A random and unique token is generated here.
If the user clicks on the link within the defined expiration period,
* the message name is sent to the (hopefully) waiting process with the given process instance id.
* feedback is given to the user whether the transaction was successful (the process was activated) or not.
* it cannot be clicked a second time with a positive result.

The following data is needed to get the verification link:

* Process instance id: the id uniquely identifying the process instance.
* Message name: The key that can be uniquely assigned to a message catch event in an active process instance.
* Expiry time (optional): If this parameter is not set, the link is valid "infinitely". If this parameter is set, it is validated on verification.
* Subject (optional): Additional information such as e-mail or similar. In a scenario with a long context (for example, a user account), this parameter could prevent a user from having to verify n times.

In the scenario described above, it does not take into account that the process instance is no longer present at the time of verification (although it still happens in the required time period). The user would then still receive positive feedback because the correlation key was sent to the - supposedly - waiting instance.

![Flowchart](~@source/images/platform/integrations/verification/flowchart.png)

## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-verification-integration-starter dependency.

With Maven:

```
   <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-verification-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-verification-integration-starter', version: '${digiwf.version}'
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
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-mail-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.

5. Configure your application

```
spring.datasource.url=jdbc:h2:mem:verification
spring.datasource.username=sa
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.jpa.database=H2
spring.jpa.hibernate.dll-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.naming.physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.flyway.enabled=false

io.muenchendigital.digiwf.verification.integration.baseAddress=http://localhost:${server.port}
```

## Architecture

The integration artifact is built as a Spring Boot starter. I.e. there must not be a central instance of the service, but potentially n services are operated in different domains.

It must be noted that the API must be accessible from the outside (internet) for verification.

How the Service should technically work:

![Data Flow](~@source/images/platform/integrations/verification/dataflow.png)

How the Service should be used in a domain-specific scenario:

![Architecture](~@source/images/platform/integrations/verification/architecture.png)

