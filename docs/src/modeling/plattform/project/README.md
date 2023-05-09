# Projekte

Projekte bilden eine Klammer um die verschiedenen Artefakte, die bei der Modellierung von Geschäftsprozessen verwendet
werden.

- [BPMN-Diagramme](/modeling/processes/modeling/)
- [DMN-Diagramme](https://docs.camunda.org/manual/7.19/reference/dmn/)
- [Formulare](/modeling/forms/)
- [Prozesskonfigurationen](/modeling/processes/config/)

Die verschiedenen Artefakte können in einem Projekt geteilt werden, um die Zusammenarbeit zwischen
unterschiedlichen Benutzern zu erleichtern.

## Projekt anlegen

Ein neues Projekt kann in der Co-Creation-Plattform angelegt werden.

![Projekt anlegen](~@source/modeling/plattform/project/create.png)

## Rollen und Rechte

In Co-Creation-Projekten ist es wichtig, dass die Benutzer unterschiedliche Rollen und Rechte haben, um den Prozess der
Zusammenarbeit und der Artefaktverwaltung zu steuern. Dabei gibt es verschiedene Aktionen, für die unterschiedliche
Rollen und Rechte benötigt werden, wie beispielsweise das Anlegen von Artefakten, das Löschen von Artefakten, das Teilen
von Artefakten und das Deployen von Prozessen.

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
    - Artefakte löschen
    - Artefakte deployen
- Besitzer

::: tip
Die Rollen bauen aufeinander auf.
Der ``Administrator`` hat bspw. auch alle Rechte, die ein ``Mitglied`` hat.
:::

## Benutzer einladen

Benutzer können in einem Projekt eingeladen werden, um an der Erstellung bzw. Bearbeitung von Artefakten mitzuwirken.
Dazu kann im jeweiligen Projekt im Menü *Mitglieder verwalten* geöffnet werden.

![Mitglieder verwalten](~@source/modeling/plattform/project/members.png)

Anschließend kann ein Benutzer hinzugefügt und die entsprechende Rolle zugewiesen werden.

::: warning
Ein Benutzer kann erst hinzugefügt werden, nachdem dieser sich erstmalig in der Plattform angemeldet hat.
:::

![Mitglied hinzufügen](~@source/modeling/plattform/project/add_member.png)


