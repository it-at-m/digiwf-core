# DigiWF Ticket-Integration

![](https://img.shields.io/badge/Integration_Name-ticketIntegration-informational?style=flat&logoColor=white&color=2c73d2)

Die DigiWF Ticket-Integration ermöglicht es Prozessentwicklern, mit einem Ticket-System zu interagieren. Momentan steht
ein Adapter für Zammad zur Verfügung.

## Verwendung

Für die Verwendung der DigiWF Ticket-Integration stehen mehrere Funktionalitäten zur Verfügung:

- Artikel erstellen
- Artikel mit Benutzer erstellen

### Artikel erstellen

Um einen Artikel zu erstellen, müssen Sie die folgenden Daten an die Ticket-Integration übergeben:

```json
{
  "type": "writeArticle",
  "integration": "ticketIntegration",
  "fileContext": "fileContext",
  "ticketId": "1234",
  "article": "Some text",
  "status": "OPEN",
  "filepaths": "path/to/folder/,path/to/file2"
}
```

Um die Prozessentwicklung zu beschleunigen kann das
Element-Template [Ticketing: Artikel schreiben](/element-template/ticket-integration/ticketing_write_article.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                                                                   | Erforderlich |
|---------------|--------|----------------------------------------------------------------------------------------------------------------|--------------|
| `type`        | String | Der Typ des Events. In diesem Fall `writeArticle`.                                                             | Ja           |
| `integration` | String | Der Name der Integration. In diesem Fall `ticketIntegration`.                                                  | Ja           |
| `fileContext` | String | Der Datei-Kontext in welchem die Dateien und Ordner aus `filepaths` liegen.                                    | Ja           |
| `ticketId`    | String | Die ID des Tickets, zu dem der Artikel hinzugefügt werden soll.                                                | Ja           |
| `article`     | String | Der Text des Artikels.                                                                                         | Ja           |
| `status`      | String | Der Status, den das Ticket annehmen soll.                                                                      | Nein         |
| `filepaths`   | String | Eine Kommaseparierte Liste von Datei- und Ordnerpfaden aus dem S3 Bucket. Ordner müssen mit einem Slash enden. | Nein         |

### Artikel mit Benutzer erstellen

Um einen Artikel mit einem Benutzer zu erstellen, müssen Sie die folgenden Daten an die Ticket-Integration übergeben:

```json
{
  "type": "writeArticleWithUser",
  "integration": "ticketIntegration",
  "fileContext": "fileContext",
  "ticketId": "1234",
  "article": "Some text",
  "userId": "1234",
  "status": "OPEN",
  "filepaths": "path/to/folder/,path/to/file2"
}
```

Um die Prozessentwicklung zu beschleunigen, kann das Element-Template
[Ticketing: Artikel mit Benutzer schreiben](/element-template/ticket-integration/ticketing_write_article_with_user.json)
verwendet werden.

#### Felder

Es werden die gleichen Felder wie bei `writeArticle` benötigt, zusätzlich wird die `userId` benötigt.

| Feld     | Typ    | Beschreibung                                                      | Erforderlich |
|----------|--------|-------------------------------------------------------------------|--------------|
| `userId` | String | Die ID des Benutzers, zu dem der Artikel hinzugefügt werden soll. | Nein         |
