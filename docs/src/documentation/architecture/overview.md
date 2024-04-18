# Übersicht

DigiWF ist das Bindeglied zwischen Frontend- und Backend-Systemen. Alles, was über eine Netzwerkverbindung erreichbar
ist, kann an DigiWF angeschlossen und deklarativ in BPMN-Prozessen verwendet werden. Wir sind die Brücke zwischen dem
Entwicklerteam und den Prozessdesignern.

## Plattformkonzept

![The concept behind DigiWF. In the middle are your business processes, that can talk to your backend or
the cloud via integration artifacts](~@source/images/platform/architecture/digiwf_concept_process_and_integrationplatform.png)

DigiWF stellt vier Kernkompetenzen zur Verfügung:

- Einen Prozesslayer (DigiWF Core), in dem Prozessinstanzen und Entscheidungstabellen (DMN) auf Basis von Camunda BPMN
  ausgeführt werden. Hier werden auch Formularbeschreibungen gespeichert und User Tasks ausgeführt.
- Einen Integrations-Layer Richtung Frontend. Hier werden zur Laufzeit Schnittstellen und/oder Formulare bereitgestellt,
  die in eigenen (Frontend)-Anwendungen genutzt werden können. Alternativ kann die fertige Tasklist verwendet werden, um
  User Tasks abzuarbeiten oder den Stand eines Workflows anzuzeigen.
- Einen Integrations-Layer Richtung Backend. Über diesen können alle Systeme mit Schnittstelle angebunden werden. Falls
  keine Schnittstelle vorhanden ist, kann ein RPA-Dienst verwendet werden. Um eigene Verfahren schnell in die Prozesse
  einzubinden, stehen Bausteine in Form von Spring Startern zur Verfügung. Diese können genutzt werden, um
  wiederkehrende Problemstellungen, wie z.B. den Umgang mit ein- oder ausgehenden Dateien (z.B. E-Mail mit Anhang),
  standardisiert zu lösen. Einfach Starter einfügen und die API nutzen.
- Einen Co-Creation Bereich, in dem auch nicht-technische Nutzer Prozesse, Entscheidungstabellen und Formulare
  modellieren und auf der Plattform ausbringen können. Hierfür wurde eine eigene Web IDE erstellt, die einfach über den
  Browser genutzt werden kann.

## Core Modules

![Das Konzept hinter DigiWF wird dargestellt. In der Mitte ist DigiWF Core (der Prozess Layer) dargestellt.
Nach oben haben wir eine Integration Richtung GUI, nach unten eine Integration in die Verfahrenslandschaft. Rechts
ist als Build Komponente das Co-Creation dargestellt.](~@source/images/platform/architecture/digiwf_how_to_integrate_your_app.png)

Das obige Bild zeigt eine "ausgewachsene" DigiWF-Architektur einschließlich selbst erstellter Artefakte. Alles in
Schwarz ist der Kern der DigiWF Plattform und reicht für sich schon aus für den Betrieb von digitalen Workflows. Da dies 
in einigen Fällen nicht ausreicht, sind wir an vielen Stellen offen für Integration. Zur Unterstützung und Vereinfachung bieten
wir die in weiß beispielhaft dargestellten Komponenten und Bibliotheken an.
Es können folgende Artefakte erstellt und integriert werden (im Bild gelb):

- Eigene Frontends
- Integrationsartefakte zur Kommunikation mit Drittsystemen (egal ob On-Prem oder Cloud)
- (Mikro-)Services

Es können beliebige Technologien verwendet werden, aber die beste Unterstützung erhält man, wenn VueJS (mit VuetifyJs)
im Frontend und Spring Boot in den Integrations- oder anderen Services verwendet werden. Es gibt nur eine
Voraussetzungen:

- Das Backend (Dienste, Integrationsartefakte) muss in der Lage sein, mit einer der
  von [Spring Cloud Streams](https://spring.io/projects/spring-cloud-stream) unterstützten Binder-Implementierungen zu
  kommunizieren.

::: tip
Wenn eine andere Event Bus-Infrastruktur verwendet werden soll, als Apache Kafka, so kann dies in der DigiWF
Konfiguration erfolgen.
:::

Natürlich können Sie, wenn Sie Spring Boot in Ihren Backend-Komponenten und VueJs als Frontend-Technologie verwenden,
alle Querschnittskomponenten (wie Spring Boot Starter, NPM-Komponenten usw.) verwenden, die wir für unsere Komponenten
erstellt haben.

### DigiWF Core

DigiWF Core ist das Herzstück von DigiWF und besteht aus fünf Diensten. Zur Interaktion mit Frontend-Anwendungen gibt es
eine nach außen gerichtete API. Zusätzlich gibt es eine eventbasierte API, sodass über den Event Bus mit verschiedenen
Backend-Systemen kommuniziert werden kann.

![Die fünf Services in Digiwf Core: Service Definition, Form + Validation, Task, Service Instance and
Process Engine.](~@source/images/platform/architecture/digiwf_core_services.png)

Das Hauptziel von DigiWF ist es, eine deklarative Möglichkeit für Prozessdesigner zu schaffen, mit einer technischen
Infrastruktur zu interagieren. Dafür haben wir einige unterstützende Dienste rund um den
Opensource (https://camunda.com/)-Workflow erstellt:

- Prozessdienst: Dies ist der Service, in den Camunda eingebettet ist. Er wird hauptsächlich verwendet, um die
  BPMN-Workflows auszuführen.
- Serviceinstanz: Dieser Service behandelt jede Art von Serviceinstanzen. Eine Serviceinstanz kann ein Prozess sein,
  muss es aber nicht.
- Task Service: Dieser Service übernimmt alles, was für menschliche Aufgaben benötigt wird (Autorisierung, Mapping,
  Rückkanal usw.).
- Formular- und Validierungsservice: Alles, was im Zusammenhang mit Formularen benötigt wird, wird hier verarbeitet. Wir
  speichern die Formulardefinition und führen alle Arten von Formularvalidierungen durch.
- Servicedefinitionsservice: Dieser Service ist für die Definition eines Services und die entsprechenden Konfigurationen
  zuständig.

### DigiWF Integration

![Das DigiWF-Konzept, wie man eigene Integrationsartefakte basierend auf verschiedenen Spring Boot
Startern wie zum Beispiel Mail- oder S3-Dateidienst.](~@source/images/platform/architecture/digiwf_how_to_build_your_own_service.png)

Um ein beliebiges Backend-System zu integrieren, kann dies über die Integrationsschicht erfolgen. Die DigiWF-Integration
besteht aus einer Reihe von vordefinierten Integrationsartefakten wie S3, Mail, JMS oder anderen. Auf der anderen Seite
ist die DigiWF-Integration eine Toolbox, die hilft, so schnell wie möglich eigene Integrationsartefakte zu erstellen.
Dies wird durch die konsequente Verwendung von Spring Boot Startern zur Implementierung von Basisfunktionalitäten
gewährleistet.

### DigiWF Tasklist

Dies ist ein einfaches Frontend zur Interaktion mit laufenden Prozessinstanzen. Jede Benutzeraufgabe kommt auf der
Aufgabenliste vor und kann abgeholt werden (natürlich nur, wenn man das Recht dazu hat). Über die Taskliste können Sie
den Status „Ihrer“ Prozesse einsehen und neue Instanzen starten. Wem ein so hochgradig standardisiertes Frontend nicht
gefällt, kann "ready to use" Komponenten wie den Form Renderer verwenden und in die eigene, schöne Web Anwendung
integrieren. Oder Sie können die API direkt verwenden und ein schickes Frontend in der gewünschten Technologie selbst
erstellen.

### DigiWF Web Components

DigiWF bietet eigene WebComponents an, die in Webanwendungen integriert werden können, um DigiWF-Funktionalität hinzuzufügen.
Diese ermöglichen beispielsweise den Zugriff auf Nutzer-spezifische DigiWF-Vorgänge oder Aufgaben.
Weitere Informationen zu diesem Thema und eine Übersicht über derzeit verfügbare WebComponents sind in der [Komponenten-Beschreibung](../components/webcomponents.md) zu finden.

### DigiWF Co-Creation

Die DigiWF Co-Creation ist der Low-Code-Bereich. Hier kann ein Prozessdesigner BPMN-Prozesse zeichnen, mit
Entscheidungstabellen (DMN) herumtüfteln oder Webformulare per Drag and Drop erstellen. Auch das Deployment in
verschiedenen Infrastrukturen ist über diese Web-App möglich.

![Ein Bild des Drag & Drop Form Builders aus dem Co-Creation Bereich.](~@source/images/platform/architecture/form_builder.png)
