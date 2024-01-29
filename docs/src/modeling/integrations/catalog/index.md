# Katalog

Auf dieser Seite sind alle Template zu finden, die in DigiWF für die Modellierung verfügbar sind.

## CoSys

Um ein Dokument über Cosys zu erstellen, muss eine CallActivity modelliert werden.
Es stehen verschiedene Cosys Templates zur Verfügung.

### CoSys: Dokument erstellen (alle Daten) (V02)

Es werden alle Daten der Prozessinstanz an CoSys übergeben

**Properties**

| Property                   | Beschreibung                                                                            | Beispiel                             |
|----------------------------|-----------------------------------------------------------------------------------------|--------------------------------------|
| Event Topic                | Das Topic der Cosys Integration                                                         | dwf-cosys-demo                       |
| Client                     | Der Name des Cosys Client                                                               | 9001                                 |
| Role                       | Der Name der Cosys Role                                                                 | TESTER, SB                           |
| Document Storage Urls (S3) | Zuvor generierte signierte Urls des S3-Service für die Ablage des generierten Dokumtens | ${presignedUrls}                     |
| GUID                       | Eindeutiger Bezeichner der Cosys-Vorlage                                                | 519650b7-87c2-41a6-8527-7b095675b13f |
| Daten                      | Daten als JSON Objekt, die in das Dokument eingemischt werden sollten                   | ${data}                              |

### CoSys: Dokument erstellen

Die Daten, die an CoSys übergeben werden, müssen zuvor definiert werden.

**Properties**

| Property                   | Beschreibung                                                                            | Beispiel                             |
|----------------------------|-----------------------------------------------------------------------------------------|--------------------------------------|
| Event Topic                | Das Topic der Cosys Integration                                                         | dwf-cosys-demo                       |
| Client                     | Der Name des Cosys Client                                                               | 9001                                 |
| Role                       | Der Name der Cosys Role                                                                 | TESTER                               |
| Document Storage Urls (S3) | Zuvor generierte signierte Urls des S3-Service für die Ablage des generierten Dokumtens | ${presignedUrls}                     |
| GUID                       | Eindeutiger Bezeichner der Cosys-Vorlage                                                | 519650b7-87c2-41a6-8527-7b095675b13f |
| Daten                      | Daten als JSON Objekt, die in das Dokument eingemischt werden sollten                   | ${data}                              |

## Email

Um eine Email zu versenden, muss eine CallActivity modelliert werden, die ein `sendMail` Element Template verwendet.

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Event Topic           | Das Topic der Email Integration                                                    | dwf-email-local-01         |
| Receiver              | Email Adressen der Empfänger (Kommasepariert)                                      | max.mustermann@example.com |
| Subject               | Betreff                                                                            | Testemail                  |
| Body                  | Email Text                                                                         | Das ist ein Test           |
| Reply-To Address      | Email Adresse, an die geantwortet werden soll                                      | test@example.com           |
| Receiver (CC)         | Empfänger CC (Kommasepariert)                                                      | max.mustermann@example.com |
| Receiver (BCC)        | Empfänger BCC (Kommasepariert)                                                     | max.mustermann@example.com |
| Attachment Paths (S3) | Von der S3 Integration generierte Presigned Urls für das herunterladen von Dateien |                            |

## S3

Für die Interaktion mit dem S3 Dienst stehen verschiedene Templates zur Verfügung

### S3: Presigned Url erstellen

**Properties**

| Property           | Beschreibung                                                   | Beispiel                 |
|--------------------|----------------------------------------------------------------|--------------------------|
| Dateipfad          | Der Pfad, der ausgelsen werden soll                            | ${app_file_context}/docs |
| Datei Aktion       | Die Aktion, die auf der Datei ausgeführt werden könne soll     | GET                      |
| Out: PresignedUrls | der Name des Json Arrays, in den das Ergebnis geschrieben wird | urls                     |

