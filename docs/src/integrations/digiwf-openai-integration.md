# DigiWF OpenAI-Integration

![](https://img.shields.io/badge/Integration_Name-ticketIntegration-informational?style=flat&logoColor=white&color=2c73d2)

Die DigiWF OpenAI-Integration ermöglicht es Prozessentwicklern, Sprachmodelle in BPMN-Prozesse integrieren zu können.

## Verwendung

Für die Verwendung der DigiWF OpenAI-Integration stehen mehrere Funktionalitäten zur Verfügung:

- Chat
- Übersetzung
- Zusammenfassung
- Mail generieren
- Daten extrahieren
- Klassifizierung

### Chat

Um eine einfache Chat-Anfrage durchzuführen, müssen die folgenden Daten an die OpenAI-Integration übergeben werden:

```json
{
  "prompt": "This is a question?"
}
```

Um die Prozessentwicklung zu beschleunigen, kann das
Element-Template [OpenAI: Chat](/element-template/openai-integration/openai_chat.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                  | Erforderlich |
|---------------|--------|---------------------------------------------------------------|--------------|
| `type`        | Header | Der Typ des Events. In diesem Fall `basicChat`.               | Ja           |
| `integration` | Header | Der Name der Integration. In diesem Fall `openaiIntegration`. | Ja           |
| `prompt`      | String | Generische Anfrage an das Sprachmodell.                       | Ja           |

### Übersetzung

Um einen Text übersetzen zu lassen, müssen Sie die folgenden Daten an die OpenAI-Integration übergeben werden:

```json
{
  "text": "This is a Text",
  "language": "en"
}
```

Um die Prozessentwicklung zu beschleunigen, kann das
Element-Template [OpenAI: Translate](/element-template/openai-integration/openai_translate.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                  | Erforderlich |
|---------------|--------|---------------------------------------------------------------|--------------|
| `type`        | Header | Der Typ des Events. In diesem Fall `translate`.               | Ja           |
| `integration` | Header | Der Name der Integration. In diesem Fall `openaiIntegration`. | Ja           |
| `text`        | String | Der zu übersetzende Text.                                     | Ja           |
| `language`    | String | Sprache, in die übersetzt werden soll.                        | Ja           |

### Zusammenfassung

Um einen Text zusammenfassen zu lassen, müssen Sie die folgenden Daten an die OpenAI-Integration übergeben werden:

```json
{
  "text": "This is a long text",
  "length": 100
}
```

Um die Prozessentwicklung zu beschleunigen, kann das
Element-Template [OpenAI: Zusammenfassung](/element-template/openai-integration/openai_summarize.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                  | Erforderlich |
|---------------|--------|---------------------------------------------------------------|--------------|
| `type`        | Header | Der Typ des Events. In diesem Fall `basicChat`.               | Ja           |
| `integration` | Header | Der Name der Integration. In diesem Fall `openaiIntegration`. | Ja           |
| `text`        | String | Der zu verkürzende Text.                                      | Ja           |
| `length`      | Int    | Ungefähre Länge des neuen Textes.                             | Ja           |

### Mail generieren

Um Mail-Texte in beliebigen Sprachen generieren zu lassen, müssen Sie die folgenden Daten an die OpenAI-Integration übergeben werden:

```json
{
  "json": "{\"name\":\"Hans\"}",
  "language": "en",
  "template": "Hallo {{name}}, danke für die Anfrage."
}
```

Um die Prozessentwicklung zu beschleunigen, kann das
Element-Template [OpenAI: Mail Generierung](/element-template/openai-integration/openai_generate-mail.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                  | Erforderlich |
|---------------|--------|---------------------------------------------------------------|--------------|
| `type`        | Header | Der Typ des Events. In diesem Fall `basicChat`.               | Ja           |
| `integration` | Header | Der Name der Integration. In diesem Fall `openaiIntegration`. | Ja           |
| `json`        | json   | Daten für das Template.                                       | Ja           |
| `language`    | String | Sprache des Mail-Textes.                                      | Ja           |
| `template`    | String | Vorlage für den Mail-Text.                                    | Ja           |

### Daten extrahieren

Um Daten aus einem Text oder JSON extrahieren zu lassen, müssen Sie die folgenden Daten an die OpenAI-Integration übergeben werden:

```json
{
  "json": "{\"request\":\"I want this in 5 days\"}",
  "fields": "timespan"
}
```

Um die Prozessentwicklung zu beschleunigen, kann das
Element-Template [OpenAI: Daten extrahieren](/element-template/openai-integration/openai_extract-data.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                  | Erforderlich |
|---------------|--------|---------------------------------------------------------------|--------------|
| `type`        | Header | Der Typ des Events. In diesem Fall `basicChat`.               | Ja           |
| `integration` | Header | Der Name der Integration. In diesem Fall `openaiIntegration`. | Ja           |
| `json`        | String | Unstrukturierter Text oder unpassende JSON-Daten.             | Ja           |
| `fields`      | String | Zu extrahierende Informationen als JSON-Felder getrennt.      | Ja           |

### Klassifizierung

Um auf Bassis von Daten einfache (Vor-)Entscheidungen zu treffen, müssen Sie die folgenden Daten an die OpenAI-Integration übergeben werden:

```json
{
  "json": "{\"request\":\"Where is my request xyz?\"}",
  "options": "question, request"
}
```

Um die Prozessentwicklung zu beschleunigen, kann das
Element-Template [OpenAI: Klassifizierung](/element-template/openai-integration/openai_classify.json)
verwendet werden.

#### Felder

| Feld          | Typ    | Beschreibung                                                  | Erforderlich |
|---------------|--------|---------------------------------------------------------------|--------------|
| `type`        | Header | Der Typ des Events. In diesem Fall `basicChat`.               | Ja           |
| `integration` | Header | Der Name der Integration. In diesem Fall `openaiIntegration`. | Ja           |
| `json`        | String | Unstrukturierter Text oder JSON-Daten als Basis.              | Ja           |
| `options`     | String | Mögliche Ergebnisse für die Klassifizierung.                  | Ja           |