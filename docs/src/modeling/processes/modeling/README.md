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

![Example Process](~@source/modeling/processes/modeling/example_process.png)

## Variablen

DigiWF speichert in der Prozessinstanz verschiedene Variablen, die für die Modellierung verwendet werden können.

- **starterOfInstance:** Der Benutzer, der die Prozessinstanz über die DigiWF Taskliste gestartet hat.
- **app_file_context:** Der Kontext, in dem die Datei hochgeladen werden.

::: warning
Die folgenden Variablen stehen ebenfalls zur Verfügung, sollten aber nicht verwendet werden, da sie bald abgeschafft
werden.
:::

- **app_process_status:** Bezeichung des aktuellen Status, in der sich die Prozessinstanz befindet
- **app_process_status_key:** Key des aktuellen Status, in der sich die Prozessinstanz befindet
- **app_process_description:** Beschreibung der Prozessinstanz die über diese Funktion gesetzt werden kann