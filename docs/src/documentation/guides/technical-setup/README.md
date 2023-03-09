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

![grundlegendender Aufbau des lokalen Stacks](~@source/images/platform/guides/technical-setup/docker-setup.png)




### Mögliche Szenarien zum Starten der Infrastruktur

#### Szenario 1: lokale Infrastruktur starten um Tasklist Backend und Frontend zu entwickeln

Im Ordner _stack_ ausführen:

```docker compose up -d```

### Szenario 2: lokale Infrastruktur starten um alles in Docker Containern zu betreiben

Im Ordner _stack- ausführen:
```docker compose --profile tasklist up -d```

