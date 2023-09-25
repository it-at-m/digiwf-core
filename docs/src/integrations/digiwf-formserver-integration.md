# DigiWF Formserver Integration

Die DigiWF Formserver Integration ist eine Integration, die es Benutzern ermöglicht Prozesse in DigiWF über den Formserver
zu starten.
Hierbei werden die Formulardaten an den zu startenden Prozess weitergegeben.

## Verwendung

### Datei-Upload

Dateien, die im Formular hochgeladen werden, werden über die [DigiWF S3 Integration](digiwf-s3-integration.md) im S3 Speicher abgelegt.

#### Eigene S3 Integration verwenden

Um eine eigene S3 Integration zu verwenden, muss für den jeweiligen Prozess eine Prozesskonfiguration deployed werden, 
in der die Konfiguration `app_file_s3_sync_config` gesetzt ist. Unter `app_file_s3_sync_config` muss die URL angegeben werden,
unter der die S3 Integration verfügbar ist.
Weiter Details zur Konfiguration sind in unserem [Modellierungshandbuch](../modeling/processes/config/README.md) beschrieben.

Ein minimalistisches Beispiel einer solchen Prozesskonfiguration ist:

```json
{
  "key": "P10 Parkausweis beantragen",
  "statusDokument": "",
  "statusConfig": [],
  "configs": [
    {
      "key": "app_file_s3_async_config",
      "value": "dwf-s3-local-01"
    },
    {
      "key": "app_file_s3_sync_config",
      "value": "http://127.0.0.1:8086"
    }
  ]
}
```
