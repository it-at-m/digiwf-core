# DigiWF OK.EWO Integration
## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initializer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the `digiwf-okewo-integration-starter` dependency.

With Maven:

```
   <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-okewo-integration-starter</artifactId>
        <version>${version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-okewo-integration-starter', version: '${version}'
```

4. Configure your application

```yaml
de.muenchen.oss.digiwf.okewo:
  url: <URL>
  username: <USERNAME>
  password: <PASSWORD>
  benutzerId: <BENUTZER_ID>
```

5. Use the repository beans

To request the OK.EWO rest api, the methods of the following listed spring beans can be used.

* `OkEwoPersonRepository`
* `OkEwoPersonErweitertRepository`