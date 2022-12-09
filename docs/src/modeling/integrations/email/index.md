# Email

Um eine Email zu versenden, muss eine Callaktivity modelliert werden, die ein `sendMail` Element Template verwendet.

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Event Topic           | Das Topic der Email Integration                                                    | dwf-email-local-01         |
| Receiver              | Email Adresse des Empfängers                                                       | max.mustermann@example.com |
| Subject               | Betreff                                                                            | Testemail                  |
| Body                  | Email Text                                                                         | Das ist ein Test           |
| Reply-To Address      | Email Adresse, an die geantwortet werden soll                                      | test@example.com           |
| Receiver (CC)         | Empfänger CC                                                                       | max.mustermann@example.com |
| Receiver (BCC)        | Empfänger BCC                                                                      | max.mustermann@example.com |
| Attachment Paths (S3) | Von der S3 Integration generierte Presigned Urls für das herunterladen von Dateien |                            |

## Email senden

![email-integration](~@source/images/platform/modeling/integrations/email-integration.png)

## Email senden mit Dateianhängen

tbd.
