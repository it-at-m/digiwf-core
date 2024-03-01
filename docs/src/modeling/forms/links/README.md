# Verlinkung von Webseiten oder Dokumenten an Nutzeraufgabe 

DigiWF stellt die Möglichkeit bereit in der Taskansicht externe Links bereitzustellen, um auf zusätzliche Informationen zu verweisen. 
Die Erstellung der Links wird über die Prozessmodellierung umgesetzt.

Die Links können dazu über eine Prozessvariable `app_task_external_links` erstellt werden.

Die Links werden dabei in der der Aufgabendetailansicht unter dem Taskname angezeigt.

![Anzeige von externen Links](~@source/images/modeling/forms/external-links.png)


## Konfiguration 

Das Setzen der Links wird hier an hand von einem Inlinescript erzeugt: 

```js
var ArrayList = Java.type('java.util.ArrayList');
var TaskExternalReference = Java.type('de.muenchen.oss.digiwf.task.TaskExternalReference');
externalLinks = new ArrayList();
externalLinks.add(new TaskExternalReference('url', 'https://google.de/'));
externalLinks.add(new TaskExternalReference('zammad', 'LHM11004832'));
externalLinks.add(new TaskExternalReference('mucsdms', 'COO.2150.307.2.41134'));
execution.setVariable('app_task_external_links', externalLinks);

```

Die Links werden als Liste in der Prozessvariable `app_task_external_links` gespeichert.
Diese muss mit Objekten von Typen `TaskExternalReference` gefüllt werden. 

Dabei werden zwei Parameter für den Konstruktur benötigt: 
1. Typ (Typen siehe Abschnitt [Unterstützte Typen](#unterstutzte-typen))
2. Die ID oder eine URL auf für die Generierung des Links. Der Wert ist vom Typen abhängig.

## Unterstützte Typen

In der folgenden Tabelle sind die unterstützten Typen aufgelistet.

| Typ     | Beschreibung                                  | anzugebener Wert                                        | Standard Anzeigetext                            |
|---------|-----------------------------------------------|---------------------------------------------------------|-------------------------------------------------|
| url     | allgemeiner Link zu einer bestimmten Webseite | URL Der Webseite beginnend mit http / https             | Eingegebene URL                                 |
| zammad  | Link zu Zammad Ticket                         | Ticket ID  im Format LHM11004832                        | Zammad Ticket &lt;Ticket Id ohne LHM Prefix&gt; |
| mucsdms | Link zum Schriftstück                         | COO Nummer des Schriftstücks  beginnend mit Prefix COO. | MUCS DMS &lt;COO Nummer&gt;                     |

## Anpassen des Anzeigetexts 

Es ist möglich den Anzeigetext in der Detailansicht des Tasks anzupassen, um Prozessspezifische Inhalte sofort anzuzeigen. 

Dazu kann die Markdownsyntax verwendet werden. Im Folgenden ist dies als Beispiel erklärt. 

Ruft man beispielsweise `new TaskExternalReference('zammad', 'LHM11004832')` auf, erscheint als Anzeigetext `Zammad Ticket 11004832`.

Wenn man den Aufruf zu `new TaskExternalReference('zammad', '[Ticket Antrag zur Hilfestellung](LHM11004832)')` ändert, erscheint der Anzeigetext `Ticket Antrag zur Hilfestellung`.
