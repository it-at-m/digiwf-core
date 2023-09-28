# Email

## BPMN-Prozess

![Email Feature Prozess](~@source/documentation/featureprocesses/email/email-feature-process.png)

Der Beispielprozess sowie die Formulare können unter [https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/email-example-V02](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/email-example-V02) abgerufen werden.

## Beschreibung

Mit dem Prozess `example-email-V02` kann das Versenden von Emails mit und ohne Email Anhang getestet werden.

Hierbei können alle für die Email relevanten Daten im Startformular des Prozesses angegeben werden und anschließend wird eine Email versendet.
Sollte das Versenden der Email fehlschlagen (z.B. weil keine gültige Email Addresse als Empfänger angegeben wurde), so wird ein User Task erstellt und dem aktuellen Benutzer zugewiesen, der die Fehlermeldung anzeigt.

![Startformular](~@source/documentation/featureprocesses/email/Startformular.png)

Wird im Startformular der Haken für das Versenden einer Email mit Dateianhängen gesetzt, kann mit diesem Prozess auch gleichzeitig das S3-Feature
verifiziert werden.
Hierfür wird ein zweiter Usertask erstellt, in dem der Benutzer die Datei auswählen kann, die als Anhang an die Email angehängt werden soll.

![Prüfformular](~@source/documentation/featureprocesses/email/fehlermeldung-pruefen.png)
