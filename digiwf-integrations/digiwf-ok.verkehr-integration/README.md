
# DigiWF OK.Verkehr Integration

## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initializer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the `digiwf-okverkehr-integration-starter` dependency.

With Maven:

```
   <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-okverkehr-integration-starter</artifactId>
        <version>${version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-okverkehr-integration-starter', version: '${version}'
```

4. Configure your application

```yaml
de.muenchen.oss.digiwf.okverkehr:
  url: <URL>
  username: <USERNAME>
  password: <PASSWORD>
```

5. Use the repository and/or service beans

To request the OK.VERKEHR rest api, the methods of the following listed spring beans can be used.

* `OkVerkehrHalterRepository`
* `OkVerkehrHalterService`
