# DigiWF Mail Integration

Die DigiWF Email Integration ist eine Integration, die es Benutzern ermöglicht, Emails über die DigiWF-Plattform zu
senden.
Mit dieser Integration können Prozessentwickler Email-Kommunikation in ihre BPMN Prozesse integrieren.

Die Email Integration erlaubt sowohl einfache Emails als auch Emails mit Dateianhängen zu versenden.
Dateianhänge können hierbei aus einem S3 Bucket geladen werden.

## Verwendung

Die Email Integration erlaubt es den User von DigiWF Emails aus einem Prozess heraus zusenden.
Hierbei können sowohl einfache Emails als auch Emails mit Dateianhängen versendet werden.
Die Dateianhänge werden hierbei aus einem S3 Bucket geladen.
Zusätzlich kann direkt im Prozess auf untenstehende Fehler reagiert werden.

### Email senden

Um eine Email mit der Email Integration zu senden, müssen Sie ein Email Event an das Kafka Topic der Email Integration
senden.
Den Namen des Topics können Sie in der Konfiguration der Email Integration
unter `spring.cloud.stream.bindings.functionRouter-in-0.destination` finden.
Zusätzlich muss im Email Event der Header `type` auf `sendMailFromEventBus` gesetzt werden.

> Standardmäßig heißen die Topics *dwf-email-${DIGIWF_ENV}*, wobei DIGIWF_ENV die aktuelle Umgebung ist.

Nachfolgend ist ein Beispiel Event für eine Email aufgeführt:

```json
{
  "receivers": "receivers@example.com",
  "receiversCc": "receivers-on-cc@example.com",
  "receiversBcc": "receivers-on-bcc@example.com",
  "subject": "My important email",
  "body": "Some text I want to send",
  "replyTo": "replyto@example.com",
  "attachments": []
}
```

### Email mit Dateianhang senden

Sie können Dateien aus einem S3 Bucket an die Emails anhängen, die Sie senden.
Dafür müssen Sie vorab presigned URLs für die Dateianhänge mit der S3 Integration erstellen und diese
unter `attachments` dem Mail Event mitgeben.
Die Email Integration lädt die Dateien herunter und fügt sie der Email vor dem Versenden als Anhang hinzu.

**Hinweis**: Die Email Integration unterstützt nur presigned URLs, die mit der GET-Aktion erstellt wurden.
Alle anderen Dateiaktionen funktionieren nicht und führen zu einem Fehler.

Nachfolgend ist ein Beispiel Event für eine Email mit einem Dateianhang aufgeführt:

```json
{
  "receivers": "receivers@example.com",
  "receiversCc": "receivers-on-cc@example.com",
  "receiversBcc": "receivers-on-bcc@example.com",
  "subject": "My important email",
  "body": "Some text I want to send",
  "replyTo": "replyto@example.com",
  "attachments": [
    {
      "url": "http://localhost:9000/s3-bucket/some/path/to/file/image.png",
      "path": "path/to/file/in/s3",
      "action": "GET"
    }
  ]
}
```

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN Errors und Incident Errors unterschieden.
BPMN Errors können im Prozess behandelt werden, während Incident Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen.

Nachfolgend sind die BPMN Errors aufgeführt, die von der Email Integration geworfen werden können:

#### BPMN Error

| Error Code                | Error Message                                                    | Beschreibung                                                                                                                               | Handlungsempfehlung                                                                                                            | 
|---------------------------|------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| `VALIDATION_ERROR`        | Fehlermeldung der auftretenden `ValidationException`             | Die übergebenen Email Daten sind nicht valide.                                                                                             | Korrigieren Sie die Daten und versuchen es erneut                                                                              |
| `MESSAGING_EXCEPTION`     | Fehlermeldung der auftretenden `MessagingException`              | Die Email konnte mit den übergebenen Daten nicht erstellt werden.                                                                          | Überprüfen Sie, ob die Daten valide sind und versuchen es erneut.                                                              | 
| `MAIL_SENDING_FAILED`     | Fehlermeldung der auftretenden `MailException`                   | Die Email konnte nicht versand werden. Es kann sein, dass die Email Adressen nicht valide sind oder ein technischer Fehler aufgetreten ist | Analysieren Sie die Fehlermeldung, korrigieren invalide Email Adressen und versuchen es erneut.                                |
| `LOAD_FILE_FAILED`        | An attachment could not be loaded from presigned url: attachment | Die Datei konnte nicht geladen werden                                                                                                      | Stellen Sie sicher, dass die presigned Url nicht abgelaufen ist. Stellen Sie sicher, dass die Datei im S3 Bucket vorhanden ist |
| `FILE_TYPE_NOT_SUPPORTED` | File type not supported of the attachment: attachment            | Der Dateityp der Datei wird nicht unterstützt oder wurde nicht erkannt                                                                     | Die Datei kann nicht als Email Anhang versendet werden.                                                                        | 

### Ressourcen

Um die Prozessentwicklung zu beschleunigen, können Sie die
Element-Templates [sendMail.json](/element-template/sendMail.json)
in einer Call Activity verwenden, um diese Integration zu verwenden.

## DigiWF Mail Integration anpassen

Die DigiWF Mail Integration wird als Spring Boot Starter Projekt bereitgestellt.
Um die Email Integration an Ihre Bedürfnisse anzupassen, können Sie das Starter-Modul verwenden und die
bereitgestellten `@bean`s überschreiben sowie eigene `@bean`s hinzufügen.

![Mail Architecture](~@source/images/platform/integrations/mail/architecture.png)

Den `digiwf-email-integration-starter` können Sie wie folgt in Ihr Projekt einbinden:

**Mit maven**

```xml

<dependency>
    <groupId>io.muenchendigital.digiwf</groupId>
    <artifactId>digiwf-email-integration-starter</artifactId>
    <version>${digiwf.version}</version>
</dependency>
```

**Mit gradle**

```gradle
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-email-integration-starter', version: '${digiwf.version}'
```

Machen Sie sich mit
dem [`digiwf-email-integration-core`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-email-integration/digiwf-email-integration-core)
und [`digiwf-email-integration-starter`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-email-integration/digiwf-email-integration-starter)
Modul vertraut und fügen Sie Ihre eigenen `@bean`s hinzu oder überschreiben Sie die bereitgestellten `@bean`s.

## Konfigurationen

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF Mail Integration verwenden:

|                                                               |                                                                                                                |
|---------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| `io.muenchendigital.digiwf.mail.fromAddress`                  | Die Absenderadresse, die für alle Emails verwendet wird, die über die DigiWF Mail Integration gesendet werden. |
| `io.muenchendigital.digiwf.mail.metrics.totalMailCounterName` | Der Name des Micrometer Counters, der die Anzahl der gesendeten Emails zählt.                                  |
| `io.muenchendigital.digiwf.mail.metrics.failureCounterName`   | Der Name des Micrometer Counters, der die Anzahl der fehlgeschlagenen Emails zählt.                            |

```yaml
# Konfigurationen für die DigiWF Mail Integration
io:
  muenchendigital:
    digiwf:
      mail:
        fromAddress: ${MAIL_USERNAME:digiwf@muenchen.de}
        metrics:
          totalMailCounterName: "digiwf.email.integration.send_mail.total"
          failureCounterName: "digiwf.email.integration.send_mail.failure"
# allgemeine Konfigurationen für Spring Mail
spring:
  mail:
  port: ${MAIL_PORT:1025}
  host: ${MAIL_HOST:localhost}
  username: ${MAIL_USERNAME:digiwf@muenchen.de}
  password: ${MAIL_PASSWORD:test}
  properties:
    mail:
      debug: false
      tls: true
      transport:
        protocol: smtp
      smtp:
        port: ${MAIL_PORT:1025}
        host: ${MAIL_HOST:localhost}
        connectiontimeout: '10000'
        timeout: '10000'
        auth: true
        ssl:
          trust: '*'
          checkserveridentity: false
        socketFactory:
          fallback: true
        starttls:
          enable: true
# Management Konfigurationen für Spring Actuator und Prometheus (Micrometer) Metriken
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```
