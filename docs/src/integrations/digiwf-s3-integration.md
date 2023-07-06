# DigiWF S3 Integration

Die DigiWF S3 Integration ermöglicht Dateianhänge in Prozessen zu verwenden. Mit dieser Integration können Prozessentwickler
Formulare entwickeln, in den Dateien hoch- und herunterladen werden können. Auch weitere Services können Dateien erzeugen und 
diese integriert über einen Prozess in einer Dateiablage ablegen, sodass andere Services diese wieder verwenden können.
Ein gutes Beispiel hierfür ist die Erzeugung eines PDF Dokuments und Versand dieses über E-Mail.

Der entsprechende Service bietet diese Funktionalität als Integration (also asynchron via Messaging) an und via REST 
(also für die synchrone Verwendung).

## Verwendung

Die Bibliothek bietet mehrere Funktionalitäten an. Wir haben einige Beispiele zusammengestellt, um die Verwendung zu demonstrieren:

_Diese sind im Ordner [example-s3-integration-client](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-s3-integration/digiwf-s3-integration-client-example)
zu finden._

### Synchrone Verwendung via REST

Um die Funktionalität **synchron** zu nutzen wurde ein spezieller Client aufgebaut. Jede Methode des Clients kommuniziert direkt
mit der entsprechenden Methode des S3 Integration Dienstes. Der Client kann in zwei unterschiedlichen
Modi betrieben: 
 - Die URL des S3 Integration Dienstes wird aus einem Parameter (property) ausgelesen.
 - Die URL des S3 Integration Dienstes wird in die jeweilige Methode durch den Aufrufer übergeben. Damit können mehrere Dienste 
   aus demselben Client aufgerufen werden.

#### Einbindung

Der spezielle Client kann in die Anwendung ganz einfach angebunden werden.
Dazu muss die `digiwf-s3-integration-client-starter` Abhängigkeit hinzugefügt werden:

Mit Maven:
```xml
<dependency>
     <groupId>io.muenchendigital.digiwf</groupId>
     <artifactId>digiwf-s3-integration-client-starter</artifactId>
     <version>${digiwf.version}</version>
</dependency>
```

Mit Gradle:
```groovy
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-s3-integration-client-starter', version: '${digiwf.version}'
```

Und der Wert der Eigenschaft `io.muenchendigital.digiwf.s3.client.document-storage-url` auf die Lokation des S3 Integration Dienstes zeigen.


### Asynchrone Verwendung via Messaging

Neben der synchronen Verwendung kann die S3 Integration auch im Rahmen einer **asynchronen nachrichten-basierten** Anbindung
verwendet werden. Dabei werden per Nachricht eine Anfrage für die Erstellung der vor-signierten URL für Dateizugriff versandt 
und als Korrelationsnachricht dazu eine Antwort empfangen. Die vor-signierte URLs sind dann in der Regel 7 Tage gültig.
(Die Eigenschaft im Dienst `io.muenchendigital.digiwf.s3.presignedUrlExpiresInMinutes` steuert diesen Zeitraum, 7 Tage = 10080 min)

Um eine vor-signierte URL für den Zugriff auf eine Datei oder ein Verzeichnis zu bekommen muss die folgende 
Nachricht `CreatePresignedUrlEvent` versandt werden: 

```json
{
  "action": "GET",
  "path": "/path/to/your/file/or/directory"
}
```

Valide Werte für das Feld `action` sind `GET`, `POST`, `PUT` und `DELETE`. Als Antwort in Form einer korrelierten Nachricht 
werden vor-signierte URLs im folgenden Format versendet:

```json
{
  "presignedUrls": [
    {
      "url": "the-presigned-url",
      "path": "/path/to/your/file/or/directory",
      "action": "GET"
    }
  ]
}
```
Diese URLs können direkt benutzt werden oder an andere Integrationen weitergegeben werden, um auf die Dateien zuzugreifen.


## Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN Errors und Incident Errors unterschieden.
BPMN Errors können im Prozess behandelt werden, während Incident Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen. Dieser deutet auf einen durch Benutzer nicht behebbaren Fehler hin, unterbricht die 
Prozessausführung und ist danach im Camunda Cockpit sichtbar.

Nachfolgend sind die BPMN Errors aufgeführt, die von der S3 Integration geworfen werden können:

#### BPMN Error

| Error Code                  | Error Message                                                    | Beschreibung                                                                                                                               | Handlungsempfehlung                                                                                                            | 
|-----------------------------|------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| `VALIDATION_ERROR`          | Fehlermeldung der auftretenden `ValidationException`             | Die übergebenen Anforderungen an Dateioperationen sind nicht valide.                                                                       | Korrigieren Sie die Daten und versuchen es erneut                                                                              |
| `FILE_DOES_NOT_EXIST_ERROR` | Fehlermeldung der auftretenden `FileExistenceException`          | Die übergebenen Anforderungen referenzieren eine nicht existierende Datei oder Ordner.                                                     | Überprüfen Sie, ob die Daten valide sind und versuchen es erneut.                                                              | 

Alle anderen Fehler im Zugriff auf die S3 werden als Incidents reported.

## Weitere Ressourcen

Um die Prozessentwicklung zu beschleunigen, können Sie die
Element-Templates [sendMail.json](/element-template/sendMail.json)
in einer Call Activity verwenden, um diese Integration zu verwenden.

## DigiWF S3 Integration Dienst betreiben und anpassen

Die DigiWF S3 Integration wird als Spring Boot Starter Projekt bereitgestellt.
Um die S3 Integration an Ihre Bedürfnisse anzupassen, können Sie das Starter-Modul verwenden und die
bereitgestellten `@bean`s überschreiben sowie eigene `@bean`s hinzufügen.

Den `digiwf-s3-integration-starter` können Sie wie folgt in Ihr Projekt einbinden

Mit Maven:

```xml
<dependency>
     <groupId>io.muenchendigital.digiwf</groupId>
     <artifactId>digiwf-s3-integration-starter</artifactId>
     <version>${digiwf.version}</version>
</dependency>
```

Mit Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-s3-integration-starter', version: '${digiwf.version}'
```

### Konfiguration

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF S3 Integration verwenden:

| Eigenschaft                                                   | Bedeutung                                                                                              |
|---------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| `io.muenchendigital.digiwf.s3.bucketName`                     | Name des S3 Buckets                                                                                    |
| `io.muenchendigital.digiwf.s3.secretKey`                      | Secret für den Zugriff auf den Bucket                                                                  |
| `io.muenchendigital.digiwf.s3.accessKey`                      | Access Key für den Zugriff auf den Bucket                                                              | 
| `io.muenchendigital.digiwf.s3.url`                            | URL des S3 Servers                                                                                     |
| `io.muenchendigital.digiwf.s3.initialConnectionTest`          | Optionale Eigenschaft ('true', 'false') um den Verbindungstest zu S3 während des Starts durchzuführen. |
| `io.muenchendigital.digiwf.s3.cronjob.cleanup.expired-files`  | Cron Ausdruck um die abgelaufende Dateien abzuräumen (z.B. `0 15 10 15 * ?`)                           |
| `io.muenchendigital.digiwf.s3.cronjob.cleanup.unused-files`   | Cron Ausdruck um die ungenutze Dateien abzuräumen (z.B. `0 15 10 16 * ?`)                              |


### S3 proxy

Wenn der Zugriff auf S3 Speicher nicht direkt möglich ist, kann dieser über einen Proxy versendet werden.
Dazu muss die Verwendung des Proxies aktiviert und die Proxy URL konfiguriert werden.

```yaml
io:
  muenchendigital:
    digiwf:
      s3:
        proxyEnabled: true
        proxyUrl: http://localhost:9000
```

Standardmäßig ist kein Proxy konfiguriert.
Mehr Details zur Arbeit mit Dateien erfahren Sie unter [https://digiwf.muenchendigital.io/resources/documentation/concept/filehandling/](https://digiwf.muenchendigital.io/resources/documentation/concept/filehandling/).