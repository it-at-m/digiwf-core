# Digiwf S3 Integration

## Getting started

_Below is an example of how you can install and setup your service_

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the `digiwf-s3-integration-starter` dependency

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-s3-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-s3-integration-starter', version: '${digiwf.version}'
```

3. Configure your S3 bucket with the following properties:
    - `io.muenchendigital.digiwf.s3.bucketName`
    - `io.muenchendigital.digiwf.s3.secretKey`
    - `io.muenchendigital.digiwf.s3.accessKey`
    - `io.muenchendigital.digiwf.s3.url`
    - `io.muenchendigital.digiwf.s3.initialConnectionTest`

`io.muenchendigital.digiwf.s3.initialConnectionTest` is an optional property which allows to enable or disable an
initial connection test to the s3 bucket during boot up.
If the property is `true` or not set, the connection test is performed.
If the property is explicitly set to `false`, no connection test is carried out.

If you want to use the cron job cleanup, take a look at the <a href="#cron-job-cleanup">usage exmaple</a> .

5. OpenAPI specification:

    - Enjoy the [OpenAPI definition](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)
    - Get the [Api-Docs](http://localhost:8080/v3/api-docs)

<p align="right">(<a href="#top">back to top</a>)</p>

**For more information and code examples see [documentation.md](documentation.md)**



## S3 Integration Library

### Usage

The library has several functionalities that can be configured. We have provided examples that show how you can use
them.

_For more examples, please refer to
the [example-s3-integration](https://github.com/it-at-m/digiwf-s3-integration/tree/dev/example-s3-integration)
and/or [example-s3-integration-client](https://github.com/it-at-m/digiwf-s3-integration/tree/dev/example-s3-integration-client)
folder._

### Minimum necessary spring boot annotations

Listed below are the required Spring boot annotations, which are minimal.

* `@SpringBootApplication`
* `@EnableJpaAuditing`
* `@EnableScheduling`

### Cron Job Cleanup

Files need to be deleted after some time. We have developed a file structure to which an end of life timestamp can be
saved.
The cron job setting determines how often and when the files are checked and deleted. To use this functionality
configure the property:

``io.muenchendigital.digiwf.s3.cronjob.cleanup.expired-files=0 15 10 15 * ?``

This job cleans the metadata of the S3 files in the database if no corresponding file within the S3 storage exists.

``io.muenchendigital.digiwf.s3.cronjob.cleanup.unused-files=0 15 10 16 * ?``

## Getting the integration client library

_Below is an example of how you can installing and setup up your service_

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the `digiwf-s3-integration-client-starter` dependency

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-s3-integration-client-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-s3-integration-client-starter', version: '${digiwf.version}'
```

3. Configure your service which uses this starter with the following properties:
    - `io.muenchendigital.digiwf.s3.client.document-storage-url`

### Usage

The client library provides several beans that can be used to interact with the `digiwf-s3-integration-starter`.
We have provided examples that show how you can use them.

Each method within the client library, which communicates directly with a `digiwf-s3-integration-service`, is available
in two different flavors.
One method that uses the document storage url defined in `io.muenchendigital.digiwf.s3.client.document-storage-url`.
Another method that expects the document storage url within the method parameter.
This allows to use different `digiwf-s3-integration-service` with the same client lib.

_For more examples, please refer to
the [example-s3-integration](https://github.com/it-at-m/digiwf-s3-integration/tree/dev/example-s3-integration)
and/or [example-s3-integration-client](https://github.com/it-at-m/digiwf-s3-integration/tree/dev/example-s3-integration-client)
folder._

The images used in this example are not subject to any license.

### Minimum necessary spring boot annotations

Listed below are the required Spring boot annotations, which are minimal.

* ```@SpringBootApplication```

## Get presigned urls asynchronously

Besides, the REST api the **S3 Integration Library** provides an asynchrones api.
The asynchrones api creates presigned urls for you which may be used to interact with files.
Asynchronously created presigned urls are usually valid for 7 days.
You can customize the presigned url expiration time with the
property `io.muenchendigital.digiwf.s3.presignedUrlExpiresInMinutes` (Note: the max value is 7 days = 10080 min).

To get a presigned urls for a file or directory you have to send a `CreatePresignedUrlEvent` to the according kafka
topic:

```json
{
  "action": "GET",
  "path": "/path/to/your/file/or/directory"
}
```

Valid actions are `GET`, `POST`, `PUT` and `DELETE`.

In return, you get a correlate message event that contains a list of presigned urls in its payload variables in the
following format.
These presigned url objects may be passed to other integration artifacts to download and/or store files in the s3
storage.

```json
{
  "presignedUrls": [
    {
      "url": "the-presigned-url",
      "path": "/path/to/your/file/or/directory",
      "action": "GET"
    }
  ]
}
```

If you request a file directly you just get the presigned url for the file.
If you request presigned urls for a directory presigned urls for all files inside the directory are created.

In the `path` you can specify multiple file paths by concatenating them with a semicolon `;` (e.g. `folder/first.txt;folder/second.txt;folder/third.txt`).

_For more examples, please refer to
the [example-s3-integration](https://github.com/it-at-m/digiwf-s3-integration/tree/dev/example-s3-integration)
and/or [example-s3-integration-client](https://github.com/it-at-m/digiwf-s3-integration/tree/dev/example-s3-integration-client)
folder._

## S3 proxy

If you cannot access the s3 storage directly, you may want to send requests to the s3 storage over a proxy.
To configure such a proxy you can enable the proxy settings and define a `proxyUrl`.
The proxy url is used to redirect requests from presigned urls to a proxy that forwards to the s3 storage.

````properties
io.muenchendigital.digiwf.s3.proxyEnabled=true
io.muenchendigital.digiwf.s3.proxyUrl=http://localhost:9000
````

The proxy is disabled by default.

Checkout [https://digiwf.muenchendigital.io/resources/documentation/concept/filehandling/](https://digiwf.muenchendigital.io/resources/documentation/concept/filehandling/)
for more information about the topic file handling in digiwf.

