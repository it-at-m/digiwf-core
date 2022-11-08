# Quickstart

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
