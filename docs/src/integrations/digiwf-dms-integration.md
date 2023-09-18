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

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN Errors und Incident Errors unterschieden.
BPMN Errors können im Prozess behandelt werden, während Incident Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen.

Nachfolgend sind die BPMN Errors aufgeführt, die von der dms Integration geworfen werden können:

#### BPMN Error

| Error Code | Error Message | Beschreibung | Handlungsempfehlung | 
|------------|---------------|--------------|---------------------|

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

| Environment Variable        | Description                                       |
|-----------------------------|---------------------------------------------------|
| DMS_INTEGRATION_SERVER_PORT | Port of the Application                           |
| DIGIWF_ENV                  | Environment in which the services runs            |
| KAFKA_SECURITY_PROTOCOL     | Security protocol of kafka (default is PLAINTEXT) |
| KAFKA_BOOTSTRAP_SERVER      | kafka server address (default is localhost)       |
| KAFKA_BOOTSTRAP_SERVER_PORT | kafka server port (default is 29092)              |
| FABASOFT_DMS_USERNAME       | technical fabasoft dms user                       |
| FABASOFT_DMS_PASSWORD       | technical fabasoft dms password                   |
| FABASOFT_DMS_HOST           | fabasoft url                                      |
| FABASOFT_DMS_PORT           | fabasoft port                                     |

