# Integrationen

Integrationen können über eine Call Activity aufgerufen werden. Für die von der Plattform bereitgestellten Integrationen werden Element-Templates angeboten, die die Modellierung vereinfachen.
Eine vollständige Liste der verfügbaren Integrationen finden Sie unter [DigiWF Integrationen](/integrations/).


## CoSys

Um ein Dokument über CoSys zu erstellen, muss eine Call Activity modelliert werden. Es stehen verschiedene
CoSys-Templates zur Verfügung.

### CoSys: Dokument erstellen (alle Daten) (V02)

Es werden alle Daten der Prozessinstanz an CoSys übergeben.

**Properties**

| Property                   | Beschreibung                                                                            | Beispiel                             |
|----------------------------|-----------------------------------------------------------------------------------------|--------------------------------------|
| Event Topic                | Das Topic der CoSys-Integration                                                         | dwf-cosys-demo                       |
| Client                     | Der Name des CoSys-Clients                                                              | 9001                                 |
| Role                       | Der Name der CoSys-Role                                                                 | TESTER, SB                           |
| Document Storage Urls (S3) | Zuvor generierte signierte URLs des S3-Service für die Ablage des generierten Dokumtens | ${presignedUrls}                     |
| GUID                       | Eindeutiger Bezeichner der CoSys-Vorlage                                                | 519650b7-87c2-41a6-8527-7b095675b13f |
| Daten                      | Daten als JSON-Objekt, die in das Dokument eingemischt werden sollten                   | ${data}                              |

### CoSys: Dokument erstellen

Die Daten, die an CoSys übergeben werden, müssen zuvor definiert werden.

**Properties**

| Property                   | Beschreibung                                                                            | Beispiel                             |
|----------------------------|-----------------------------------------------------------------------------------------|--------------------------------------|
| Event Topic                | Das Topic der CoSys-Integration                                                         | dwf-cosys-demo                       |
| Client                     | Der Name des CoSys-Clients                                                              | 9001                                 |
| Role                       | Der Name der CoSys-Role                                                                 | TESTER                               |
| Document Storage Urls (S3) | Zuvor generierte signierte URLs des S3-Service für die Ablage des generierten Dokumtens | ${presignedUrls}                     |
| GUID                       | Eindeutiger Bezeichner der CoSys-Vorlage                                                | 519650b7-87c2-41a6-8527-7b095675b13f |
| Daten                      | Daten als JSON-Objekt, die in das Dokument eingemischt werden sollten                   | ${data}                              |

## Email

Um eine E-Mail zu versenden, muss eine Call Activity modelliert werden, die ein `sendMail` Element Template verwendet.

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Event Topic           | Das Topic der E-Mail-Integration                                                   | dwf-email-local-01         |
| Receiver              | E-Mail-Adressen der Empfänger (kommasepariert)                                     | max.mustermann@example.com |
| Subject               | Betreff                                                                            | Testemail                  |
| Body                  | E-Mail-Text                                                                        | Das ist ein Test           |
| Reply-To Address      | E-Mail-Adresse, an die geantwortet werden soll                                     | test@example.com           |
| Receiver (CC)         | Empfänger CC (kommasepariert)                                                      | max.mustermann@example.com |
| Receiver (BCC)        | Empfänger BCC (kommasepariert)                                                     | max.mustermann@example.com |
| Attachment Paths (S3) | Von der S3-Integration generierte Presigned-URLs für das Herunterladen von Dateien |                            |

## S3

Für die Interaktion mit dem S3-Dienst stehen verschiedene Templates zur Verfügung.

### S3: Presigned URL erstellen

**Properties**

| Property           | Beschreibung                                                   | Beispiel                 |
|--------------------|----------------------------------------------------------------|--------------------------|
| Dateipfad          | Der Pfad, der ausgelesen werden soll                           | ${app_file_context}/docs |
| Dateiaktion        | Die Aktion, die auf der Datei ausgeführt werden soll           | GET                      |
| Out: PresignedUrls | Der Name des JSON-Arrays, in den das Ergebnis geschrieben wird | urls                     |