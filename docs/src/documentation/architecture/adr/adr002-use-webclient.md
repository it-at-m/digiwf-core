# ADR-002 Use WebClient

## Status

accepted

## Context

Für die Maschine-zu-Maschine-Kommunikation wird ein WebClient benötigt, der die HTTP-Zugriffe kapselt. Die Verwendung
verschiedener WebClients erschwert die Konfiguration und Übertragbarkeit der Software und sollte daher vermieden werden.
Spring Boot bringt einen WebClient (reaktiv oder auch nicht) mit, es existieren aber auch weitere WebClients (okhttp,
apache, feign).

## Decision

Anstatt eine konkrete Implementierung eines WebClients zu verwenden, verwenden wir das deklarative
WebClient-Framework [**OpenFeign**](https://github.com/OpenFeign/feign). Bei der Verwendung innerhalb von Spring Boot nutzen wir den
entsprechenden [SpringCloud Feign Starter](https://cloud.spring.io/spring-cloud-openfeign/reference/html/#netflix-feign-starter).
Wenn eine OpenAPI-Spezifikation einer Schnittstelle vorliegt, sollte der `feign` Client mithilfe von `openapi-generator`
generiert werden.

## Consequences

Durch die Verwendung einer weiteren Abstraktion müssen die Einstellungen nicht in der Low-Level-Implementierung des
WebClients, sondern auf der Feign-Ebene vorgenommen werden.
