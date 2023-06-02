# Migration der Taskverwaltung in den Task Service

Aktuell findet die Ablösung der Taskverwaltung aus dem Engine Service in den Taskservice. 
Dafür ist ein Parallelbetrieb im Frontend notwendig

## Grundsätzlicher Aufbau

![Grundsätzliche Funktionsweise des Parallelbetriebs](~@source/images/platform/guides/taskservice-integration/overview.png)

Die Einstellungen werden hauptsächlich über das Api Gateway vorgenommen. 

Feature Toggles werden dann bei dem Betrieb über Cookie-Header an das Frontend übersendet und da ausgewertet.

Um einen parallelen Betrieb gewährleisten zu können benötigt es folgende Dinge: 

* Unabhängige Api Clients für Taskservice und Engine Service
* ein zentrales Model im Frontend 
* Mapper welche aus den Response Entities auf das zentrale Model mappen
* Feature Toggle Handler welche steuern welcher Api-Client verwendet wird.

### Unabhängige Api Clients für Taskservice und Engine Service

Es werden beide Api Clients parallel verwendet. Diese sind unabhängig voneinander und werden separat konfiguriert.

### ein zentrales Model im Frontend

Es wurde ein eigenes Model für die Verarbeitung der Tasks entwickelt. Die UI Schicht kennt nur noch dieses Modell.

### Mapper welche aus den Response Entities auf das zentrale Model mappen

Um von den unterschiedlichen Response Entities auf das einheitliche Model zu gelangen wurden jeweilige Mapperfunktionen gebaut.
Diese werden in der Middleware gesteuert.

### Feature Toggle Handler welche steuern welcher Api-Client verwendet wird.

Das Auslesen der Cookies wird abstrahiert. Änderungen von seitens des Nutzers werden im localStorage gespeichert. Bei jedem Request wird geschaut welcher Api Client verwendet werden soll. 

## Konfiguration über das Api Gateway

Voraussetzungen für den Parallelbetrieb ist die Definition der Routings für den Taskservice. Beispiel ist in der [lokalen Config](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-gateway/src/main/resources/application-local.yml) des API Gateways zu finden.

Die Konfiguration des Frontends erfolgt über Environment Variablen im Api Gateway. Folgende Environment Variablen können konfiguriert werden:

### FEATURE_USE_TASK_SERVICE
Mögliche Werte: "true", "false" 

Wenn die Environment Variable auf "true" gesetzt wird, werden alle verfügbaren Schnittstellen des Taskservices genutzt. Alle anderen werden weiterhin an den Engine Service angefragt.

Wenn die Environment Variable auf "false" gesetzt wird, werden weiterhin alle Requests an den Engine Service gestellt.

### FEATURE_SHOW_BETA_BUTTON
Mögliche Werte: "true", "false". 

Wenn die Environment Variable auf "true" gesetzt wird, wird über den Avatar ein Kontextmenü bereitgestellt in dem der Nutzer zwischen der Nutzung der API des neuen Taskservices und des Engine Services entscheiden können. Die Entscheidung überschreibt den Wert von _FEATURE_USE_TASK_SERVICE_

Wenn die Environment Variable auf "false" gesetzt wird, wird kein Kontextmenü angezeigt.
