# Systemintegration

Eine Kernfunktionalität von DigiWF ist die Integration von Prozessen in die Backend-Infrastruktur. Dabei spielt es keine
Rolle, ob die Anwendungen, die in einem Workflow verwendet werden sollen, on prem gehostet werden oder in der Cloud
laufen.
Wichtig ist lediglich, dass sie eine Schnittstelle besitzen - bzw. wenn sie keine haben, können die Anwendungen immer noch
über einen RPA Dienst angebunden werden.

## Integrationsarchitektur

![Es wird die Verbindung zwischen DigiWF Core, dem Event Bus und dem Integrationslayer mit diversen
Integrations Services (z.B. S3, Mail, LDAP, usw.) gezeigt.](~@source/images/platform/architecture/integration/digiwf_integration_architecture.png)

Im Application Integration Layer befindet sich eine Sammlung von Services, die mit dem Event-Bus verbunden sind. Es ist
unwichtig, wo diese Services laufen oder in welcher Technologie sie implementiert sind, solange sie mit dem Event-Bus
kommunizieren können. Die *Art* der Services ist nicht auf die Verbindung zwischen der Plattform und einem bestehenden
System beschränkt, sondern auch verschiedene Microservices können direkt angebunden werden.

Im `run` weiß der Business Process Layer (DigiWF Core) nichts von einem Integrations-Service. Die beiden sind lose
gekoppelt. Im `build` müssen die Services bzw. Service-Operationen, die man aus einem Prozess heraus aufrufen möchte, 
dagegen bekannt sein. Die Verbindung erfolgt hier über ein `Element Template` [^1]. Ein solches Template ist im
Grundegenommen ein Input-/Output-Mapping. Das bedeutet, dass die Daten aus dem Prozess auf die erwarteten
Daten der Operation abgebildet werden und umgekehrt.

## Interaktion aus dem Prozess

![Es wird dargestellt, wie ein Aufruf eines Integrationsartefaktes mit Hilfe eines Subprozesses aus einem
BPMN Prozess heraus aussehen könnte.](~@source/images/platform/architecture/integration/digiwf_integrate_from_process.png)

Um wiederverwendbare Operationsaufrufe aus BPMN-Prozessen heraus zu erstellen, ist es sinnvoll, diese in Subprozessen zu
kapseln. Die Abbildung zeigt dies exemplarisch. Ein Send-&-Reply-Pattern erscheint hier als ein Service-Task, den der
Prozessmodellierer einfach in den Prozess aufnehmen kann. Hier könnte auch ein generisches Fehlerhandling abgebildet
werden. Wichtig ist, dass der Subprozess ein eigenes `Element Template` hat. Das bedeutet, dass die eingehenden
Parameter den Werten entsprechen müssen, die der Subprozess zum Aufruf erwartet. Die ausgehenden Parameter sind
diejenigen, die der Subprozess am Ende zurückgibt.

## Integrationsartefakte erstellen

![Es wird dargestellt, wie man vorhandenene Spring Boot Starter Komponenten einbinden kann, um einen
eigenen Integratiosnartefakt zu erstellen.](~@source/images/platform/architecture/integration/digiwf_how_to_build_a_integration_artifact.png)

Um die Erstellung eigener Integrationsartefakte oder Microservices so einfach wie möglich zu gestalten, bietet DigiWF
Basisfunktionalitäten in Form von `Spring Boot Startern` [^2] an. Diese können einfach über das entsprechende
Dependency-Management-System (Maven, Gradle usw.) in das Projekt eingebunden werden und stehen dann zur Nutzung bereit.
Sie können über die `application.yaml`-Datei des Projekts (hier `your-own-mail-integration`) konfiguriert werden. Dies
ist sinnvoll, um die individuellen umgebungsspezifischen Parameter für ein System setzen zu können, während die
standardisierte Nutzung nicht selbst implementiert werden muss.

Im obigen Beispiel wird ein `your-own-mail-integration`-Service erstellt, der eingehende und ausgehende E-Mails
behandelt. Wenn der `digiwf-mail-integration-starter` eingebunden wird, erhält man automatisch eine Anbindung an den
S3-Service. Mails können Dateianhänge enthalten, die man nicht im Prozess haben möchte. Daher werden eingehende Anhänge
vorab im Dateispeicher gespeichert und die Referenz an den Prozess weitergegeben. Bei ausgehenden Mails ist es genau
andersherum. Der Prozess stellt eine Referenz auf die Datei(en) bereit, damit diese aus dem Dateispeicher geladen und an
die E-Mail angehängt werden können. Diese Logik ist bereits im `digiwf-mail-integration-starter` und
im `digiwf-s3-integration-client-starter` enthalten, sodass im Idealfall nichts mehr programmiert werden muss, um einen
Mail-Server anzubinden. Man erstellt lediglich ein Spring-Boot-Projekt (z. B. über [^3]), bindet
die Starter ein und kann mit der richtigen Konfiguration sofort arbeiten.

[^1]: Siehe [https://docs.camunda.io/docs/components/modeler/desktop-modeler/element-templates/about-templates/](https://docs.camunda.io/docs/components/modeler/desktop-modeler/element-templates/about-templates/)
[^2]: Siehe [https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters)
[^3]: Siehe [https://start.spring.io/](https://start.spring.io/)