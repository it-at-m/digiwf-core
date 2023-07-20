# Fehlerbehandlung Integrationen


## Zielgruppe

* Integrationsentwickler*innen
* Prozessmodelierer*innen


## Fehlerarten

Mögliche Fehler im DigiWF-Integrationsumfeld können sein:
* Fehler außerhalb der Message Consumer
  * Deserialisierung: Eine empfangene Nachricht hat nicht das erwartete Format und kann somit nicht in die Zielklasse konvertiert werden.
  * Connection-Timeout zu einem Zielsystem
  * Ziel-Topic ist nicht vorhanden
  * Expiration der Message
* Fehler innerhalb der Message Consumer
  * Exceptions bei der Verarbeitung


## Fehlerhandling Kontext 

![Fehlersequenz](~@source/images/platform/integrations/ErrorHandlingIntegrations.drawio.png)


## Fehlerhandling der Integration

Integrationsbausteine funktionieren in DigiWF über den Mechanismus der External-Tasks. Der Connector als Client ist dafür verantwortlich, 
die Tasks aus der Queue auszulesen und and die Integration-Services weiterzuverteilen. Tritt bei diesem Publishing bereits ein Fehler auf, 
z.B. weil das angegebene Ziel-Topic nicht verfügbar ist, wird die eingegangene Message in den
[DLQ Stage 1](#dlq-stage-1) verschoben.

Ist das Publishing erfolgreich, übernimmt der Integration-Service mit seinem Message Consumer. 

Für den Fall, dass noch außerhalb des Message-Consumers beim Deserialisieren der Message ein Fehler auftritt, ist ein _ErrorHandlingDeserializer_ registriert,
der mit den Header-Informationen der Message einen Incident erstellt (Näheres dazu siehe unten).

Tritt bei der Verarbeitung einer Message generell ein Fehler auf, kommt zuerst ein [Retry](#retry)-Mechanismus zum Einsatz.

Bei Fehlern in anderen Verarbeitungsschritten innerhalb der unterstützenden Frameworks wird die Message in die 
für den Consumer konfigurierte [DLQ Stage 1](#dlq-stage-1) verschoben. 

Kommt es zum Fehlerfall innerhalb des Message-Consumers selbst (z.B. auch beim Aufruf eines externen Systems), sollte ein try-catch-Block 
erwartete sowie unerwartete Exceptions auf oberster Ebene fangen. 
Fängt man die Exception nicht und läßt man den Consumer sie weiterwerfen, wird die Message nach den konfigurierten Retry-Versuchen in die DLQ verschoben 
(das besorgt spring-cloud-stream bei der Einstellung _enableDlq=true_ für uns).

Tritt ein Fehler auf, muss der Service entscheiden, ob es sich um einen fachlichen oder technischen Fehler handelt, 
bzw. ob bei der Prozessinstanz ein BPMN-Error oder ein Incident ausgelöst werden soll. 
Zum Unterschied zwischen BPMN-Error und Incident 
siehe [business-error-vs-technical-error](https://docs.camunda.io/docs/components/modeler/bpmn/error-events/#business-error-vs-technical-error).

Nach der Datenaufbereitung sendet der Service eine Message entweder in das BPMN-Error-Topic (siehe [BPMN-Error-Verarbeitung](#bpmn-error-verarbeitung)) 
oder das Incident-Topic des Connectors (siehe [Incident Verarbeitung](#incident-verarbeitung)).


## Retry

Für alle Message-Consumer ist ein Retry-Handler konfiguriert, der bei Fehlern die Verarbeitung wiederholt anstößt, um kurzfristig bestehende Probleme 
wie z.B. Netzwerkverbindungsprobleme zu umgehen.
Standardmäßig werden alle Nachrichten dreimal verarbeitet, bevor sie an die DLQ gesendet werden.
Der Standard kann aber auch umkonfiguriert werden:
```
spring.cloud.stream.bindings.<binding-name>.consumer.maxAttempts
```


## Incident-Verarbeitung

Im Connector empfängt ein Consumer Nachrichten aus dem Incident-Topic und erstellt einen Incident für die entspr. Prozessinstanz.
Dazu ermittelt er aus der Engine alle Activities, die das Event aus dem Message-Header _messageName_ abonniert haben (Event-Subscription) 
und erstellt auf dieser Activity einen Incident mit dem Typ _integrationError_.

Kann der Incident aufgrund irgendeines auftretenden Fehlers nicht erstellt werden, wird die Message in die DLQ des Connectors weitergeleitet.


## BPMN-Error-Verarbeitung

Im Connector empfängt ein Consumer Nachrichten aus dem BPMN-Error-Topic und korreliert einen BPMN-Error mit der Prozessinstanz.

Der von dem BPMN-Hauptprozess eingebundene Integrationsbaustein enthält das Streaming-Template, welches über ein eigenes, konfigurierbares Receive-Event
für BPMN Errors verfügt. Korreliert man eine Message mit diesem Event, kann im nächsten Schritt des Streaming-Templates der ServiceTask _Throw BPMN Error_ 
mit der darin referenzierten Delegate-Klasse einen BPMN-Error mit dynamischem _errorCode_ und _errorMessage_ erzeugen.

Eine dynamische Erzeugung ohne Delegate allein mit BPMN-Mitteln ist nicht möglich.

Die Engine ist über die Einstellung
```
enableExceptionsAfterUnhandledBpmnError=true
```
so konfiguriert, dass BPMN-Errors, für die kein Catch-Event definiert ist, automatisch einen Incident in der Prozessinstanz auslösen.
Damit ist gesichert, dass bei einem Fehler die Instanz nicht unkontrolliert weiterläuft. 

Kann der BPMN-Error aufgrund irgendeines auftretenden Fehlers nicht erstellt werden, wird die Message in die DLQ des Connectors weitergeleitet.


## DLQ Stage 1

Für den Dead-Letter-Queue-Mechanismus werden zwei Stages des Topics benötigt, um eine Endlosschleife beim Publishing zu verhindern.
Würde beim Konsumieren einer Message aus der DLQ ein Fehler auftreten, wäre die Konsequenz, dass die Message wieder zurück in die Eingangs-DLQ 
verschoben und sofort erneut konsumiert würde.

Stage 1 der DLQ ist ausschliesslich für Messages, die automatisiert zu einem Incident verarbeitet werden sollen.

Ein Listener konsumiert dort eintreffende Messages und versucht über die Header-Informationen, in denen eine Prozessinstanz-Id enthalten sein sollte, 
die [Incident Verarbeitung](#incident-verarbeitung) anzustoßen. Schlägt diese Möglichkeit fehl, wird die Message an die [DLQ Stage 2](#dlq-stage-2) weitergeleitet.


## DLQ Stage 2

Die Stage 2 des DLQ ist ausschliesslich Ziel für Messages, die nicht automatisiert zu einem Incident verarbeitet werden konnten. 

Alle Nachrichten in den Dead-Letter-Queues müssen manuell analysiert werden, um festzustellen, um welches Problem es sich handelt 
und wie man dies heilen kann bzw. ob es auf Prozessinstanzseite einen Incident erfordert. 
Informationen dazu bieten zusätzliche Headereinträge in der Message. Ist die Problembehebung durch Anpassung einer fehlerhaften Konfiguration 
oder durch das Verfügbarmachen einer Netzwerkressource erfolgt, kann die Originalnachricht wieder in das entspr. 
Eingangs-Topic verschoben werden und somit der regulären Verarbeitung zugeführt werden.
Auf diese Weise nicht behebbare Fehler müssen zu einem Incident in der Prozessinstanz führen.

Um nicht verarbeitbare Messages schnell und zuverlässig erkennen zu können, ist die DLQ mit einem Monitoring und Alerting zu überwachen.

Einen eigenen Listener für die DLQ zu definieren, um automatisiert Incidents für betroffenen Prozessinstanzen zu erstellen ist kritisch, 
da bei unerwarteten Fehlern in der Verarbeitung die Nachricht zurück in die DLQ geschrieben wird und somit die Gefahr einer Endlosschleife besteht.  


## Konfiguration

Um den IncidentService und den BpmnErrorService aus den streaming-utils verwenden zu können, sind für beide jeweils die Producer-Functions 
und die Zieltopics sowie die DLQ zu konfigurieren:
```
spring.cloud.function.definition=...
spring.cloud.stream.default.consumer.maxAttempts=3
spring.cloud.stream.kafka.default.consumer.dlqName=<DLQ-NAME>
spring.cloud.stream.kafka.default.consumer.enableDlq=true
```
