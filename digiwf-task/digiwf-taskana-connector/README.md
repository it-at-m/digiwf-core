# Taskana Camunda Kafka Starter

## Add kafka dependency

Add the following dependency to the project where is planed to use this starter.

```
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>${version.spring.kafka}</version>
</dependency>
```

Add to the properties the version information.

```
<version.spring.kafka>2.8.7</version.spring.kafka>
```


## Add configuration to the application.yml file

### Kafka-Server

```
spring:
  kafka:
    producer:
      bootstrap-servers: <server-name:server-port>
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

### Topic-Names

```
digiwf.incomingTopic: <in-topic-name>
digiwf.outgoingTopic: <out-topic-name>
```
