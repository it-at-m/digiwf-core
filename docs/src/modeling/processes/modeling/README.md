# Modellierung

Bei der Modellierung von Prozessen in DigiWF können verschiedene Eigenschaften gesetzt werden.
Für die Plattform und Camunda als verwendete Engine sind die folgenden Eigenschaften wichtig:

- **ID:** Die ID des Prozesses. Diese sollte mit dem Plattformbetreiber abgestimmt werden, da sie innerhalb
  der Plattforminstanz eindeutig sein muss und sich nicht überschreiben lässt. Es ist sinnvoll, eine
  Versionierung für inkompatible Änderungen vorzunehmen, z.B.: `ProzessName_V01`.
- **Name:** Der Name des Prozesses, der in der Plattform angezeigt wird.
- **Version Tag:** Version des Prozesses. Ist hilfreich für die Verwaltung des Prozesses.
- **Element Documentation:** Beschreibung des Prozesses, die in der Plattform angezeigt wird. Erleichtert die Suche für
  Anwender:innen, passende Vorgänge zu finden.
- **History Time To Live:** Prozessdaten dürfen nur solange aufgehoben werden, wie sie für die Verarbeitung
  benötigt werden. D.h. nach dem Beenden einer Prozessinstanz sind die Daten generell nicht mehr relevant und müssen
  aufgeräumt werden. Eine Ausnahme bildet die Erfordernis, Prozessdaten z.B. über Optimize auswerten zu können.
  DigiWF sieht deshalb eine standardmäßig eingestellte Löschfrist von 185 Tagen (ca. 6 Monate) vor. Nach dieser Zeit
  werden die historischen Prozessdaten aus der Datenbank gelöscht. Sollten Sie für Ihren Prozess eine andere Anforderung
  für die Löschzeit haben, können Sie das im Properties-Panel des Modelers konfigurieren.
- **Tasklist Configuration: Startable:** Mit diesem Flag kann gesteuert werden, ob der Prozess unter "Vorgang starten"
  in
  der DigiWF-Taskliste angezeigt wird. Für Prozessbausteine sollte dieses deaktiviert werden.

![Example Process](~@source/modeling/processes/modeling/example_process.png)

## Variablen

DigiWF speichert in der Prozessinstanz verschiedene Variablen, die für die Modellierung verwendet werden können.

- **starterOfInstance:** Der Benutzer, der die Prozessinstanz über die DigiWF Taskliste gestartet hat.
- **app_file_context:** Der Kontext, in dem die Datei hochgeladen wird.
- **app_task_tag:** Der Tag, der für die Aufgabe (Usertask) gesetzt wurde und nach dem gefiltert werden kann. Die
  Variable ist standardmäßig leer und muss in der Modellierung bei Bedarf gesetzt werden.
- **app_process_status:** Bezeichnung des aktuellen Status, in der sich die Prozessinstanz befindet.
- **app_process_start_date:** Startzeitpunkt der Prozessinstanz (nicht zu verwechseln mit Antragseingang).

::: warning
Die folgenden Variablen stehen ebenfalls zur Verfügung, sollten aber nicht verwendet werden, da sie bald abgeschafft
werden.
:::

- **app_process_info_id:** ID der aktuellen Prozessinstanz.
- **app_process_description:** Beschreibung der Prozessinstanz, die über diese Funktion gesetzt werden kann.

## Prozess-Start

Neue Prozessinstanzen können auf [verschiedene Art und Weise gestartet](https://docs.camunda.org/manual/current/reference/bpmn20/events/start-events/) 
werden, z.B. None-, Timer- oder Message-Events. Während des Starts initialisiert DigiWF die oben genannten Variablen
sowie alle benötigten DigiWF-Daten außerhalb von Camunda.

### Best Practices: Der Start von Prozessen sollte immer asynchron sein

In der Regel sollte der Start eines Prozesses, egal ob None/Message/Timer-Event, mit Async-After konfiguriert werden.
Dadurch wird der Prozess korrekt in der Datenbank abgelegt, bevor eine weitere Activity ausgeführt wird. Dies ist
notwendig, da sonst
ein Fehler in einer folgenden Activity, z.B. Skript-Task, zum kompletten Abbruch des Prozess-Starts führt und somit
nicht in der Datenbank gespeichert wird.

![Example Process](~@source/modeling/processes/modeling/async-start.png)

Dieses Verhalten kann man sich aber auch zunutze machen, falls die Eingangsparameter über Skripte oder andere synchrone
Checks geprüft werden müssen.
In dem Fall sollte der Async-After nach der Check-Activity gesetzt werden.