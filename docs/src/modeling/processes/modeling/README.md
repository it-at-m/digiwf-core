# Modellierung

Bei der Modellierung von Prozessen in DigiWF können verschiedene Eigenschaften gesetzt werden.
Für die Plattform und Camunda als verwendete Engine sind die folgenden Eigenschaften wichtig:

- **Id:** Die Id des Prozesses. Bitte stimmen Sie diese mit dem DigiWF Team ab. Es ist sinnvoll eine Versionierung für
  inkompatible Änderungen vorzunehmen: ProzessName_V01
- **Name:** Der Name des Prozesses, der in der Plattform angezeigt wird.
- **Version Tag:** Version des Prozesses. Ist für die Verwaltung eines Prozess hilfreich.
- **History Time To Live:** Die Zeitspanne in Tagen, die ein Prozess und dessen Daten aufbewahrt wird, nachdem er
  abgeschlossen ist. Wird kein Wert eingetragen, findet der Standard-Wert von 185 Tagen Anwendung, der in der DigiWF
  Camunda-Engine konfiguriert ist. Mehr dazu unter Aufräumfrist
- **Element Documentation:** Beschreibung des Prozesses, die in der Plattform angezeigt wird. Erleichtert die Suche für
  Anwender:innen, passende Vorgänge zu finden.
