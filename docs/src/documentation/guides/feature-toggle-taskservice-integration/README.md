# Nutzung von Feature Toggles für Migrationen

Für die Ablösung der Taskverwaltung aus der Engine in einen eigenständigen Service war die Nutzung von Feature Toggles
notwendig.

Links zu Tasks:

* [Einführung des Feature Toggles](feature-toggle-taskservice-integration)
* [Ausbau des Feature Toggles](feature-toggle-taskservice-integration)

## Grundsätzlicher Aufbau

![Grundsätzliche Funktionsweise des Parallelbetriebs](~@source/images/platform/guides/taskservice-integration/overview.png)

Die Einstellungen werden hauptsächlich über das API-Gateway vorgenommen.

Feature Toggles werden dann bei Betrieb über Cookie-Header an das Frontend übertragen und dort ausgewertet.

Um einen parallelen Betrieb zu gewährleisten, sind folgende Dinge erforderlich:

* Unabhängige API-Clients für den Taskservice und den Engineservice
* Ein zentrales Model im Frontend
* Mapper, die von den Response Entities auf das zentrale Model mappen
* Feature-Toggle-Handler, die steuern, welcher API-Client verwendet wird.

### Unabhängige API-Clients für den Taskservice und den Engineservice

Beide API-Clients werden parallel verwendet. Sie sind unabhängig voneinander und werden separat konfiguriert.

### Ein zentrales Model im Frontend

Es wurde ein eigenes Model für die Verarbeitung der Tasks entwickelt. Die UI-Schicht kennt nur noch dieses Modell.

### Mapper, die von den Response Entities auf das zentrale Model mappen

Um von den unterschiedlichen Response Entities auf das einheitliche Model zu gelangen, wurden jeweilige
Mapperfunktionen gebaut. Diese werden in der Middleware gesteuert.

### Feature-Toggle-Handler, die steuern, welcher API-Client verwendet wird.

Das Auslesen der Cookies wird abstrahiert. Änderungen seitens des Nutzers werden im localStorage gespeichert. Bei jedem
Request wird geprüft, welcher API-Client verwendet werden soll.

## Konfiguration über das API-Gateway

Voraussetzung für den Parallelbetrieb ist die Definition der Routings für den Taskservice. Ein Beispiel hierfür ist in
der [lokalen Config](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-gateway/src/main/resources/application-local.yml)
des API-Gateways zu finden.

Die Konfiguration des Frontends erfolgt über Environment-Variablen im API-Gateway. Folgende Environment-Variablen können
konfiguriert werden:

### FEATURE_USE_TASK_SERVICE

Mögliche Werte: "true", "false"

Wenn die Environment-Variable auf "true" gesetzt wird, werden alle verfügbaren Schnittstellen des Taskservices genutzt.
Alle anderen werden weiterhin an den Engineservice angefragt.

Wenn die Environment-Variable auf "false" gesetzt wird, werden weiterhin alle Requests an den Engineservice gestellt.

### FEATURE_SHOW_BETA_BUTTON

Mögliche Werte: "true", "false"

Wenn die Environment-Variable auf "true" gesetzt wird, wird über den Avatar ein Kontextmenü bereitgestellt, in dem der
Nutzer zwischen der Nutzung der API des neuen Taskservices und des Engineservices entscheiden kann. Die Entscheidung
überschreibt den Wert von _FEATURE_USE_TASK_SERVICE_.

Wenn die Environment-Variable auf "false" gesetzt wird, wird kein Kontextmenü angezeigt.