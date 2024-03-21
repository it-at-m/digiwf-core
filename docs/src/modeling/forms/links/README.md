# Verlinkung von Webseiten oder Dokumenten an Nutzeraufgabe

DigiWF ermöglicht es, in der Taskansicht externe Links bereitzustellen, um auf zusätzliche Informationen zu verweisen.
Die Erstellung der Links erfolgt über die Prozessmodellierung.

Die Links können über die Prozessvariable `app_task_external_links` erstellt werden und werden in der
Aufgabendetailansicht unter dem Tasknamen angezeigt (siehe Abbildung).

![Anzeige von externen Links](~@source/images/modeling/forms/external-links.png)


## Konfiguration

Die Erstellung der Links erfolgt durch ein Inlinescript, das wie folgt aussieht:

```js
var ArrayList = Java.type('java.util.ArrayList');
var TaskExternalReference = Java.type('de.muenchen.oss.digiwf.task.TaskExternalReference');
externalLinks = new ArrayList();
externalLinks.add(new TaskExternalReference('url', 'https://google.de/'));
externalLinks.add(new TaskExternalReference('zammad', 'LHM11004832'));
externalLinks.add(new TaskExternalReference('mucsdms', 'COO.2150.307.2.41134'));
execution.setVariable('app_task_external_links', externalLinks);
```

Die Links werden als Liste in der Prozessvariable `app_task_external_links` gespeichert und müssen mit Objekten vom
Typ `TaskExternalReference` gefüllt werden. Hierbei sind zwei Parameter für den Konstruktor erforderlich:

1. Der Typ (siehe Abschnitt [Unterstützte Typen](#unterstutzte-typen)).
2. Die ID oder URL für die Generierung des Links. Der Wert ist vom Typen abhängig.

## Unterstützte Typen

In der folgenden Tabelle sind die unterstützten Typen aufgelistet.

| Typ     | Beschreibung                                  | anzugebener Wert                                        | Standard-Anzeigetext                            |
|---------|-----------------------------------------------|---------------------------------------------------------|-------------------------------------------------|
| url     | allgemeiner Link zu einer bestimmten Webseite | URL der Webseite beginnend mit http / https             | Eingegebene URL                                 |
| zammad  | Link zu Zammad-Ticket                         | Ticket-ID im Format LHM11004832                         | Zammad Ticket &lt;Ticket-ID ohne LHM-Prefix&gt; |
| mucsdms | Link zum Schriftstück                         | COO-Nummer des Schriftstücks  beginnend mit Prefix COO. | MUCS DMS &lt;COO-Nummer&gt;                     |

## Anpassen des Anzeigetexts

Es ist möglich, den Anzeigetext in der Detailansicht des Tasks anzupassen, um prozessspezifische Inhalte sofort
anzuzeigen. Dazu kann die Markdown-Syntax verwendet werden. Im Folgenden ist ein Beispiel dafür erklärt.

Wird beispielsweise `new TaskExternalReference('zammad', 'LHM11004832')` aufgerufen, erscheint als
Anzeigetext `Zammad Ticket 11004832`. Wenn der Aufruf jedoch zu `new TaskExternalReference('zammad', '[Ticket Antrag zur Hilfestellung](LHM11004832)')`
geändert wird, erscheint der Anzeigetext als `Ticket Antrag zur Hilfestellung`.