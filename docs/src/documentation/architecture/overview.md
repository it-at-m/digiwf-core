# Übersicht

DigiWF ist das Bindeglied zwischen den Frontend- und Backend-Systemen. Alles, was über eine Netzwerkverbindung erreicht werden kann, kannst Du an DigiWF anhängen und deklarativ in Deinen BPMN-Prozessen verwenden. Wir sind die Brücke zwischen dem Entwicklerteam und den Prozessdesignern.

## Plattform Konzept

![The concept behind DigiWF. In the middle are your business processes, that can talk to your backend or
the cloud via integration artifacts](~@source/images/platform/architecture/digiwf_concept_process_and_integrationplatform.png)

DigiWF stellt vier Kernkompetenzen zur Verfügung:

- Einen Prozesslayer (DigiWF Core), in dem natürlich die Prozessinstanzen und Entscheidungstabellen (DMN) auf Basis von Camunda BPMN ausgeführt werden. Aber auch die Formularbeschreibungen gespeichert, oder User Tasks ausgeführt werden.
- Einen Integrations Layer Richtung frontend. Hier werden zur Laufzeit Schnittstellen und / oder Formulare zur Verfügung gestellt, die man in eigenen (Frontend) Anwendungen nutzen kann. Alternativ kann die fertige Tasklist verwendet werden, um User Tasks abzuarbeiten, oder den Stand eines Workflows anzuzeigen.
 Einen Integrationslayer in Richtung Backend. Über den kann alles was eine Schnittstelle hat angebunden werden. Hat es keine Schnittstelle, kann immer noch ein RPA Dienst verwendet werden. Um schnell eigene Verfahren in die Prozesse einbinden zu können, werden eine Reihe von "ready to use" Bausteinen in Form von Spring Startern zur Verfügung gestellt. Diese können genutzt werden, um wiederkehrende Problemstellung - wie beispielsweise der Umgang mit ein- oder ausgehenden Dateien (z.B. E-Mail mit Anhang) - standardisiert zu lösen. Einfach Starter einfügen und die API nutzen.
- Einen Co-Creation Bereich, um auch nicht technischen Nutzern die Möglichkeit zu geben ihre Prozesse, entscheidungstabellen und Formulare modellieren und sogar auf der Plattform ausbringen zu können. Dafür wurde eine eigene Web IDE erstellt, die einfach über den Browser genutzt werden kann.

## Core Modules


![Das Konzept hinter DigiWF wird dargestellt. In der Mitte ist DigiWF Core (der Prozess Layer) dargestellt.
Nach oben haben wir eine Integration Richtung GUI, nach unten eine Integration in die Verfahrenslandschaft. Rechts
ist als Build Komponente das Co-Creation dargestellt.](~@source/images/platform/architecture/digiwf_how_to_integrate_your_app.png)

Das Bild oben zeigt eine mögliche "ausgewachsene" DigiWF-Architektur einschließlich selbst erstellter Artefakte. Alles in
Blau wird vom DigiWF-Projekt bereitgestellt, aber wir sind offen für Integrationen. Es könnten also folgende Artefakte
erstellen und integriert werden:

- eigene Frontends (Die Technologie ist nichjt wirklich entscheidend - wenn man aber beispielsweise unsere
  Formurlarkomponente verwenden will, dann geht das mit VueJs am besten)
- eigene Integrationsartefakte zur Kommunikation mit der On-Premise- oder Cloud-Infrastruktur
- eigene (Mikro-)Services

Es kann jede beliebige Technologie verwenden werden. Bestens unterstützt wird man aber, wenn im Frontend VueJS (mit
VuetifyJs) und in den Integrations- oder anderen Services Spring Boot verwendet wird. Es gibt nur zwei Voraussetzungen:

- Die Frontend-Technologie muss über einen GraphQL-Client verfügen
- Das Backend (Dienste, Integrationsartefakte) muss in der Lage sein, mit einer der von [Spring Cloud Streams]
  (https://spring.io/projects/spring-cloud-stream) unterstützten Binder-Implementierungen zu kommunizieren.

::: tip
Wenn eine andere Event Bus Infrastruktur verwendet werden soll, als Apache Kafka, so kann dies in der DigiWF 
Konfiguration erfolgen.
:::

Of course - if you'll use Spring Boot in your backend components and VueJs as front end technology, you can use all cross-section components (like Spring Boot Starters, NPM components, ...) we have created for our components.

### DigiWF Core
DigiWF Core ist das Herzstück von DigiWF und besteht aus 5 Diensten. Zur Interaktion mit Frontend-Anwendungen gibt
es eine Graph-QL API. Zusätzlich eine sehr generische API, die über einen Event Bus mit verschiedenen
Backend-Systemen kommuniziert.


![Die fünf Services in Digiwf Core: Service Definition, Form + Validation, Task, Service Instance and
Process Engine.](~@source/images/platform/architecture/digiwf_core_services.png)

Das Hauptziel von DigiWF ist es, eine deklarative Möglichkeit für Prozessdesigner zu schaffen, mit einer technischen Infrastruktur zu interagieren. Dafür haben wir einige unterstützende Dienste rund um den Opensource [camunda](https://camunda.com/)-Workflow erstellt:

- Prozessdienst: Dies ist der Service, in den Camunda eingebettet ist. Es wird hauptsächlich verwendet, um die
  BPMN-Workflows auszuführen.
- Serviceinstanz: Dies behandelt jede Art von Serviceinstanzen. Eine Serviceinstanz kann ein Prozess sein, muss aber
  nicht.
- Task Service: Dieser Service übernimmt alles, was wir für menschliche Aufgaben benötigen (Autorisierung, Mapping, Rückkanal, ...).
- Formular- und Validierungsservice: Alles, was wir im Zusammenhang mit Formularen benötigen, wird hier verarbeitet.
  Wir speichern die Formulardefinition und führen alle Arten von Formularvalidierungen durch.
- Servicedefinitionsservice: Dieser Service ist für die Definition eines Services und die entsprechenden
  Konfigurationen
  zuständig.

### DigiWF Integration

![Das DigiWF-Konzept, wie man eigene Integrationsartefakte basierend auf verschiedenen Spring Boot
Startern wie zum Beispiel Mail- oder S3-Dateidienst.](~@source/images/platform/architecture/digiwf_how_to_build_your_own_service.png)


Um ein beliebiges Backend-System zu integrieren, kann dies über die Integrationsschicht getan werden. Die
DigiWF-Integration ist einerseits eine Reihe vordefinierter Integrationsartefakte wie S3, Mail, JMS oder andere. Auf der anderen Seite ist die DigiWF-Integration eine Toolbox, die hilft, so schnell wie möglich eigene Integrationsartefakte zu erstellen. Dies wird durch die konsequente Verwendung von Spring Boot Startern zur Implementierung von Basisfunktionalitäten gewährleistet.

### DigiWF Tasklist
Dies ist ein einfaches Frontend zur Interaktion mit laufenden Prozessinstanzen. Jede Benutzeraufgabe kommt auf der
Aufgabenliste vor und kann abgeholt werden (natürlich nur, wenn man das Recht dazu hat). Über die Taskliste können
Sie den Status „Ihrer“ Prozesse einsehen und neue Instanzen starten. Wem ein so hochgradig standardisiertes Frontend
nicht gefällt, kann "ready to use" Komponenten wie den Form Renderer verwenden und in die eigene, schöne Web Anwendung
integrieren.
Oder man kann die API direkt verwenden und ein schickes Frontend in der gewünschten Technologie selbst erstellen.

### DigiWF Co-Creation
Die DigiWF Co-Creation ist der Low-Code-Bereich. Hier kann ein Prozessdesigner BPMN-Prozesse zeichnen,
mit Entscheidungstabellen (DMN) herum tüfteln oder Webformulare per Drag and Drop erstellen. Auch das Deployment in
verschiedenen Infrastrukturen ist über diese Web-App möglich.

![Ein Bild des Drag & Drop Form Builders aus dem Co-Creation Bereich.](~@source/images/platform/architecture/form_builder.png)

