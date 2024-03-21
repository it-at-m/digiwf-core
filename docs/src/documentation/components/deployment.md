# Deployment

Mit der neuen CoCreation ändert sich das Deployment grundlegend. Artefaktdeployments werden synchron mit REST anstelle
von asynchronen Messages durchgeführt. Dabei greift das Deployment auf die Miranum-Deployment-Komponenten zurück und
implementiert diese in den bestehenden Anwendungen. Die Implementierung orientiert sich hierbei an
der [Miranum-Dokumentation](https://miranum.com/docs/components/miranum-ide/miranum-deployment).

> Eine Anwenderdokumentation finden Sie unter [https://digiwf.oss.muenchen.de/modeling/plattform/deployment/](/modeling/plattform/deployment/README.md)

## Deployment Services

![digiwf-deployment](~@source/images/platform/components/deployment/digiwf-deployment.png)

### miranum-cli

Nachdem in der neuen CoCreation lokal Prozesse entwickelt werden, müssen diese mit der *miranum-cli* in die Umgebungen
deployt werden. Um flexibel bei den Deployments und Umgebungen sein zu können, leitet die *miranum-cli* die Artefakte an
den *digiwf-cocreation-deployment* Service weiter.

### digiwf-cocreation-deployment

Der *digiwf-cocreation-deployment* Service ist eine Single API, die die Deployments aus der CoCreation an die jeweils
zuständigen Services im digiwf-core weiterleitet. Dadurch können Deployments flexibel an die jeweiligen Services
weitergeleitet werden, ohne Anpassungen an der CoCreation vornehmen zu müssen.

> Aktuell werden alle Deployments in der *digiwf-engine* durchgeführt. In Zukunft können hier weitere Services hinzukommen.

### miranum-deployment-receiver-core

Jeder Service implementiert einen *miranum-deployment-receiver*, um die Deployments entgegenzunehmen und durchzuführen.
Aktuell werden nur die Deployments in der *digiwf-engine* durchgeführt.

Die *digiwf-engine* nutzt den *miranum-deployment-receiver-core* und definiert einen eigenen
REST-Controller `/rest/deployment/v2/`. Dieser REST-Controller wurde implementiert, um URL-Kollisionen zu vermeiden, da
die Engine bereits über einen `/rest/deployment/` Endpunkt verfügt.

## Authentifizierung

tbd.