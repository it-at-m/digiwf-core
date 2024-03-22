# DigiWF Connector

Der DigiWF Connector ermöglicht eine nahtlose Verbindung zwischen der Camunda Engine (DigiWF Engine) und
den [DigiWF Integrationen](/integrations/).
Er bietet eine einfache und effiziente Möglichkeit, Integrationen aufzurufen, Events zu handhaben und die Kommunikation
zwischen den verschiedenen Systemen,
die in Ihren Geschäftsprozessen involviert sind, zu verwalten.

## Verbindung zwischen DigiWF Engine und DigiWF Integrationen

Der DigiWF Connector stellt eine Verbindung zur Camunda Engine (DigiWF Engine) über die ExternalTask-API her.
Diese ExternalTasks werden nacheinander vom Connector abgearbeitet, indem aus den ExternalTasks Events erzeugt und an
den Event Bus (Apache Kafka) gesendet werden.
Die Events werden von den DigiWF Integrationen verarbeitet und die Ergebnisse werden an den Connector zurückgegeben, der
die Ergebnisse dann an die Camunda Engine (DigiWF Engine) weitergibt (CorrelateMessage).

Im Bpmn-Prozess wird dies als Call Activity modelliert, die das StreamingTemplateV02 aufruft.

## Features

Folgende Features unterstützt der DigiWF Connector:

### Default Event Topics

Im Connector können für die Integrationen default Event Topics definiert werden. Diese werden verwendet, wenn im Bpmn
Prozess kein Event Topic angegeben wurde.
Das default Event Topic für eine Integration wird anhand des Integration Names aus der Config geladen.
Eine Liste der Integrationen mit default Event Topics wird unter `de.muenchen.oss.digiwf.connector.core.integrations` im
Connector konfiguriert.

### Custom Event Topics

Wenn zusätzlich zum Integration-Name ein Event Topic im Bpmn-Prozess angegeben wurde, wird dieses verwendet.
Dadurch können weitere Integrationen an die DigiWF-Plattform angebunden werden.

### Policies

tbd.

### Fehlerbehandlung

Wenn während des Aufrufs einer Integration oder beim Event-Handling ein Fehler auftritt, wirft die jeweilige Integration
einen BPM-Error oder Incident-Error.
Diese werden an den Connector weitergeleitet und dieser behandelt sie. Im Falle eines BPM-Errors wird der Fehler an den
Prozess korreliert.
Bei einem Incident-Error wird ein Incident erzeugt.

Eine detaillierte Beschreibung der Fehlerbehandlung finden Sie
unter [Fehlerbehandlung Integration](/integrations/concept/error-handling).