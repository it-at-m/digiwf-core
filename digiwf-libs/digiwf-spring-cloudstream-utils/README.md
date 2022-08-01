<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">DigiWF Spring Cloudstream Utils</h3>

  <p align="center">
    This is a Spring Boot Starter library to send and receive messages and errors to / from a Spring Cloudstream compatible
messaging service.
    <a href="https://github.com/it-at-m/digiwf-spring-cloudstream-utils/issues">Report Bug</a>
    ·
    <a href="https://github.com/it-at-m/digiwf-spring-cloudstream-utils/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li>
      <a href="#usage">Usage</a>
      <ul>
        <li><a href="#minimum-required-spring-boot-annotations">Minimum required spring boot annotations</a></li>
      </ul>
      <ul>
        <li><a href="#sending-messages">Sending messages</a></li>
      </ul>
      <ul>
        <li><a href="#function-routing">Function routing</a></li>
      </ul>
</li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

The goal of this library is the streamlining of sending and receiving messages to / from a spring cloudstream eventbus.

Features:

* Choose your own binder in the application context
* Easily send messages and errors to the eventbus
* Receive messages from the eventbus and use the built-in function router to route them within your application,
  depending on the type of the message
* Easily start processes in digiwf

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

This project is built with:

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting started

_Below is an example of how you can install and set up your service_

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-spring-cloudstream-utils-starter dependency

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-spring-cloudstream-utils-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-spring-cloudstream-utils-starter', version: '${digiwf.version}'
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

4. Configure your binder.

__application.properties__

```
spring.cloud.stream.kafka.binder.producerProperties.value.serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.cloud.stream.kafka.binder.producerProperties.key.serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.cloud.stream.kafka.binder.consumerProperties.key.deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.cloud.stream.kafka.binder.consumerProperties.value.deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.cloud.stream.kafka.binder.consumerProperties.auto.offset.reset=latest
spring.cloud.stream.kafka.binder.configuration.security.protocol=SSL
spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;sendBpmnError;sendIncident;

spring.cloud.stream.kafka.binder.brokers=<brokerUrl>

spring.kafka.ssl.key-store-location=
spring.kafka.ssl.trust-store-location=
spring.kafka.ssl.key-store-password=
spring.kafka.ssl.trust-store-password=
spring.kafka.ssl.key-password=
```

5. Configure function routing

See <a href="#function-routing">Function Routing</a>

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->

## Usage

The library has several functionalities that can be configured. We have provided examples that show how you can use
them.

_For an example, please refer to
the [example](https://github.com/it-at-m/digiwf-spring-cloudstream-utils/tree/dev/example)
folder_

### Minimum required spring boot annotations

Listed below is the required Spring boot annotation.

* ```@SpringBootApplication```

### Sending messages

To send messages you can use the PayloadSender. This will send all messages to the topic specified in your
application.properties.

application.properties

```
spring.cloud.stream.bindings.sendMessage-out-0.destination=<topic>
```

### Sending bpmn errors and incidents

To send errors you have to specify the topics in your application.properties.

application.properties

```
  cloud.stream.bindings.sendBpmnError-out-0.destination: <topic>
  cloud.stream.bindings.sendIncident-out-0.destination: <topic>
```

<!-- FUNCTION ROUTING -->

### starting processes

To start processes you have to specify the topic in your application.properties.

application.properties

```
  cloud.stream.bindings.sendStartProcess-out-0.destination: <topic>
```

<!-- FUNCTION ROUTING -->

### Function routing configuration

To use the function router, you first have to specify the topic you want to listen in and your groupId. We have exactly
one topic for each sink. That's the configuration in your `application.yml`:

``` yml
spring.cloud.stream.bindings.functionRouter-in-0.group=<groupId>
spring.cloud.stream.bindings.functionRouter-in-0.destination=<topic>
```

For each operation we have a specific identifier called "type". This "type" (you can define it yourself) has to be set
in the MessageHeader property called `type`. Then you will need to link the MessageHeader `type` of incoming messages to
methods within your application in your `application.yml` file.

``` yml
io.muenchendigital.digiwf.streaming.typeMappings.<processMessageType_1>=<processMessageMethod_1>
io.muenchendigital.digiwf.streaming.typeMappings.<processMessageType_2>=<processMessageMethod_2>
io.muenchendigital.digiwf.streaming.typeMappings.<processMessageType_3>=<processMessageMethod_3>
...
```

The built-in function router now tries to correlate and route incoming messages with the Type-Header set to
"processMessageType" to the "processMessageMethod" method in your application.

#### Static function routing

If you don't want to make your message to function routing configurable, you can overwrite the routing in Spring. Use
for this a configuration class. If you write a spring starter, you should do this in a `AutoConfiguration.class`.

``` java
@Configuration
@AutoConfigureBefore({StreamingConfiguration.class})
public class MyConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback myRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(MESSAGE_TYPE_HEADER, MESSAGE_METHOD);
        return new RoutingCallback(typeMappings);
    }
}
```