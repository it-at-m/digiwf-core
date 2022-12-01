# DigiWF Cosys Integration

The goal of the DigiWF Cosys Integration is to allow async document creation in cosys with an event broker and s3 storage.

Features:

* Can be used to create documents in cosys and save them in a s3 compatible storage
* Can inform the receiver through an event if the creation was successful or if there was a problem

Checkout the source code [here](../../../../digiwf-integrations/digiwf-cosys-integration).

## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-cosys-integration-starter dependency.

With Maven:

```xml
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-cosys-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```gradle
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-cosys-integration-starter', version: '${digiwf.version}'
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
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-cosys-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.

5. Configure S3

```gradle
io.muenchendigital.digiwf.s3.client.document-storage-url: http://s3-integration-url:port
```

See [this](https://github.com/it-at-m/digiwf-spring-cloudstream-utils) for an example.

6. Configure your application

```
io.muenchendigital.digiwf.cosys.url=localhost:800
io.muenchendigital.digiwf.cosys.merge.datafile=/root/multi
io.muenchendigital.digiwf.cosys.merge.inputLanguage=Deutsch
io.muenchendigital.digiwf.cosys.merge.outputLanguage=Deutsch
io.muenchendigital.digiwf.cosys.merge.keepFields=unresolved-ref
```

7. Define a RestTemplate. For an example, please refer to
   the [example project](https://github.com/it-at-m/digiwf-cosys-integration/tree/dev/example).

## Usage

To create a cosys document asynchronously, simply create a `GenerateDocument` object, set the TYPE-Header
to `createCosysDocument` and send it to the corresponding kafka topic.

```json
{
  "client": "",
  "role": "",
  "guid": "",
  "variables": {},
  "documentStorageUrls": [
    {
      "url": "",
      "path": "",
      "action": ""
    }
  ]
}
```

The cosys integration will generate a cosys document with the variables given and save it in a s3 storage.
Therefore, you have to pass a valid presigned url for document creation (action `POST` or `PUT`) within the `documentStorageUrls` variable.

The cosys integration only accepts a single presigned url in the `documentStorageUrls` variable with the action `POST` or `PUT`.
The action `POST` is used for the creation of new files in the s3 storage and the action `PUT` to override an already existing document.

**Usage in bpmn processes**

Create a *Callactivity*, use on of our Element-Templates and fill it with the required information:

* [Cosys all data](src/.vuepress/public/element-template/cosys-alle-daten.json)
* [Cosys create document](src/.vuepress/public/element-template/cosys-dokument-erstellen.json)
* [Cosys create document (V02)](src/.vuepress/public/element-template/cosys_generate_document_template_V02.json)

To create presigend urls with the digiwf-s3-integration you can also use a *Callactivity* and the Element-Tempalate [s3_create_presigned_url](src/.vuepress/public/element-template/s3_create_presigned_url_template.json) and pass the results to the cosys integration.
