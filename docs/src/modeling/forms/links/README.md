# Verlinkung von Webseiten oder Dokumenten an Nutzeraufgabe 

DigiWF stellt die Möglichkeit bereit als Prozessmodellierer dem Sachbearbeiter zusätzliche Informationen bereitzustellen.

Die Links können dazu über eine Prozessvariable erstellt werden.

Die Links werden unter dem Taskname angezeigt.

## Konfiguration 

Das Setzen der Links wird hier an hand von einem Inlinescript erzeugt: 

```js
var ArrayList = Java.type('java.util.ArrayList');
var TaskExternalReference = Java.type('de.muenchen.oss.digiwf.task.TaskExternalReference');
externalLinks = new ArrayList();
externalLinks.add(new TaskExternalReference('url', '[Google](https://google.de/)'));
externalLinks.add(new TaskExternalReference('zammad', '[Ticket 11004832](LHM11004832)'));
externalLinks.add(new TaskExternalReference('mucsdms', '[Vorgang 1](COO.2150.307.2.41134)'));
execution.setVariable('app_task_external_links', externalLinks);

```
Die Links werden als Liste in der Prozessvariable `app_task_external_links` gespeichert. 

Dabei muss die Liste mit Objekten von Typen `TaskExternalReference` gefüllt werden. 
Dabei werden zwei Parameter für den Konstruktur benötigt: 
1. Typ (Typen siehe Abschnitt Typen)
2. Linkinformationen in Markdownsyntax. Dabei wird das Label in [] Klammern geschrieben, der Wert in den Runden Klammern.

## Unterstützte Typen

| Name    | Beschreibung                                  | Wert                         |
|---------|-----------------------------------------------|------------------------------|
| url     | allgemeiner Link zu einer bestimmten Webseite | URL Der Webseite             |
| zammad  | Link zu Zammad Ticket                         | Ticket ID                    |
| mucsdms | Link zum Schriftstück                         | COO Nummer des Schriftstücks |

