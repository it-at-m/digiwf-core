# Technischer Setup

Im Folgenden wird das technische Setup beschrieben

## lokale Infrastruktur

Für die Entwicklung wird eine lokale Infrastruktur im _stack_ Ordner bereitgestellt.

### Voraussetzungen

Folgende Programme / Tools müssen installiert sein:

* IDE welche Java, TypeScript und VueJs unterstützt (IntelliJ Idea, VSCode)
* Maven
* NodeJs
* OpenJDK 11
* Docker

In der Hostdatei des Computers muss keycloak als Hostname hinzugefügt wurden sein.

Dateipfad Linux/Mac: /etc/hosts
Dateipfad Windows: C:\Windows\System32\Drivers\etc\hosts

Zeile `127.0.0.1 localhost` muss zu `127.0.0.1 localhost keycloak` geändert werden.

### Komponenten der Infrastruktur

Die lokale Infrastruktur ist mittels docker-compose aufgesetzt.

In der folgenden Abbildung ist der grundlegende Aufbau zu sehen:

![grundlegender Aufbau des lokalen Stacks](~@source/images/platform/guides/technical-setup/docker-setup.png)

Dabei werden folgende Bausteine verwendet:

* [PostgreSQL](https://www.postgresql.org/)
* [Keycloak](https://www.keycloak.org/)
* [Keycloak Migration](https://github.com/mayope/keycloakmigration)
* [Apache Kafka](https://kafka.apache.org/)
* [Nginx](https://www.nginx.com/)

### Mögliche Szenarien zum Starten der Infrastruktur

Aktuell gibt es zwei mögliche Szenarien.

#### Szenario 1: lokale Infrastruktur starten um Tasklist Backend und Frontend zu entwickeln

Das erste Szenario ist für die Entwicklung der Tasklist (aktuelles Wording: Engine). Dabei wird das Docker-Compose Projekt so gestartet, dass die notwendigen Servies mit der Tasklist-backend Jar (aktuelles Wording: digiwf-engine) und dem Vite Server für das Tasklist Frontend kommunizieren können.

Dazu in der `stack/docker-compose.yaml` die Konfiguration des Api Gateways (Servicename: digiwf-gateway) anpassen.

Environments: 

```
SPRING_PROFILES_ACTIVE: local
ENGINE_SERVER_HOST: "host.docker.internal"
```

Des Weiteren müssen die extra_hosts noch konfiguriert werden: 

```
extra_hosts:
  - "host.docker.internal:host-gateway"
```

Danach das Docker Compose Projekt starten. 
Dazu im Ordner _stack_ ausführen:

```docker compose up -d```

Danach sollte die Ausgabe von `docker ps` ungefähr wie folgt aussehen:

![Ausgabe von docker ps mit allen gestarteten Services](~@source/images/platform/guides/technical-setup/docker-ps-output.png)

Wenn das Api Gateway nicht hochgefahren ist, noch einmal `docker compose up -d` ausführen.

Danach startet man das Tasklist Backend (EngineServiceApplication). 

Dazu startet man dieses mit folgenden Profilen: local, streaming, no-ldap

Zusätzlich bindet man die .env Datei aus dem Stack Ordner ein (Dafür kann man das Idea Plugin [EnvFile](https://plugins.jetbrains.com/plugin/7861-envfile) nutzen)

Ist das Backend erfolgreich gestartet, startet man noch das Frontend. (`npm run serve:tasklist` im _digiwf-apps_ Ordner)

Danach im Browser [http://localhost:8082](http://localhost:8082) aufrufen. Damit wird man auf das Api Gateway geleitet.

Es sollte eine Anmeldeseite erscheinen, welche von Keycloak bereitgestellt wird.

Dort meldet man sich mit dem Nutzername _johndoe_ und dem Passwort _test_ an.
Bei erfolgreichem Login bekommt man eine 500 zurück.  

Danach wechselt man auf [http://localhost:8081](http://localhost:8081). Man sollte jetzt das Frontend sehen. Alle Netzwerkrequests sollten erfolgreich beantwortet werden können.

### Szenario 2: lokale Infrastruktur starten, um alles in Docker Containern zu betreiben

Im Ordner _stack- ausführen:
```docker compose --profile tasklist up -d```

