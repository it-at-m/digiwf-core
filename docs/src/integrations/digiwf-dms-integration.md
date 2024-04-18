# DigiWF Dms Integration

![](https://img.shields.io/badge/Integration_Name-dmsIntegration-informational?style=flat&logoColor=white&color=2c73d2)

Die DMS Integration ermöglicht eine asynchrone Ablage von Dokumenten aus einem S3-Speicher in einem DMS-System.
Zudem können DMS-Strukturen angelegt und verwaltet werden, darunter:

- Anlage eines Dokuments
- Speichern von Schriftstücken
- Anlage und Suche von Akten
- Anlage und Archivierung von Vorgängen

## Verwendung

Durch die DMS-Integration wird die Interaktion mit einem DMS-System ermöglicht, darunter die Ablage von Dokumenten.
Zusätzlich kann direkt im Prozess auf untenstehende Fehler reagiert werden. Es gibt die Möglichkeit zwischen zwei
DMS-Systemen zu wählen: MUCS- und ALW-DMS. Für das MUCS DMS wird der Integration-Name `mucsDmsIntegration` verwendet,
für
das ALW DMS `alwDmsIntegration`.

### Sachakte anlegen

Zur asynchronen Erstellung einer Sachakte im DMS erzeugen Sie zuerst ein `CreateFileDto`-Objekt und setzen den
TYPE-Header auf `createFile`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka-Topic. Den Namen des Topics
können Sie in der Konfiguration des DMS-Integration-Services
unter `spring.cloud.stream.bindings.functionRouter-in-0.destination` finden.

Nachfolgend ist ein Beispiel für ein `CreateFileDto`-Objekt aufgeführt:

```json
{
  "apentryCOO": "",
  "title": "",
  "user": ""
}
```

Die DMS-Integration erzeugt eine Sachakte mit den angegebenen Variablen. Dafür muss vorab die ID des Aktenplans gesucht
und über das Feld `apentryCOO` übergeben werden.

**Verwendung in BPMN-Prozessen**

Verwenden Sie eines der Element-Templates in einer Call Activity, um die Prozessentwicklung zu beschleunigen und
befüllen Sie es mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Vorgang anlegen

Zur asynchronen Erstellung eines Vorgangs im DMS erzeugen Sie zuerst ein `CreateProcedureDto`-Objekt und setzen den
TYPE-Header auf `createProcedure`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka-Topic. Den Namen des
Topics können Sie in der Konfiguration des DMS-Integration-Services
unter `spring.cloud.stream.bindings.functionRouter-in-0.destination` finden.

Nachfolgend ist ein Beispiel für ein `CreateProcedureDto`-Objekt aufgeführt:

```json
{
  "fileCOO": "",
  "title": "",
  "fileSubj": "",
  "user": ""
}
```

Die DMS-Integration erzeugt einen Vorgang mit den angegebenen Variablen. Dafür muss vorab eine Akte angelegt und die ID
über das Feld `fileCOO` übergeben werden.

**Verwendung in BPMN-Prozessen**

Verwenden Sie eines der Element-Templates in einer Call Activity, um die Prozessentwicklung zu beschleunigen und
befüllen Sie es mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Dokument erstellen

Zur asynchronen Erstellung eines Dokuments im DMS erzeugen Sie zuerst ein `CreateDocumentDto`-Objekt und setzen den
TYPE-Header auf `createDocument`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka-Topic. Den Namen des
Topics können Sie in der Konfiguration des DMS-Integration-Services
unter `spring.cloud.stream.bindings.functionRouter-in-0.destination` finden.

Nachfolgend ist ein Beispiel für ein `CreateDocumentDto`-Objekt aufgeführt:

```json
{
  "procedureCoo": "",
  "title": "",
  "date": "",
  "user": "",
  "type": "",
  "filepaths": "",
  "fileContext": ""
}
```

Die DMS-Integration erzeugt ein Dokument mit den angegebenen Variablen. Dafür muss vorab ein Vorgang angelegt und die ID
über das Feld `procedureCoo` übergeben werden. Als `type` können die Werte EINGEHEND, AUSGEHEND oder INTERN angegeben
werden. Bei `filepaths` können mehrere Pfade zu Dateien oder Ordnern mit einem Komma getrennt übergeben werden.

**Verwendung in BPMN-Prozessen**

Verwenden Sie eines der Element-Templates in einer Call Activity, um die Prozessentwicklung zu beschleunigen und
befüllen Sie es mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Dokument updaten

Zum Updaten eines Dokuments im DMS erzeugen Sie zuerst ein `UpdateDocumentDto`-Objekt und setzen den TYPE-Header
auf `updateDocument`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka-Topic. Den Namen des Topics können
Sie in der Konfiguration des DMS-Integration-Services
unter `spring.cloud.stream.bindings.functionRouter-in-0.destination` finden.

Nachfolgend ist ein Beispiel für ein `UpdateDocumentDto`-Objekt aufgeführt:

```json
{
  "documentCoo": "",
  "user": "",
  "type": "",
  "filepaths": "",
  "fileContext": ""
}
```

Die DMS-Integration fügt bei einem bestehenden Dokument die angegebenen Schriftstücke hinzu. Dafür muss vorab ein
Dokument angelegt und die ID über das Feld `documentCoo` übergeben werden. Als `type` können die Werte EINGEHEND,
AUSGEHEND oder INTERN angegeben werden. Bei `filepaths` können mehrere Pfade zu Dateien oder Ordnern mit einem Komma
getrennt übergeben werden.

**Verwendung in BPMN-Prozessen**

Verwenden Sie eines der Element-Templates in einer Call Activity, um die Prozessentwicklung zu beschleunigen und
befüllen Sie es mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Zu den Akten legen

Um ein Objekt im DMS zu den Akten zu legen, müssen Sie zuerst ein `DepositObjectDto`-Objekt erstellen und den
TYPE-Header auf `depositObject` setzen. Anschließend müssen Sie das Objekt an das entsprechende Kafka Topic senden. Den
Namen des Topics finden Sie in der Konfiguration des DMS Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination.

Hier ist ein Beispiel für ein `DepositObjectDto`-Objekt:

```json
{
  "objectCoo": "",
  "user": ""
}
```

Die DMS-Integration legt das Objekt mit der angegebenen `Coo` zu den Akten. Hierfür muss ein Objekt zuvor angelegt und
die ID über das Feld `objectCoo` übergeben werden.

**Verwendung in BPMN-Prozessen**

Verwenden Sie das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen, und füllen Sie es
mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Objekt stornieren

Um ein Objekt im DMS zu stornieren, müssen Sie zuerst ein `CancelObjectDto`-Objekt erstellen und den TYPE-Header
auf `cancelObject` setzen. Anschließend müssen Sie das Objekt an das entsprechende Kafka Topic senden. Den Namen des
Topics finden Sie in der Konfiguration des DMS Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination.

Hier ist ein Beispiel für ein `CancelObjectDto`-Objekt:

```json
{
  "objectCoo": "",
  "user": ""
}
```

Die DMS-Integration storniert das Objekt mit der angegebenen `Coo`. Hierfür muss ein Objekt zuvor angelegt und die ID
über das Feld `objectCoo` übergeben werden.

**Verwendung in BPMN-Prozessen**

Verwenden Sie das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen, und füllen Sie es
mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Dateien lesen und in den S3-Speicher übertragen

Um eine Datei aus dem DMS zu lesen und in den S3-Speicher zu übertragen, müssen Sie zuerst ein `ReadContentDto`-Objekt
erstellen und den TYPE-Header auf `readContent` setzen. Anschließend müssen Sie das Objekt an das entsprechende Kafka
Topic senden. Den Namen des Topics finden Sie in der Konfiguration des DMS Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination.

Hier ist ein Beispiel für ein `ReadContent`-Objekt:

```json
{
  "contentCoos": [
    "coo1",
    "coo2"
  ],
  "filePath": "folder",
  "fileContext": "processContext",
  "user": "test"
}
```

Die DMS-Integration liest die Inhalte mit den angegebenen `contentCoos` und überträgt sie in den S3-Speicher.

**Verwendung in BPMN-Prozessen**

Verwenden Sie das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen, und füllen Sie es
mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Sachakte suchen

Um eine Sachakte im DMS zu suchen, müssen Sie zuerst ein `SearchObjectDto`-Objekt erstellen und den TYPE-Header
auf `searchFile` setzen. Anschließend müssen Sie das Objekt an das entsprechende Kafka Topic senden. Den Namen des
Topics finden Sie in der Konfiguration des DMS Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination.

Hier ist ein Beispiel für ein `SearchObject`-Objekt:

```json
{
  "searchString": "untergruppe.*-sachakte-*",
  "user": "test"
}
```

Es ist möglich, die Suche über ein Fachdatum einzugrenzen. Dafür kann der Suche eine Referenz auf ein Fachdatum
mitgegeben werden. Hier ist ein Beispiel für ein `SearchObject`-Objekt, das die optionalen Felder 'Fachdatum-Referenz'
und 'Fachdatum-Wert' verwendet:

```json
{
  "searchString": "untergruppe.*-sachakte-*",
  "user": "test",
  "reference": "testNumber",
  "value": "42"
}
```

Die DMS-Integration sucht nach der entsprechenden Sachakte und gibt eine COO-Liste derjenigen Sachakten zurück, auf die
die Suchkriterien zutreffen. Wenn keine gefunden werden, wird ein `OBJECT_NOT_FOUND` BPMN Error geworfen.

**Verwendung in BPMN-Prozessen**

Verwenden Sie das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen, und füllen Sie es
mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Aktenplaneintrag suchen

Um einen Aktenplaneintrag im DMS zu suchen, müssen Sie zuerst ein `SearchObjectDto`-Objekt erstellen und den TYPE-Header
auf `searchSubjectArea` setzen. Anschließend müssen Sie das Objekt an das entsprechende Kafka Topic senden. Den Namen
des Topics finden Sie in der Konfiguration des DMS Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination.

Hier ist ein Beispiel für ein `SearchObject`-Objekt:

```json
{
  "searchString": "aktenplanName",
  "user": "test"
}
```

Die DMS-Integration sucht nach dem entsprechenden Aktenplaneintrag und gibt die `Coo` des ersten zurück, der gefunden
wird. Wenn keiner vorhanden ist, wird ein `OBJECT_NOT_FOUND` BPMN Error geworfen.

**Verwendung in BPMN-Prozessen**

Verwenden Sie das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen, und füllen Sie es
mit den gewünschten Informationen. Eine Liste der Element-Templates finden Sie unter
[Element Templates](/modeling/templates/element-templates/).

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN Errors und Incident Errors unterschieden. BPMN Errors können im Prozess
behandelt werden, während Incident Errors nicht im Prozess behandelt werden können und einen Incident erzeugen.

Nachfolgend sind die BPMN Errors aufgeführt, die von der DMS-Integration geworfen werden können:

#### BPMN Error

| Error Code                                   | Error Message                                                                                                                                                                                                                 | Beschreibung                                                                      | Handlungsempfehlung                                                                            | 
|----------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| `LOAD_FILE_FAILED`                           | An file could not be loaded from url: filepath                                                                                                                                                                                | Die Datei konnte nicht geladen werden                                             | Stellen Sie sicher, dass die Datei im S3 Bucket vorhanden ist                                  |
| `LOAD_FOLDER_FAILED`                         | An folder could not be loaded from url: folderpath                                                                                                                                                                            | Der Ordner konnte nicht geladen werden                                            | Stellen Sie sicher, dass der Ordner im S3 Bucket vorhanden ist                                 |
| `FILE_TYPE_NOT_SUPPORTED`                    | The type of this file is not supported: filepath                                                                                                                                                                              | Der Dateityp der Datei wird nicht unterstützt                                     | Die Datei kann nicht in DMS abgelegt werden                                                    | 
| `OBJEKT_GESPERRT`                            | Das Objekt "Objektname", „Objektadresse“ ist seit DD.MM.YYYY HH:MM:SS von Benutzername gesperrt.                                                                                                                              | Das Objekt befindet sich aktuell in Bearbeitung und ist daher gesperrt            | Stellen Sie sicher, dass das Objekt sich nicht in Bearbeitung befindet                         | 
| `FEHLENDE_BERECHTIGUNG`                      | Ihre Rechte für Objekt `<COO-Adresse>` (Eigentümer/in `<Benutzername>`) reichen nicht aus.                                                                                                                                    | Zum Ausführen der Aktion fehlt dem übergebenen Benutzer die Berechtigung          | Stellen Sie sicher, dass der Benutzer die notwendigen Berechtigungen hat                       | 
| `UNGUELTIGE_ADRESSE`                         | Ungültiger Input Parameter: Objektadresse `<im Aufruf angegebene COO-Adresse>`                                                                                                                                                | Eine falsche oder nicht existierende COO-Adresse wurde übergeben                  | Stellen Sie sicher, dass die richtige COO-Adresse übergeben wird                               | 
| `AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE`        | Das übergebene Objekt mit der COO-Adresse `<COO Adresse>` ist ungültig, da das übergebene Objekt von der Objektklasse `<Objektklasse>` ist und dies nicht mit der/den erwarteten Objektklasse `<Objektklasse>` übereinstimmt. | Das auszulesende Objekt entspricht nicht der erwarteten Objektklasse              | Stellen Sie sicher, dass die erwartete Objektklasse mit dem auszulesenden Objekt übereinstimmt | 
| `HINWEIS_LESEN_VON_STORNIERTEM_OBJEKT`       | Das übergebene Objekt mit der COO-Adresse `<COO-Adresse>` ist storniert.                                                                                                                                                      | Das übergebene Objekt ist storniert                                               | Das Objekt kann nicht gelesen werden                                                           | 
| `FALSCHE_ZUGRIFFSDEFINITION`                 | Ungültiger Input Parameter: “Zugriffsdefinition“ : `<Wert>` enthält einen ungültigen Wert.                                                                                                                                    | Die übergebene Zugriffsdefinition ist ungültig                                    | Stellen Sie sicher, dass eine gültige Zugriffsdefinition übergeben wird                        | 
| `FALSCHER_AKTENPLANEINTRAG`                  | Die Akte kann nicht erzeugt werden, da der übergebene Aktenplaneintrag `[objname]+[COO-Adresse]` keine Betreffseinheit ist.                                                                                                   | Der übergebene Aktenplaneintrag ist keine Betreffseinheit                         | Stellen Sie sicher, dass der übergebene Aktenplaneintrag eine Betreffseinheit ist              | 
| `NICHT_PLAUSIBEL`                            | Rückmeldung, wenn eine Plausibilitätsprüfung aufschlägt. z.B.: Das Eingangsdatum darf nicht in der Zukunft liegen.                                                                                                            | Eine Plausibilitätsprüfung schlägt fehl                                           | Überprüfen Sie Ihre Eingabe anhand der Fehlermeldung                                           | 
| `OBJEKT_ZU_GROSS_FUER_UEBERTRAGUNG_MIT_SOAP` | Inhaltsobjekt (objname) ist zu groß (über 100 MB) und kann daher nicht via SOAP übertragen werden.                                                                                                                            | Das Schriftstück ist zu groß                                                      | Passen Sie die Größe des Schriftstücks an                                                      |
| `AKTE_HAT_OFFENE_PROZESSE`                   | Akte / Vorgang hat offene Prozesse und z.A. kann nicht erfolgen                                                                                                                                                               | Es liegt in der Akte oder dem Vorgang noch ein nicht abgeschlossender Prozess vor | Prozesse durch manuellen Eingriff beenden lassen                                               |  
| `AKTE_BEREITS_ZA`                            | Akte / Vorgang ist schon z.A. geschrieben                                                                                                                                                                                     | Die Akte oder der Vorgang ist bereits z.A. gesetzt                                | Bei Bedarf sicherstellen, warum schon z.A. gesetzt. Eventuell kein Handlungsbedarf             |  
| `ROLLE_NICHT_IDENTIFIZIERBAR`                | Rolle nicht identifizierbar                                                                                                                                                                                                   | Übergebene Rolle liegt nicht in der eAkte vor.                                    | Stellen Sie sicher, dass genutzte Rollen auch in der eAkte angelegt sind.                      | 

## DigiWF DMS-Integration anpassen

Die DigiWF DMS-Integration wird als Spring-Boot-Starter-Projekt bereitgestellt. Um die Integration an Ihre Bedürfnisse
anzupassen, können Sie das Starter-Modul verwenden und die bereitgestellten `@Bean`s überschreiben oder eigene `@Bean`s
hinzufügen.

Sie können den `digiwf-dms-integration-starter` in Ihr Projekt wie folgt einbinden:

**Mit Maven**

```xml

<dependency>
    <groupId>de.muenchen.oss.digiwf</groupId>
    <artifactId>digiwf-dms-integration-starter</artifactId>
    <version>${digiwf.version}</version>
</dependency>
```

**Mit Gradle**

```gradle
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-dms-integration-starter', version: '${digiwf.version}'
```

Machen Sie sich mit
dem [`digiwf-dms-integration-core`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-dms-integration/digiwf-dms-integration-core)-
und dem [`digiwf-dms-integration-starter`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-dms-integration/digiwf-dms-integration-starter)
Modul vertraut und fügen Sie Ihre eigenen `@Bean`s hinzu oder überschreiben Sie die bereitgestellten `@Bean`s.

## Konfigurationen

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF Dms Integration verwenden:

### Dms Konfigurationen

| Environment Variable             | Description                                                   |
|----------------------------------|---------------------------------------------------------------|
| MUCS_DMS_INTEGRATION_SERVER_PORT | Port of the MUCS DMS Application                              |
| ALW_DMS_INTEGRATION_SERVER_PORT  | Port of the ALW DMS Application                               |
| DIGIWF_ENV                       | Environment in which the services runs                        |
| KAFKA_SECURITY_PROTOCOL          | Security protocol of kafka (default is PLAINTEXT)             |
| KAFKA_BOOTSTRAP_SERVER           | kafka server address (default is localhost)                   |
| KAFKA_BOOTSTRAP_SERVER_PORT      | kafka server port (default is 29092)                          |
| FABASOFT_DMS_USERNAME            | technical fabasoft dms user                                   |
| FABASOFT_DMS_PASSWORD            | technical fabasoft dms password                               |
| FABASOFT_DMS_HOST                | fabasoft url                                                  |
| FABASOFT_DMS_PORT                | fabasoft port                                                 |
| FABASOFT_ENABLE_MTOM             | Enables MTOM default is true. Should be disabled with mocking |
