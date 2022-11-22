# Konzept zur Fehlerbehandlung

## Zielgruppe

* Integrationsentwickler
* Prozessmodelierer*innen

## Integrations

![Ablauf](images/ErrorHandlingIntegrations.png)

* Unterschied BPMN-Error u. Incident
  * wiederholbar 

* Integration: 
  * header:type = name of consumer-bean
  * payload:messageName -> bpmn message event  
  * Incident: cloudstream-utils:IncidentService (type incident)
  * configure createIncident-Function  
    ...
  * IncidentConsumer
  * IncidentServiceImpl: getEventSubscriptions("incident?")  
  * IncidentType("integrationError  
  * executionApi.createIncident  
  
  * Konfiguration: 
    * createIncident-Function
    * createIncident-destination = connector-Topic
    

Deprecated:
[comment]: <> (    * message event "incident"  wird korreliert)

[comment]: <> (    * createIncidentDelegate  )


  * BpmnError: BpmnErrorService aus cloudstream-utils

