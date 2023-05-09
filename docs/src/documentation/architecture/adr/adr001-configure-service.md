# ADR-001 Configure Service

## Status

accepted

## Context

Die Konfiguration von Spring Boot Systemen erfolgt über verschiedene Mechanismen. So wird die Konfiguration über 
Properties-Dateien (in Properties oder YAML-Form) und die Verwendungen von Umgebungsvariablen unterstützt. Die 
Anwendungen werden einmalig gebaut und dann in den einzelnen Stages unterschiedlich konfiguriert und an den Betrieb
in der jeweiligen Stage-Umgebung angepasst. Die **Konfigurierbarkeit** der Anwendung muss dabei so sichergestellt werden,
damit es für die Entwickler und Betrieb einfach ist, die Konfiguration zu erstellen und anzupassen. Die Verwendung
von unterschiedlichen Methoden erschweren Konfigurierbarkeit des Systems, führt zu Fehlern in der Konfiguration und 
zu erhöhtem Aufwand bei Entwicklung und Betrieb. 

Die Standardisierung der Konfiguration muss auf verschiedenen Ebenen passieren:

* Format
* Standardkonfiguration (unveränderliche)
* Staging der umgebungsspezifischen Einstellungen 
* Einstellung der Konfiguration für die lokale Entwicklung
* Nutzung von Spring-Profilen

## Decision

Für die Konfiguration von Spring-Boot Anwendungen verwenden wir eine Konfiguration, die in `application.yml` abgelegt wird
und zusammen mit dem Quelltext in einen Spring-Boot JAR Archiv eingepackt wird. Dabei beinhaltet die Datei eine
Standardkonfiguration der Anwendung. Wenn die Einstellung eines Wertes eines Konfigurationsparameters umgebungsspezifisch 
ist, wird dazu eine Umgebungsvariable eingeführt und in der Konfigurationsdatei referenziert. Z.B.:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${SSO_ISSUER_URL}
```

Für den Betrieb der Anwendung ist nun die Umgebungsvariable `SSO_ISSUER_URL` notwendig. In den Stages wird diese
mithilfe von OCP Umgebungsvariablen gesetzt. Bei der lokalen Umgebung wird eine Env-Datei verwendet, die sowohl im `docker-compose`
Setup als auch beim Start der Anwendung aus der IDE verwendet werden kann. Ist der Start der Anwendung via Docker 
notwendig, können die Variablen auch über die Docker-Umgebung (env) übergeben werden.

Von der Nutzung von Spring-Profilen wird grundsätzlich abgeraten, weil dadurch das Verhalten des Systems nachträglich verändert wird.
Eine Ausnahme bildet das `test`/`itest` Profil, der zum Testen des Systems aktiviert wird, um SpringBoot Tests ausserhalb der
Umgebung durchführen zu können. Das System sollte nach Möglichkeit sowohl lokal als auch in den Stages ohne Profile
(also in der Standard-Konfiguration mit angewandten umgebungsspezifischen Anpassung) betrieben werden.

## Consequences

Statt direkter Änderung von Konfigurationsvariablen über Pfade (`SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER-URL=...`)
müssen die Werte über die entsprechende Umgebungsvariablen geändert werden. Durch diese Indirektion sind zwar zusätzliche 
Einstellungen notwendig, jedoch wird die Konfigurierbarkeit des Systems **explizit** und es entstehen weniger Fehler.