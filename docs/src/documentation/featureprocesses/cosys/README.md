# Cosys

## BPMN-Prozess

![Cosys Feature Prozess.](~@source/documentation/featureprocesses/cosys/cosys-feature-process.png)

## Beschreibung

Mit dem Prozess `Cosys GenerateDocument Test` kann das Generieren einer Grußkarte auf Basis eines Cosys-Formulars getestet werden.
Die Umgebungsparameter sowie der Grußkartentext können dabei in der Startform eingegeben werden. 

![Eingabeformular.](~@source/documentation/featureprocesses/cosys/input-form.png)

Zum Abschluss erhält man im Usertask eine herunterladbare Pdf-Datei mit den gemachten Eingaben.

![Prüfformular.](~@source/documentation/featureprocesses/cosys/check-form.png)

Da dieses Dokument aus dem S3 Document Storage geladen wird, kann mit diesem Prozess auch gleichzeitig das S3-Feature 
verifiziert werden.
