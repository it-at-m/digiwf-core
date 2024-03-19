# Integrationen

Integrationen können über eine Call Activity aufgerufen werden. Für die von der Plattform bereitgestellten Integrationen werden Element-Templates angeboten, die die Modellierung vereinfachen.
Eine vollständige Liste der verfügbaren Integrationen finden Sie unter [DigiWF Integrationen](/integrations/).


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

## E-Mail

Um eine E-Mail zu versenden, muss eine CallActivity modelliert werden.
Es stehen verschiedene E-Mail Templates zur Verfügung.

### E-Mail versenden

Um eine einfache E-Mail zu versenden wird das Element Template `Mail: E-Mail senden V02`  verwendet.

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Receiver              | E-Mail Adressen der Empfänger (Kommasepariert)                                      | max.mustermann@example.com |
| Subject               | Betreff                                                                            | Test E-Mail                      |
| Body                  | E-Mail Text                                                                         | Das ist ein Test           |
| Reply-To Address      | E-Mail Adresse, an die geantwortet werden soll                                      | test@example.com           |
| Receiver (CC)         | Empfänger CC (Kommasepariert)                                                      | max.mustermann@example.com |
| Receiver (BCC)        | Empfänger BCC (Kommasepariert)                                                     | max.mustermann@example.com |
| Attachment Paths (S3) | Von der S3 Integration generierte Presigned Urls für das herunterladen von Dateien |                            |


### E-Mail mit Logo versenden

Um eine E-Mail mit Logo zu versenden wird das Element Template `Mail: E-Mail mit Logo senden`  verwendet.

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Receiver              | E-Mail Adressen der Empfänger (Kommasepariert)                                      | max.mustermann@example.com |
| Subject               | Betreff                                                                            | Test E-Mail                |
| E-Mail Text                  | E-Mail Text                                                                         | Das ist ein Test           |
| E-Mail Gruß                  | E-Mail Gruß                                                                         | Mit freundlichen Grüßen    |
| Reply-To Address      | E-Mail Adresse, an die geantwortet werden soll                                      | test@example.com           |
| Receiver (CC)         | Empfänger CC (Kommasepariert)                                                      | max.mustermann@example.com |
| Receiver (BCC)        | Empfänger BCC (Kommasepariert)                                                     | max.mustermann@example.com |
| Attachment Paths (S3) | Von der S3 Integration generierte Presigned Urls für das herunterladen von Dateien |                            |

### E-Mail mit Logo und Link versenden

Um eine E-Mail mit Logo zu versenden wird das Element Template `Mail: E-Mail mit Logo und Link senden`  verwendet.

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Receiver              | E-Mail Adressen der Empfänger (Kommasepariert)                                      | max.mustermann@example.com |
| Subject               | Betreff                                                                            | Test E-Mail                      |
| E-Mail Text                  | E-Mail Text                                                                         | Das ist ein Test           |
| E-Mail Gruß                  | E-Mail Gruß                                                                         | Mit freundlichen Grüßen    |
| Link Bezeichnung                  | Bezeichung, die auf dem Button angezeigt wird                                      | Beispielseite öffnen       |
| Link URL               | Link, auf den der Button verlinkt                                                  | example.com                |
| Reply-To Address      | E-Mail Adresse, an die geantwortet werden soll                                      | test@example.com           |
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

