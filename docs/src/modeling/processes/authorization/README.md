# Autorisierung

In DigiWF wird zwischen zwei Arten von Autorisierungen unterschieden:

- **Autorisierung von Prozessinstanzen** - Autorisierung von Prozessinstanzen ist die Autorisierung von Benutzern, um
  auf eine Prozessinstanz zugreifen zu können.
- **Autorisierung von Prozessen** - Autorisierung von Prozessen ist die Autorisierung von Benutzern, um Prozessinstanzen
  zu starten.

## Autorisierung von Prozessinstanzen

DigiWF bietet die Möglichkeit einem Bearbeiter:in Zugriff auf einen gestarteten Vorgang zu geben. Dadurch hat dieser die
Möglichkeit, jederzeit den Status der Instanz einzusehen und zu prüfen, welche Aufgaben bereits abgeschlossen wurden.
Zudem gibt es die Möglichkeit Prozessinstdaten einzusehen, wenn dies entsprechend in
der [Prozesskonfiguration](/modeling/processes/config/) hinterlegt ist.

::: tip
Die Person, die eine Prozessinstanz startet, wird automatisch für die die Instanz berechtigt.
:::

Weitere Sachbearbeiter können beim Abschließen einer Benutzeraufgabe für die Prozessinstanz autorisiert werden. Dies
können Sie konfigurieren, indem Sie den Input Parameter `app_assign_user_to_processinstance` auf true setzen.

![Autorisierung von Prozessinstanzen](~@source/modeling/processes/authorization/authorization_processinstance.png)

::: tip

Diese Funktionalität ist Bestandteil des `Basic: User Task` Templates. Dieses ist im
entsprechenden [Modeling Abschnitt](/modeling/user-tasks/modeling) beschrieben.

:::

## Autorisierung von Prozessen

DigiWF bietet die Möglichkeit, dass nur bestimmte Benutzer:innen Prozessinstanzen starten können. Hierfür wird die
Funktionalität der Process Engine Camunda verwendet.
Genauere Informationen wie Autorisierungen in Camunda konfiguriert werden können, sind in
der [Dokumentation](https://docs.camunda.org/manual/7.15/user-guide/process-engine/authorization/) zu finden.

Für eine Prozessdefinition müssen in DigiWF die folgenden Berechtigungen gesetzt werden:

- READ
- CREATE_INSTANCE
- READ_INSTANCE

![Autorisierung von Prozessen](~@source/modeling/processes/authorization/authorization_process.png)