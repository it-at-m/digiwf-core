# DigiWF Cosys Integration

Die Cosys Integration ermöglicht eine asynchrone Erstellung von Dokumenten in [Cosys](https://www.cib.de/cosys/) mit anschließender Speicherung in einem S3 Speicher. 
Mit dieser Integration können Dokumente erstellt und in einem S3-kompatiblen Speicher abgelegt werden. Diese Dokumente können im Anschluss beispielweise in einer E-Mail versendet werden.    

## Verwendung

Durch die Cosys Integration wird die Erstellungen von Dokumenten ermöglicht, die anschließend in einem S3 Speicher abgelegt werden. Zusätzlich kann direkt im Prozess auf untenstehende Fehler reagiert werden. 

### Dokument erstellen

Zur asynchronen Erstellung eines Dokumentens in Cosys, erzeugen Sie zuerst ein `GenerateDocument`-Objekt und setzen den TYPE-Header auf `createCosysDocument`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka Topic. Den Namen des Topics können Sie in der Konfiguration der Cosys Integration unter spring.cloud.stream.bindings.functionRouter-in-0.destination finden.

> Standardmäßig heißen die Topics *dwf-cosys-${DIGIWF_ENV}*, wobei DIGIWF_ENV die aktuelle Umgebung ist.

Nachfolgend ist ein Beispiel für ein `GenerateDocument`-Objekt aufgeführt:

```json
{
  "client": "",
  "role": "",
  "guid": "",
  "variables": {},
  "documentStorageUrls": [
    {
      "url": "",
      "path": "",
      "action": ""
    }
  ]
}
```

Die Cosys Integration erzeugt ein Dokument mit den angegebenen Variablen und speichert dieses in einem S3 Speicher. Dafür muss vorab eine `presigned URL` für das Dokument erstellt werden. Diese wird in der Variable `documentStorageUrls` übergeben. 

In der Variable `documentStorageUrls`  wird lediglich eine einzige `presigned URL` akzeptiert und es können die Aktionen `POST` oder `PUT` verwendet werden.
Die Aktion `POST` wird für die Erstellung neuer Dateien im S3 Speicher verwendet und die Aktion `PUT` um ein bereits vorhandenes Dokument zu überschreiben.

**Verwendung in BPMN Prozessen**

Verwenden Sie eines unsere Element-Templates in einer Call Activity um die Prozessentwicklung zu beschleunigen und befüllen Sie es mit den gewünschten Informationen:

* [Cosys all data](https://github.com/it-at-m/digiwf-core/blob/dev/docs/src/.vuepress/public/element-template/cosys-alle-daten.json)
* [Cosys create document](https://github.com/it-at-m/digiwf-core/blob/dev/docs/src/.vuepress/public/element-template/cosys-dokument-erstellen.json)
* [Cosys create document (V02)](https://github.com/it-at-m/digiwf-core/blob/dev/docs/src/.vuepress/public/element-template/cosys_generate_document_template_V02.json)

Zur Erstellung von presigned URLs können Sie das Element-Template [s3_create_presigned_url](https://github.com/it-at-m/digiwf-core/blob/dev/docs/src/.vuepress/public/element-template/s3_create_presigned_url_template.json) in einer Call Activity verwenden und das Ergebnis an die Cosys Integration übergeben. 

In der folgenden Grafik wird ein Beispiel für einen BPMN Prozess dargestellt. Wie oben beschrieben, wird zuerst eine presigned URL erstellt bevor ein Dokument erstellt wird. 

![Cosys Feature Prozess.](~@source/documentation/featureprocesses/cosys/cosys-feature-process.png)

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN Errors und Incident Errors unterschieden.
BPMN Errors können im Prozess behandelt werden, während Incident Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen.

Nachfolgend sind die BPMN Errors aufgeführt, die von der Cosys Integration geworfen werden können:

#### BPMN Error

| Error Code                | Error Message                                                    | Beschreibung                                                                                                                                                     | Handlungsempfehlung                                               | 
|---------------------------|------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|
| `VALIDATION_ERROR`        | Fehlermeldung der auftretenden `ValidationException`             | Die übergebenen Daten sind nicht valide.                                                                                                                | Korrigieren Sie die Daten und versuchen es erneut                 |
| `S3_FILE_SAVE_ERROR`      | Document could not be saved.                                     | Die in Cosys erzeugte Datei kann nicht im S3 Storage gespeichert werden. Die übergebene Presigned Url ist fehlerhaft (nicht valide, abgelaufen, falsche Action). | Überprüfen Sie, ob die Daten valide sind und versuchen es erneut. | 
| `COSYS_DOCUMENT_CREATION_FAILED`     | Document could not be created.                   | Das Dokument konnte nicht erstellt werden. Es kann sein, dass ein technischer Fehler aufgetreten ist                                                             | Analysieren Sie die Fehlermeldung und versuchen es erneut.        |


## DigiWF Cosys Integration anpassen

Die DigiWF Cosys Integration wird als Spring Boot Starter Projekt bereitgestellt.
Um die Cosys Integration an Ihre Bedürfnisse anzupassen, können Sie das Starter-Modul verwenden und die
bereitgestellten `@bean`s überschreiben sowie eigene `@bean`s hinzufügen.

Den `digiwf-cosyc-integration-starter` können Sie wie folgt in Ihr Projekt einbinden:

**Mit Maven**

```xml
   <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-cosys-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

**Mit Gradle**

```gradle
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-cosys-integration-starter', version: '${digiwf.version}'
```

Machen Sie sich mit
dem [`digiwf-cosys-integration-core`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-cosys-integration/digiwf-cosys-integration-core)
und [`digiwf-cosys-integration-starter`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-cosys-integration/digiwf-cosys-integration-starter)
Modul vertraut und fügen Sie Ihre eigenen `@bean`s hinzu oder überschreiben Sie die bereitgestellten `@bean`s.

## Konfigurationen

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF Cosys Integration verwenden:

### Cosys Konfigurationen

| Eigenschaft                                            | Bedeutung             |
|--------------------------------------------------------|-----------------------|
| `io.muenchendigital.digiwf.cosys.url`                  | URL des Cosys Servers |
| `io.muenchendigital.digiwf.cosys.merge.datafile`       |                       |
| `io.muenchendigital.digiwf.cosys.merge.inputLanguage`  | Sprache Eingabe       |
| `io.muenchendigital.digiwf.cosys.merge.outputLanguage` | Sprache Ausgabe       |
| `io.muenchendigital.digiwf.cosys.merge.keepFields`     |                       |

### S3 Konfigurationen

| Eigenschaft                                                | Bedeutung             |
|------------------------------------------------------------|-----------------------|
| `io.muenchendigital.digiwf.s3.client.document-storage-url` | 	Document Storage URL |
