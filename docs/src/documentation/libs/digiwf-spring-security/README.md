# DigiWF Spring Security

tbd.

## Feign Clients mit Spring Security

Um einen Feign Client mit Spring Security verwenden zu können, muss neben den Feign Client Dependencies auch die DigiWF Spring Security Dependency eingebunden werden.
Die `digiwf-spring-security-starter` Dependency enthält die Configuration Klasse `DigiwfFeignOauthClientConfig`, welche die Feign Clients mit dem OAuth2 Token ausstattet.

```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>io.github.openfeign</groupId>
        <artifactId>feign-httpclient</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-openfeign-core</artifactId>
    </dependency>

    <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-spring-security-starter</artifactId>
        <version>${project.version}</version>
    </dependency>
```

Dann kann der Feign Client wie folgt konfiguriert werden. Für die Implementierung des Feign Clients verwenden wir den `spring-cloud-starter-openfeign`.
Dieser ermöglicht es Feign Clients in einer ähnlichen Art und Weise wie Spring MVC Controller zu implementieren.
Damit diese Bibliothek den Feign Client als Bean bereitstellt, muss die Annotation `@EnableFeignClients` in der Spring Boot Application Klasse verwendet werden.

```java
@FeignClient(
        name = "${feign.client.config.digiwf-process-config.name:digiwf-process-api}",
        url = "${feign.client.config.digiwf-process-config.url}",
        configuration = DigiwfFeignOauthClientConfig.class
)
public interface ProcessConfigClient {

    @RequestMapping(method = RequestMethod.GET, value = "/rest/processconfig/{key}", consumes = "application/json")
    ProcessConfigTO getProcessConfig(@PathVariable("key") final String engine);

}
```

Zu guter Letzt muss noch die `application.yml` um die Security Konfiguration des `digiwf-spring-security-starter` erweitert werden:

```yaml
digiwf:
  security:
    # client-id and client-secret are required for feign client authentication
    client-id: ${SSO_TASK_CLIENT_ID}
    client-secret: ${SSO_TASK_CLIENT_SECRET}
```
