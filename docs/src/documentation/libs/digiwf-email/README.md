# DigiWF Email

Mit der **DigiWF E-Mail** Bibliothek wird der E-Mail Versand mit Spring Mail an einer zentralen Stelle in DigiWF implementiert.

## Verwendung

Die **DigiWF E-Mail** Bibliothek stellt die `EmailApi` bereit, die verwendet wird, um E-Mails zu versenden.
Die `EmailApi` stellt mehrere Methoden zum E-Mail Versand bereit, denen jeweils ein `Mail` Objekt übergeben wird.

```java
@Autowired
DigiwfEmailApi digiwfEmailApi;

Mail mail = Mail.builder()
        .receivers("receiver1@example.com,receiver2@example.com")
        .subject("Subject")
        .body("E-Mail Body")
        .build();
digiwfEmailApi.sendMail(mail);
```

Im `Mail` Objekt müssen die Empfänger, der Betreff und der Inhalt der E-Mail angegeben werden.
Zusätzlich können die Absender, CC-Empfänger, BCC-Empfänger sowie eine Reply-To Addresse angegeben werden.

**E-Mails mit Anhängen**

Um Anhänge per Email zu versenden, kann dem `Mail` Objekt eine Liste von `FileAttachment` Objekten hinzugefügt werden.
Diese bestehen aus einem Dateinamen und einem `ByteArrayDataSource`, der den Inhalt der Datei enthält.

## Konfiguration

Die E-Mail Konfiguration erlaubt es einen default Wert für den Absender der E-Mails zu setzen mit der Property `io.muenchendigital.digiwf.mail.fromAddress`.
Alle weiteren Konfiguration werden direkt über die Spring Mail Konfiguration vorgenommen.

```yaml
# digiwf-email config
io:
  muenchendigital:
    digiwf:
      mail:
        fromAddress: "digiwf@muenchen.de"
# spring mail config
spring:
  mail:
  port: ${MAIL_PORT}
  host: ${MAIL_HOST}
  username: ${MAIL_USERNAME}
  password: ${MAIL_PASSWORD}
  properties:
    mail:
      debug: false
      tls: true
      transport:
        protocol: smtp
      smtp:
        port: ${MAIL_PORT}
        host: ${MAIL_HOST}
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
```
