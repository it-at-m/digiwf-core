# DigiWF Ticket Integration

![](https://img.shields.io/badge/Integration_Name-ticketIntegration-informational?style=flat&logoColor=white&color=2c73d2)

Die DigiWF Ticket Integration ermöglicht es Prozessentwicklern, mit einem Ticket-System zu interagieren. Momentan steht
ein Adapter für Zammad zur Verfügung.

## Verwendung

Für die Verwendung der DigiWF Ticket Integration stehen mehrere Funktionalitäten zur Verfügung.

- Artikel erstellen
- Artikel mit Benutzer erstellen

### Artikel erstellen

Um einen Artikel zu erstellen, müssen Sie die folgenden Daten an die Ticket Integration übergeben:

```json
{
  "type": "writeArticle",
  "integration": "ticketIntegration",
  "ticketId": "1234",
  "article": "Some text",
  "status": "OPEN",
  "filepaths": "path/to/file1;path/to/file2"
}
```

Um die Prozessentwicklung zu beschleunigen kann das
Element-Template [Ticketing: Artikel schreiben](/element-template/ticket-integration/ticketing_write_article.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                    | Erforderlich |
|---------------|--------|-----------------------------------------------------------------|--------------|
| `type`        | String | Der Typ des Events. In diesem Fall `writeArticle`.              | Ja           |
| `integration` | String | Der Name der Integration. In diesem Fall `ticketIntegration`.   | Ja           |
| `ticketId`    | String | Die ID des Tickets, zu dem der Artikel hinzugefügt werden soll. | Ja           |
| `article`     | String | Der Text des Artikels.                                          | Ja           |
| `status`      | String | Der Status, den das Ticket annehmen soll                        | Nein         |
| `filepaths`   | Array  | Eine Kommaseparierte Liste von Dateipfaden aus dem S3 Bucket.   | Nein         |

### Artikel mit Benutzer erstellen

Um einen Artikel mit einem Benutzer zu erstellen, müssen Sie die folgenden Daten an die Ticket Integration übergeben:

```json
{
  "type": "writeArticleWithUser",
  "integration": "ticketIntegration",
  "ticketId": "1234",
  "article": "Some text",
  "userId": "1234",
  "status": "OPEN",
  "filepaths": "path/to/file1;path/to/file2"
}
```

Um die Prozessentwicklung zu beschleunigen kann das
Element-Template [Ticketing: Artikel mit Benutzer schreiben](/element-template/ticket-integration/ticketing_write_article_with_user.json)
verwendet werden.

#### Felder

Es werden die gleichen Felder wie bei `writeArticle` benötigt, zusätzlich wird die `userId` benötigt.

| Feld     | Typ    | Beschreibung                                                      | Erforderlich |
|----------|--------|-------------------------------------------------------------------|--------------|
| `userId` | String | Die ID des Benutzers, zu dem der Artikel hinzugefügt werden soll. | Nein         |

## Konfigurationen

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF Ticket Integration verwenden:

- **DIGIWF_ENV** - The environment of DigiWF, e.g. `local-01`, `dev`, `test`, `demo`.
- **SSO_TICKET_CLIENT_ID** - The client id of the ticket service.
- **SSO_TICKET_CLIENT_SECRET** - The client secret of the ticket service.
- **TICKETING_ZAMMAD_URL** - The URL of the ticket service (e.g. zammad).
- **ENGINE_REST_ENDPOINT_URL** - The URL of the engine server.
- **DOCUMENT_STORAGE_HOST** - The host of the document storage service.
- **DOCUMENT_STORAGE_PORT** - The port of the document storage service.
- **SSO_ISSUER_URL** - The URL of the SSO issuer.
- **SSO_BASE_URL** - The base URL of the SSO service.
- **SSO_REALM** - The realm of the SSO service.
- **ZAMMAD_SSO_ISSUER_URL** - The URL of the SSO issuer for the ticket service.
- **ZAMMAD_SSO_BASE_URL** - The base URL of the SSO service for the ticket service.
- **ZAMMAD_SSO_REALM** - The realm of the SSO service for the ticket service.
- **SSO_S3_CLIENT_ID** - The client id of the S3 service.
- **SSO_S3_CLIENT_SECRET** - The client secret of the S3 service.
- **TICKETING_INTEGRATION_SERVER_PORT** - The port of the ticket integration server.
