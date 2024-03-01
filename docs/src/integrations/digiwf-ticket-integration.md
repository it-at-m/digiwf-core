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
  "status": "OPEN"
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

### Artikel mit Benutzer erstellen

Um einen Artikel mit einem Benutzer zu erstellen, müssen Sie die folgenden Daten an die Ticket Integration übergeben:

```json
{
  "type": "writeArticleWithUser",
  "integration": "ticketIntegration",
  "ticketId": "1234",
  "article": "Some text",
  "userId": "1234",
  "status": "OPEN"
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

| Variable                            | Beschreibung                                                          | Erforderlich |
|-------------------------------------|-----------------------------------------------------------------------|--------------|
| `SSO_TICKET_CLIENT_ID`              | Die Id des Clients, der für die Authentifizierung verwendet wird.     | Ja           |
| `SSO_TICKET_CLIENT_SECRET`          | Das Secret des Clients, der für die Authentifizierung verwendet wird. | Ja           |
| `TICKETING_ZAMMAD_URL`              | Die URL des Zammad-Servers.                                           | Ja           |
| `TICKETING_INTEGRATION_SERVER_PORT` | Der Port der Ticketing Integration                                    | Nein         |
