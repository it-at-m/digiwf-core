# Autorisierung

In DigiWF wird zwischen zwei Arten von Autorisierungen unterschieden:

- **Autorisierung von Prozessinstanzen** - Autorisierung von Prozessinstanzen ist die Autorisierung von Benutzern, um
  auf eine Prozessinstanz zugreifen zu können.
- **Autorisierung von Prozessen** - Autorisierung von Prozessen ist die Autorisierung von Benutzern, um Prozessinstanzen
  zu starten.

## Autorisierung von Prozessinstanzen

DigiWF bietet die Möglichkeit einem Bearbeiter oder einer Bearbeiterin, Zugriff auf einen bereits gestarteten Vorgang zu
geben. Dadurch kann der oder die Bearbeiter:in jederzeit den Status der Instanz einsehen und prüfen, welche Aufgaben
bereits erledigt wurden. Falls entsprechend in der [Prozesskonfiguration](/modeling/processes/config/) hinterlegt,
können auch
Prozessinstanzdaten eingesehen werden.

::: tip
Die Person, die eine Prozessinstanz startet, wird automatisch für die die Instanz berechtigt.
:::

Weitere Sachbearbeiter können beim Abschließen einer Benutzeraufgabe für die Prozessinstanz autorisiert werden. Um dies
zu konfigurieren, muss der Input-Parameter `app_assign_user_to_processinstance` auf `true` gesetzt werden.

![Autorisierung von Prozessinstanzen](~@source/modeling/processes/authorization/authorization_processinstance.png)

::: tip
Diese Funktion ist Bestandteil des `Basic: User Task`-Templates. Nähere Informationen dazu finden Sie im
entsprechenden [Modeling Abschnitt](/modeling/user-tasks/modeling).
:::

## Autorisierung von Prozessen

DigiWF ermöglicht es, dass nur bestimmte Benutzer:innen Prozessinstanzen starten können. Hierfür wird die Funktionalität
der Process Engine Camunda genutzt. Weitere Informationen dazu, wie Autorisierungen in Camunda konfiguriert werden
können, finden Sie in
der [Dokumentation](https://docs.camunda.org/manual/7.15/user-guide/process-engine/authorization/).

Für eine Prozessdefinition müssen in DigiWF die folgenden Berechtigungen gesetzt werden:

- READ
- CREATE_INSTANCE
- READ_INSTANCE

![Autorisierung von Prozessen](~@source/modeling/processes/authorization/authorization_process.png)