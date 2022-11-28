# System Integration

Eine Basis Funktionalität von DigiWF ist es, die Prozesse mit der Backend Infrastruktur zu verbinden.
Hierbei ist es zweitrangig, wo die Anwendungen liegen, die in einem Workflow verwendet werden sollen.
Sie können sowohl on prem gehostet werden, als auch in der Cloud laufen. Wichtig ist, dass sie eine Schnittstelle haben - bzw. wenn sie keine haben, kann die Anwendung immer noch über einen RPA Dienst angebunden werden.


## Integrationsarchitektur
![Es wird die Verbindung zwischen DigiWF Core, dem Event Bus und dem Integrationslayer mit diversen
Integrations Services (z.B. S3, Mail, LDAP, usw.) gezeigt.](~@source/images/platform/architecture/integration/digiwf_integration_architecture.png)

Ganz abstrakt haben wir im `Application Integration Layer` eine Sammlung an Services, die mit dem `Event Bus`
verbunden sind. Dabei ist es erst einmal egal, wo diese Services laufen, bzw. in welcher Technologie sie
implementiert sind. Wichtig ist, dass sie mit dem `Event Bus` kommunizieren können. Die "Art" der Services ist auch
nicht auf eine Verbindung zwischen der Plattform und einem bereits bestehenden System beschränkt. Hier können auch
diverse Microservices direkt angebunden werden.

Im `run` weiß auch der `Business Process Layer` (DigiWF Core) nichts von irgend einem Integrations Service. Die
beiden sind tatsächlich lose gekoppelt. Im `build` dagegen müssen die Services, bzw. die Service Operationen, die
man aus einem Prozess heraus aufrufen will sehr wohl bekannt sein. Die Verbindung passiert hier durch ein `Element
Template` [^1]. Im Prinzip ist solch ein Template ein Input / Output Mapping. D.h. Die Daten aus dem Prozess werden
auf die erwarteten Daten der Operation gemappt und umgekehrt.

## Interaktion aus dem Prozess
![Es wird dargestellt, wie ein Aufruf eines Integrationsartefaktes mit Hilfe eines Subprozesses aus einem
BPMN Prozess heraus aussehen könnte.](~@source/images/platform/architecture/integration/digiwf_integrate_from_process.png)

Um wiederverwendbare Operationsaufrufe aus BPMN Prozessen heraus erstellen zu können, ist es sinnvoll diese in
Subprozessen zu kapseln. Die Abbildung oben zeigt dies exemplarisch. Ein Send & Reply Pattern erscheint hier als ein
Service Task, den Prozessmodellierer einfach in den Prozess aufnehmen kann. Hier könnte auch ein generisches
Fehlerhandling abgebildet werden. Wichtig ist, dass der Subprozess ein eigenes `Element Template` hat. D.h. die
eingehenden Parameter entsprechen den Werten, die der Subprozess zum Aufruf erwartet. Die ausgehenden
Parameter sind diejenigen, die der Subprozess am Ende zurückgibt.

## Integrationsartefakte erstellen
![Es wird dargestellt, wie man vorhandenene Spring Boot Starter Komponenten einbinden kann, um einen
eigenen Integratiosnartefakt zu erstellen.](~@source/images/platform/architecture/integration/digiwf_how_to_build_a_integration_artifact.png)

Um es möglichst einfach zu machen, eigene Integrations Artefakte oder Micro Services zu erstellen, bieten wir
Basisfunktionalitäten in Form von `Spring Boot Startern` [^2] an. Diese können einfach über das entsprechende
Dependency Management (Maven, Gradle, etc.) in das Projekt eingebunden werden. Danach stehen sie zur Nutzung bereit.
D.h. sie können über die `application.yaml` des Projektes (hier `your-own-mail-integration`) konfiguriert werden.
Das ist sinnvoll, um zwar die individuellen umgebungsspezifischen Parameter für ein System setzen zu können - aber
trotzdem die standardisierte Nutzung nichts selbst implementieren zu müssen.

Im Beispiel oben wird ein `your-own-mail-integration` Service erstellt. Dieser behandelt eingehende und ausgehende
Mails. Wenn man den `digiwf-mail-integration-starter` einbindet, so bekommt man automatisch eine Anbindung an den S3
Service mit. Mails können ja Datei Anhänge enthalten, die man nicht im Prozess haben will. D.h. bei eingehenden
Anhängen werden diese vorab im Datei Speicher gespeichert und die Referenz an den Prozess weiter gegeben. Bei
ausgehenden Mails ist es genau anders herum. Der Prozess stellt eine Referenz auf die Datei(en) zur Verfügung, mit
der diese aus dem Datei Speicher geladen werden und an die E-Mail gehängt werden können. Diese Logik - Dateianhänge
über den Datei Speicher zu verarbeiten - ist bereits komplett `digiwf-mail-integration-starter` und
`digiwf-s3-integration-client-starter` enthalten. D.h. um einen Mail Server anzubinden, muss im besten Fall nichts
mehr programmiert werden. Man erstellt nur ein Spring Boot Projekt (z.B. über [^3]), bindet die Starter ein und kann
mit der richtigen Konfiguration sofort arbeiten.

[^1]: Siehe https://docs.camunda.io/docs/components/modeler/desktop-modeler/element-templates/about-templates/
[^2]: Siehe https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters
[^3]: Siehe https://start.spring.io/
