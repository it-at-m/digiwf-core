# Modellierung

Bei der Modellierung von Prozessen in DigiWF können verschiedene Eigenschaften gesetzt werden.
Für die Plattform und Camunda als verwendete Engine sind die folgenden Eigenschaften wichtig:

- **Id:** Die Id des Prozesses. Diese sollte mit dem Plattformbetreiber abgestimmt werden, da diese eindeutig innerhalb
  der Plattforminstanz sind und sich nicht überschreiben dürfen. Es ist sinnvoll eine
  Versionierung für inkompatible Änderungen vorzunehmen: `ProzessName_V01`
- **Name:** Der Name des Prozesses, der in der Plattform angezeigt wird.
- **Version Tag:** Version des Prozesses. Ist für die Verwaltung eines Prozess hilfreich.
- **Element Documentation:** Beschreibung des Prozesses, die in der Plattform angezeigt wird. Erleichtert die Suche für
  Anwender:innen, passende Vorgänge zu finden.
- **History Time To Live:** Prozessdaten dürfen nur solange aufgehoben werden, wie sie für die Verarbeitung
  benötigt werden. D.h. nach dem Beenden einer Prozessinstanz sind die Daten generell nicht mehr relevant und müssen
  aufgeräumt werden. Eine Ausnahmne dabei bildet die Erfordernis, Prozessdaten z.B. über Optimize auswerten zu können.
  DigiWF sieht deshalb eine standardmäßig eingestellte Löschfrist von 185 Tagen (ca. 6 Monate) vor. Nach dieser Zeit
  werden die historischen Prozessdaten aus der Datenbank gelöscht. Sollten Sie für Ihren Prozess eine andere Anforderung
  für die Löschzeit haben, können Sie das in dem Properties-Panel des Modelers konfigurieren.
- **Tasklist Configuration: Startable:** Mit diesem Flag kann gesteuert werden, ob der Prozess unter "Vorgang starten" in 
  der DigiWF-Taskliste angezeigt wird. Für Prozessbausteine sollte dieses deaktiviert werden.
  

![Example Process](~@source/modeling/processes/modeling/example_process.png)

## Variablen

DigiWF speichert in der Prozessinstanz verschiedene Variablen, die für die Modellierung verwendet werden können.

- **starterOfInstance:** Der Benutzer, der die Prozessinstanz über die DigiWF Taskliste gestartet hat.
- **app_file_context:** Der Kontext, in dem die Datei hochgeladen werden.
- **app_task_tag:** Der Tag, der für die Aufgabe (Usertask) gesetzt wurde und nach dem gefiltert werden kann. Die Variable ist standardmäßig leer und muss in der Modellierung bei Bedarf gesetzt werden.
- **app_process_status:** Bezeichnung des aktuellen Status, in der sich die Prozessinstanz befindet
- **app_process_start_date:** Startzeitpunkt der Prozessinstanz (Nicht zu verwechseln mit Antragseingang)

::: warning
Die folgenden Variablen stehen ebenfalls zur Verfügung, sollten aber nicht verwendet werden, da sie bald abgeschafft
werden.
:::

- **app_process_info_id:** Id der aktuellen Prozessinstanz
- **app_process_description:** Beschreibung der Prozessinstanz die über diese Funktion gesetzt werden kann

## Best Practices

### Der Start von Prozessen sollte immer asynchrone sein

In der Regel sollte der Start eines Prozesses, egal ob None/Message/Timer-Event, mit Async-After konfiguriert werden. 
Dadurch wird der Prozess korrekt in der Datenbank abgelegt, bevor eine weitere Activity ausgeführt wird. Dies ist notwendig, da sonst 
ein Fehler in einer folgenden Activity, z.B. Skript-Task, zum kompletten Abbruch des Prozess-Starts führt und somit nicht in der Datenbank 
gespeichert wird.

![Example Process](~@source/modeling/processes/modeling/async-start.png)

Dieses Verhalten kann man sich aber auch zunutze machen, falls die Eingangsparameter über Skripte oder andere synchrone Checks geprüft werden müssen.
In dem Fall sollte der Asnyc-After nach der Check-Activity gesetzt werden. 