# Cosys

Um eine Dokument über Cosys zu erstellen, muss eine CallActivity modelliert werden, 
die das Element Template `Cosys: Dokument erstellen (alle Daten) (V02)` verwendet.

Es werden alle gesammelten Prozessdaten an Cosys übergeben.

Die vorsignierten S3-Urls, unter denen das zu erstellende  sind zuvor über den S3-Baustein zu generieren

**Properties**

| Property              | Beschreibung                                                                       | Beispiel                   |
|-----------------------|------------------------------------------------------------------------------------|----------------------------|
| Event Topic           | Das Topic der Cosys Integration                                                    | dwf-cosys-demo             |
| Client                | Der Name des Cosys Client                                                          | 9001                       |
| Role                  | Der Name der Cosys Role                                                            | TESTER                     |
| Presigned urls (S3)   | Zuvor generierte signierte Urls des S3-Service für die Ablage des generierten Dokumtens | ${presignedUrls}                     |
| GUID                  | Eindeutiger Bezeichner der Cosys-Vorlage                                                | 519650b7-87c2-41a6-8527-7b095675b13f |

