# Modellierung

- **Id:** Id der Aufgabe. Diese sollte für eine bessere Fehleranalyse entsprechen benannt sein: Task_TaskName
- **Name:** Der Name der Aufgabe, der in der Aufgabenliste angezeigt wird
- **Assignee:** Id des Benutzers, dem die Aufgabe zugewiesen ist.
- **Candidate Users:** IDs von Benutzern, die die Aufgabe bearbeiten dürfen (mehrere Benutzer mit , separiert)
- **Candidate Group:** Benutzergruppen, die die Aufgabe bearbeiten dürfen (mehrere Gruppen mit , separiert)

![Example User Task](~@source/modeling/user-tasks/modeling/example_user_task.png)

## Template

Für die Modellierung von Benutzeraufgaben können Element Templates verwendet werden. Diese können über das Menü *Element
Templates* geöffnet werden. Das Template dient als Schnittstelle für Variablen, die an Benutzeraufgaben gesetzt werden
können. Zu den bereits erwähnen, gibt es weitere Variablen, die von DigiWF definiert wurden.

![Element Templates](~@source/modeling/user-tasks/modeling/element_templates.png)

- **Beschreibungstext**: Mit dem Parameter **app_task_description** ist es möglich eine detaillierte Beschreibung für
  die Aufgabe zu hinterlegen. Diese ist hilfreich, um Kontextinformationen zu einer Aufgabe in der Aufgabenliste
  anzuzeigen.
- **Formular Key**: Die Id des Formulars mit dem die Aufgabe bearbeitet werden soll.
- **Candidate Users benachrichtigen?**: Mit dem Parameter **app_notification_send_candidate_users** kann festgelegt
  werden, ob Benutzer, die die Aufgabe bearbeiten dürfen, per E-Mail benachrichtigt werden sollen.
- **Assignee benachrichtigen?**: Mit dem Parameter **app_notification_send_assignee ** kann festgelegt
  werden, ob der Benutzer, der der Aufgabe zugewiesen ist, per E-Mail benachrichtigt werden sollen.
- **Candidate Groups benachrichtigen?**: Mit dem Parameter **app_notification_send_candidate_users** kann festgelegt
  werden, ob Gruppen, die die Aufgabe bearbeiten dürfen, per E-Mail benachrichtigt werden sollen. Hierzu muss
  der Gruppe in der jeweiligen Benutzerverwaltung eine E-Mail-Adresse zugeordnet sein.
- **User für Vorgang berechtigen?**: Darüber kann gesteuert werden, ob ein Benutzer für die zu bearbeitende
  Prozessinstanz berechtigt wird. Dadruch erhält dieser in der Tasklist Zugriff.
- **S3 Pfade für Schreibrechtigungen**: Mit dem Parameter können Pfade für Schreibrechte
  für den Benutzer hinterlegt werden. Diese können dann in der Aufgabe verwendet werden, um Dateien hochzuladen.
- **S3 Pfade für Leseberechtigungen**: Mit dem Parameter können Pfade für Leseberechtigungen
  für den Benutzer hinterlegt werden. Diese können dann in der Aufgabe verwendet werden, um Dateien anzuzeigen.