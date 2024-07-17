# DigiWF ALW Integration

![](https://img.shields.io/badge/Integration_Name-alwIntegration-informational?style=flat&logoColor=white&color=2c73d2)

The goal of this library is to enable asynchronous communication with the ALW System dispatched by an EventBus of your
environment.

Features:

* Can be used to dispatch requests/responses of the ALW Personeninfo Feature asynchronously through an eventbus.
* Can inform the receiver through an eventbus if the request was successful or if there was a problem.
* Performs a functional ping to the ALW System to check connectivity.

### Built With

The documentation project is built with technologies we use in our projects:

* Spring Boot
* Spring Cloud Stream
* Apache Kafka

### Fehlerbehandlung

Bei der Fehlerbehandlung wird zwischen BPMN-Errors und Incident-Errors unterschieden.
BPMN-Errors können im Prozess behandelt werden, während Incident-Errors nicht im Prozess behandelt werden können
und einen Incident erzeugen.

Nachfolgend sind die BPMN-Errors aufgeführt, die von der ALW-Integration geworfen werden können:

#### BPMN Error

| Error Code                 | Error Message                                                                                                                                                     | Beschreibung                                                                                                                                                                                     | Handlungsempfehlung                                                                                                                                 | 
|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `RESPONSIBILITY_NOT_FOUND` | Responsible `<found respnsible>` for azr `<given AZR-Number>` does not match any known responsibility! / Could not find ALW responsible for `<given AZR-Number>`. | Die verantwortliche Organisationseinheit kann nicht gefunden werden, entweder weil sie von ALW nicht zurückgegeben wurde oder die zurückgegebene nicht einer bekannten Verantwortung entspricht. | Der Vorgang wird ohne verantwortliche Organisationseinheit weiter bearbeitet oder abgebrochen.                                                      |
| `VALIDATION_ERROR_CODE`    | AZR-Number is invalid; it must contain 12 digits.The AZR-Number is required and cannot be null or empty./                                                         | Die übergebene AZR-Nummer (Nummer Ausländerzentralregister) ist nicht valide. Sie muss aus genau 12 Ziffern bestehen.                                                                            | Stellen Sie sicher, dass eine gültige AZR-Nummer übergeben wird.                                                                                    | 
| `UNEXPECTED_ERROR`         | `{ALW-ERROR-CODE-MESSAGE}`                                                                                                                                        | Bei ALW ist ein unerwarteter Fehler aufgetreten, außer `404 Not found`.                                                                                                                          | Es ist ein Fehler aufgetreten und ALW konnte die Anfrage nicht verarbeiten. Eine Überprüfung der Anfrage sowie des Status von ALW ist erforderlich. | 

## Konfiguration

Allgemeine Konfigurationen für DigiWF-Integrationen sind unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren)
beschrieben.

Die Konfiguration der ALW Integration ist in der [README.md](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-alw-integration/README.md) beschrieben.