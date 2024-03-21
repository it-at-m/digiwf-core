# Projekte

Projekte dienen als Rahmen für die verschiedenen Artefakte, die bei der Modellierung von Geschäftsprozessen verwendet
werden.

- [BPMN-Diagramme](/modeling/processes/modeling/)
- [DMN-Diagramme](https://docs.camunda.org/manual/7.19/reference/dmn/)
- [Formulare](/modeling/forms/)
- [Prozesskonfigurationen](/modeling/processes/config/)

Die verschiedenen Artefakte können in einem Projekt geteilt werden, um die Zusammenarbeit zwischen verschiedenen
Benutzern zu erleichtern.

## Projekt erstellen

Ein neues Projekt kann in der Co-Creation-Plattform erstellt werden.

![Projekt anlegen](~@source/modeling/plattform/project/create.png)

## Rollen und Berechtigungen

In Co-Creation-Projekten ist es wichtig, dass Benutzer unterschiedliche Rollen und Berechtigungen haben, um den Prozess
der Zusammenarbeit und der Artefaktverwaltung zu steuern. Es gibt verschiedene Aktionen, für die unterschiedliche Rollen
und Berechtigungen erforderlich sind, wie beispielsweise das Erstellen von Artefakten, das Löschen von Artefakten, das
Teilen von Artefakten und das Deployen von Prozessen.

Folgende Rollen stehen in der Co-Creation-Plattform zur Verfügung:

- Betrachter
    - Artefakte öffnen
    - Deployments lesen
- Mitglied:
    - Artefakte anlegen
    - Artefakte updaten
    - Artefakte kopieren
    - Milestone erstellen
    - Artefakte herunterladen
- Administrator
    - Löschen von Artefakten
    - Bereitstellen von Artefakten
- Besitzer

::: tip
Die Rollen bauen aufeinander auf.
Ein ``Administrator`` hat beispielsweise auch alle Rechte, die ein ``Mitglied`` hat.
:::

## Benutzer einladen

Benutzer können in ein Projekt eingeladen werden, um bei der Erstellung bzw. Bearbeitung von Artefakten mitzuwirken.
Dazu kann im jeweiligen Projekt im Menü *Mitglieder verwalten* geöffnet werden.

![Mitglieder verwalten](~@source/modeling/plattform/project/members.png)

Anschließend kann ein Benutzer hinzugefügt und die entsprechende Rolle zugewiesen werden.

::: warning
Ein Benutzer kann erst hinzugefügt werden, nachdem er sich zum ersten Mal in der Plattform angemeldet hat.
:::

![Mitglied hinzufügen](~@source/modeling/plattform/project/add_member.png)


