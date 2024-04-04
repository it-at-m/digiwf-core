# DigiWF Spring Security

tbd.

## Feign-Clients mit Spring Security

Um einen Feign-Client mit Spring Security verwenden zu können, müssen neben den Feign-Client Dependencies auch die
DigiWF Spring Security Dependencies eingebunden werden. Die `digiwf-spring-security-starter` Dependency enthält die
Konfigurationsklasse `DigiwfFeignOauthClientConfig`, die den Feign-Clients den OAuth2-Token zuweist.

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

Der Feign-Client kann dann wie folgt konfiguriert werden. Für die Implementierung des Feign-Clients verwenden
wir `spring-cloud-starter-openfeign`. Diese Bibliothek ermöglicht es, Feign-Clients ähnlich wie Spring MVC Controller zu
implementieren. Damit diese Bibliothek den Feign-Client als Bean bereitstellt, muss die Annotation `@EnableFeignClients`
in der Spring Boot Application Klasse verwendet werden.

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

Zuletzt muss die `application.yml` um die Security-Konfiguration von `digiwf-spring-security-starter` erweitert werden:

```yaml
digiwf:
  security:
    # client-id and client-secret are required for feign client authentication
    client-id: ${SSO_TASK_CLIENT_ID}
    client-secret: ${SSO_TASK_CLIENT_SECRET}
```