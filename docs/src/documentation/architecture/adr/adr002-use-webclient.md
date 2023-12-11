# ADR-002 Use WebClient

## Status

accepted

## Context

Für eine Machine-zu-maschine Kommunikation wird ein WebClient benötigt, der die HTTP Zugriffe kapselt. Die Verwendung 
von verschieden WebClients erschwert die Konfigurierbarkeit und Übertragbarkeit der Software und sollte daher vermieden 
werden. SpringBoot bringt einen WebClient (reactive oder auch nicht) mit, es existieren aber auch noch weitere Web Clients
(okhttp, apache, feign).   

## Decision

Statt eine konkrete Implementierung eines WebClients zu verwenden, verwenden wir ein deklaratives WebClient 
Framework [**OpenFeign**](https://github.com/OpenFeign/feign). Bei der Verwendung innerhalb von SpringBoot 
nutzen wir den entsprechenden [SpringCloud Feign Starter](https://cloud.spring.io/spring-cloud-openfeign/reference/html/#netflix-feign-starter).
Liegt eine Open API Spezifikation einer Schnittstelle vor, sollte der `feign` Client mittels `openapi-generator` 
generiert werden.

## Consequences

Durch die Verwendung einer weiteren Abstraktion müssen die Einstellungen nicht in der Low-Level Implementierung des 
WebClients, sondern auf der Feign Ebene vorgenommen werden. 

