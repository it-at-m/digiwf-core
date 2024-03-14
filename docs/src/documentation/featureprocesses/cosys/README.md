# Cosys

## BPMN-Prozess

![Cosys Feature Prozess.](~@source/documentation/featureprocesses/cosys/cosys-feature-process.png)

## Beschreibung

Die Prozesse `Cosys GenerateDocument Test` und `Cosys GenerateDocument Test (Streaming)` ermöglichen das Testen des
Generierens einer Grußkarte auf Basis eines Cosys-Formulars. Der zweite Prozess basiert auf der asynchronen
Streaming-Integration und erfordert den digiwf-Connector in der Umgebung.

In der Startform können Umgebungsparameter sowie der Grußkartentext eingegeben werden.

![Eingabeformular.](~@source/documentation/featureprocesses/cosys/input-form.png)

Am Ende des Prozesses erhält man im Usertask eine herunterladbare PDF-Datei mit den gemachten Eingaben.

![Prüfformular.](~@source/documentation/featureprocesses/cosys/check-form.png)

Da dieses Dokument aus dem S3 Document Storage geladen wird, kann mit diesem Prozess auch gleichzeitig das S3-Feature
verifiziert werden.