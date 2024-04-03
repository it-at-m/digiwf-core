# Engine

**Offen:** Allgemeine Beschreibung der Engine

## Aufräumen von alten Prozessdefinitionen

DigiWF bietet über die Engine verschiedene Strategien an, um alte und nicht mehr benötigte Prozessdefinitionen 
aufzuräumen. Definitionen, die keine laufenden Instanzen haben und nicht die aktuellste Version sind, 
können mit einem Endpunkt direkt gelöscht werden. Zusätzlich gibt es die Möglichkeit, alte Prozess-Instanzen 
automatisch auf die neueste Definitions-Version zu migrieren. Sollten alte Instanzen nicht migrierbar sein, 
können sie entweder nach Ablauf einer bestimmten Zeit (standardmäßig 185 Tage) oder ab einem gewissen Schwellwert an Versionen 
mit den abgelaufenen Definitionen gelöscht werden.

Folgende Endpunkte stehen zur Verfügung:

- `GET admin/process-definitions/key`: Gibt eine Liste aller eindeutigen Prozessdefinition-Schlüssel zurück.
- `GET admin/process-definitions/key/{key}`: Gibt Informationen zu einem bestimmten Prozessdefinition-Schlüssel zurück, wie beispielsweise die Anzahl der Instanzen und das Datum der neuesten Instanz.
- `POST admin/process-definitions/key/{key}/migrate`: Versucht, alle Instanzen eines Prozessdefinition-Schlüssels auf die neueste Version zu migrieren.
- `DELETE admin/process-definitions/key/{key}/obvious`: Löscht alle Prozessdefinitionen mit 0 Instanzen, die älter als eine voreingestellte Zeit sind (standardmäßig 185 Tage).
- `DELETE admin/process-definitions/key/{key}/threshold`: Löscht alle Prozessdefinitionen eines bestimmten Schlüssels, die mehr Instanzen als der Schwellenwert haben.

Beispiel-Anfragen zu den Endpunkten können hier eingesehen werden: [cleanup.http](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-engine/digiwf-engine-service/cleanup.http)