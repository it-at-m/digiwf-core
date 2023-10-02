# DigiWF Dms Integration

Die DMS Integration ermöglicht eine asynchrone Ablage von Dokumenten aus einem S3 Speicher in einem Dms System.
Zudem können Dms Strukturen angelegt und verwaltete werden, darunter:

- Anlage eines Dokuments
- Speichern von Schriftstücken
- Anlage und Suche von Akten
- Anlage und Archivierung von Vorgängen

## Verwendung

Durch die DMS Integration wird die Interaktion mit einem DMS System ermöglicht, darunter die Ablage von Dokumenten.
Zusätzlich kann direkt im Prozess auf untenstehende Fehler reagiert werden.

### Vorgang anlegen

Zur asynchronen Erstellung eines Vorgangs im Dms, erzeugen Sie zuerst ein `CreateProcedureDto`-Objekt und setzen den
TYPE-Header auf `createProcedure`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka Topic. Den Namen des
Topics können Sie in der Konfiguration des Dms Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination finden.

> Standardmäßig heißen die Topics *dwf-dms-${DIGIWF_ENV}*, wobei DIGIWF_ENV die aktuelle Umgebung ist.

Nachfolgend ist ein Beispiel für ein `CreateProcedureDto`-Objekt aufgeführt:

```json
{
  "fileCOO": "",
  "title": "",
  "user": ""
}
```

Die Dms Integration erzeugt einen Vorgang mit den angegebenen Variablen.
Dafür muss vorab eine Akte angelegt und die Id über das Feld `fileCOO` übergeben werden.

**Verwendung in BPMN Prozessen**

Verwenden Sie eines das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen und
befüllen Sie es mit den gewünschten Informationen:
[Vorgang anlegenn](https://github.com/it-at-m/digiwf-core/blob/dev/docs/src/.vuepress/public/element-template/createProcedure.json)

### Dokument erstellen

Zur asynchronen Erstellung eines Dokuments im Dms, erzeugen Sie zuerst ein `CreateDocumentDto`-Objekt und setzen den
TYPE-Header auf `createDocument`. Im Anschluss senden Sie das Objekt an das entsprechende Kafka Topic. Den Namen des
Topics können Sie in der Konfiguration des Dms Integration Services unter
spring.cloud.stream.bindings.functionRouter-in-0.destination finden.

> Standardmäßig heißen die Topics *dwf-dms-${DIGIWF_ENV}*, wobei DIGIWF_ENV die aktuelle Umgebung ist.

Nachfolgend ist ein Beispiel für ein `CreateDocumentDto`-Objekt aufgeführt:

```json
{
  "procedureCoo": "",
  "title": "",
  "user": "",
  "type": "",
  "filepaths": "",
  "fileContext": ""
}
```

Die Dms Integration erzeugt ein Dokument mit den angegebenen Variablen.
Dafür muss vorab ein Vorgang angelegt und die Id über das Feld `procedureCoo` übergeben werden.
Als `type` können die Werte EINGEHEND, AUSGEHEND oder INTERN angegeben werden.
Bei `filepaths` können mehrere Pfade zu Dateien oder Ordnern mit einem Komma getrennt übergeben werden.

**Verwendung in BPMN Prozessen**

Verwenden Sie eines das Element-Template in einer Call Activity, um die Prozessentwicklung zu beschleunigen und
befüllen Sie es mit den gewünschten Informationen:
[Dokument erstellen](https://github.com/it-at-m/digiwf-core/blob/dev/docs/src/.vuepress/public/element-template/createDocument.json)

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN Errors und Incident Errors unterschieden.
BPMN Errors können im Prozess behandelt werden, während Incident Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen.

Nachfolgend sind die BPMN Errors aufgeführt, die von der dms Integration geworfen werden können:

#### BPMN Error

| Error Code                                   | Error Message                                                                                                                                                                                                                   | Beschreibung                                                            | Handlungsempfehlung                                                                            | 
|----------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| `LOAD_FILE_FAILED`                           | An file could not be loaded from url: filepath                                                                                                                                                                                  | Die Datei konnte nicht geladen werden                                   | Stellen Sie sicher, dass die Datei im S3 Bucket vorhanden ist                                  |
| `LOAD_FOLDER_FAILED`                         | An folder could not be loaded from url: folderpath                                                                                                                                                                              | Der Ordner konnte nicht geladen werden                                  | Stellen Sie sicher, dass der Ornder im S3 Bucket vorhanden ist                                 |
| `FILE_TYPE_NOT_SUPPORTED`                    | The type of this file is not supported: filepath                                                                                                                                                                                | Der Dateityp der Datei wird nicht unterstützt                           | Die Datei kann nicht in DMS abgelegt werden                                                    | 
| `OBJEKT_GESPERRT`                            | Das Objekt "Objektname", „Objektadresse“ ist seit DD.MM.YYYY HH:MM:SS von Benutzername gesperrt.                                                                                                                                | Das Objekt befindet sich aktuell in Bearbeitung und ist daher gesperrt  | Stellen Sie sicher, dass das Objekt sich nicht in Bearbeitung befindet                         | 
| `FEHLENDE_BERECHTIGUNG`                      | Ihre Rechte für Objekt “<COO-Adresse>“ (Eigentümer/in “<Benutzername>“) reichen nicht aus.                                                                                                                                      | Zum Ausführen der Aktion fehlt dem übergeben Bentuzter die Berechtigung | Stellen Sie sicher, dass der Benutzer die notwendigen Berechtigungen hat                       | 
| `UNGUELTIGE_ADRESSE`                         | Ungültiger Input Parameter: Objektadresse “<im Aufruf angegebene COO-Adresse>“                                                                                                                                                  | Eine falsche oder nicht existierende COO-Adresse wurde übergeben        | Stellen Sie sicher, dass die richtige COO-Adresse übergeben wird                               | 
| `MEHR_ALS_1000_UNTERGEORDNETE_OBJEKTE`       | Unter dem Objekt “Objektname, COO-Adresse” dürfen keine weiteren Objekte angelegt werden, da dem Objekt bereits über 1000 untergeordnete Objekte zugeordnet sind.                                                               | Das übergeordnete Objekt enthält über 1000 Objekte                      | Es muss ein neues übergeordnetes Objekt erstellt werden                                        | 
| `AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE`        | Das übergebene Objekt mit der COO-Adresse “<COO Adresse>“ ist ungültig, da das übergebene Objekt von der Objektklasse “<Objektklasse>“ ist und dies nicht mit der/den erwarteten Objektklasse/n “<Objektklasse>“ übereinstimmt. | Das auszulesende Objekt entspricht nicht der erwarteten Objektklasse    | Stellen Sie sicher, dass die etwartete Objektklasse mit dem auszulesenden Objekt übereinstimmt | 
| `HINWEIS_LESEN_VON_STORNIERTEM_OBJEKT`       | Das übergebene Objekt mit der COO-Adresse „<COO-Adresse>" ist storniert.                                                                                                                                                        | Das übergebene Objekt ist storniert                                     | Das Objekt kann nicht gelesen werden                                                           | 
| `FALSCHE_ZUGRIFFSDEFINITION`                 | Ungültiger Input Parameter: “Zugriffsdefinition“ : „<Wert>“ enthält einen ungültigen Wert.                                                                                                                                      | Die übergebene Zugriffsdefinition ist ungültig                          | Stellen Sie sicher, dass eine gültige Zugriffsdefinition übergeben wird                        | 
| `FALSCHER_AKTENPLANEINTRAG`                  | Die Akte kann nicht erzeugt werden, da der übergebene Aktenplaneintrag „[objname]+[COO-Adresse]“ keine Betreffseinheit ist.                                                                                                     | Der übergebene Aktenplaneintrag ist keine Betreffseinheit               | Stellen Sie sicher, dass der übergebene Aktenplaneintrag eine Betreffseinheit ist              | 
| `NICHT_PLAUSIBEL`                            | Rückmeldung, wenn eine Plausibilitätsprüfung aufschlägt. z.B.: Das Eingangsdatum darf nicht in der Zukunft liegen.                                                                                                              | Eine Plausibilitätsprüfung schlägt fehl                                 | Überprüfen Sie Ihre Eingabe anhand der Fehlermeldung                                           | 
| `OBJEKT_ZU_GROSS_FUER_UEBERTRAGUNG_MIT_SOAP` | Inhaltsobjekt (objname) ist zu groß (über 100 MB) und kann daher nicht via SOAP übertragen werden.                                                                                                                              | Das Schriftstück ist zu groß                                            | Passen Sie die Größe des Schriftstücks an                                                      | 

## DigiWF Dms Integration anpassen

Die DigiWF Dms Integration wird als Spring Boot Starter Projekt bereitgestellt.
Um die Dms Integration an Ihre Bedürfnisse anzupassen, können Sie das Starter-Modul verwenden und die
bereitgestellten `@bean`s überschreiben sowie eigene `@bean`s hinzufügen.

Den `digiwf-dms-integration-starter` können Sie wie folgt in Ihr Projekt einbinden:

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
dem [`digiwf-dms-integration-core`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-dms-integration/digiwf-dms-integration-core)
und [`digiwf-dms-integration-starter`](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-dms-integration/digiwf-dms-integration-starter)
Modul vertraut und fügen Sie Ihre eigenen `@bean`s hinzu oder überschreiben Sie die bereitgestellten `@bean`s.

## Konfigurationen

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF Dms Integration verwenden:

### Dms Konfigurationen

| Environment Variable        | Description                                                   |
|-----------------------------|---------------------------------------------------------------|
| DMS_INTEGRATION_SERVER_PORT | Port of the Application                                       |
| DIGIWF_ENV                  | Environment in which the services runs                        |
| KAFKA_SECURITY_PROTOCOL     | Security protocol of kafka (default is PLAINTEXT)             |
| KAFKA_BOOTSTRAP_SERVER      | kafka server address (default is localhost)                   |
| KAFKA_BOOTSTRAP_SERVER_PORT | kafka server port (default is 29092)                          |
| FABASOFT_DMS_USERNAME       | technical fabasoft dms user                                   |
| FABASOFT_DMS_PASSWORD       | technical fabasoft dms password                               |
| FABASOFT_DMS_HOST           | fabasoft url                                                  |
| FABASOFT_DMS_PORT           | fabasoft port                                                 |
| FABASOFT_ENABLE_MTOM        | Enables MTOM default is true. Should be disabled with mocking |

