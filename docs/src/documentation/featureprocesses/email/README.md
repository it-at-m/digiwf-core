# E-Mail

## BPMN-Prozess

![Email Feature Prozess](~@source/documentation/featureprocesses/email/email-feature-process.png)

Der Beispielprozess sowie die Formulare können
unter [https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/email-example-V02](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/email-example-V02)
abgerufen werden.

## Beschreibung

Mit dem Prozess `example-email-V02` kann das Versenden von E-Mails mit und ohne Anhang getestet werden.

Alle relevanten Daten für die E-Mail können im Startformular des Prozesses angegeben werden und anschließend wird die
E-Mail versendet. Falls das Versenden der E-Mail fehlschlägt (z.B. weil keine gültige E-Mail-Adresse als Empfänger
angegeben wurde), wird ein User Task erstellt und dem aktuellen Benutzer zugewiesen, der die Fehlermeldung angezeigt
bekommt.

![Startformular](~@source/documentation/featureprocesses/email/Startformular.png)

Wenn im Startformular der Haken für das Versenden einer E-Mail mit Dateianhang gesetzt wird, kann mit diesem Prozess auch
gleichzeitig das S3-Feature verifiziert werden. Dafür wird ein zweiter User Task erstellt, in dem der Benutzer die Datei
auswählen kann, die als Anhang an die E-Mail angehängt werden soll.

![Prüfformular](~@source/documentation/featureprocesses/email/fehlermeldung-pruefen.png)
