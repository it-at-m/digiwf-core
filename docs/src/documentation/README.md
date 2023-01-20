# Einführung

2019 haben wir begonnen, die Prozessautomatisierung in der öffentlichen Verwaltung der [Landeshauptstadt München](https://stadt.muenchen.de) auf
Basis der [Camunda BPMN Process Engine](https://camunda.com) voranzutreiben. Relativ früh wurde unter dem Projektnamen DigiWF eine eigene
Aufgabenliste auf Basis von [Vue.js](https://vuejs.org/) und [VuetifyJs](https://vuetifyjs.com) implementiert. Außerdem wurden erste Integrationsmodule wie Mail oder
DMS in die Backend-Infrastruktur eingebaut.

Heute ist DigiWF zu einer viel genutzten Plattform geworden. Vor allem das
Integrationskonzept ist mittlerweile ausgereift und jedes System, das über eine Schnittstelle verfügt (und wenn es keine
Schnittstelle hat, dann bindet man einfach einen RPA-Client an) lässt sich ohne großen Aufwand anbinden. Möglich wird
dies durch ein modulares Baukastenkonzept über eine Sammlung von [Spring Boot](https://spring.io/projects/spring-boot) Startern. Probleme wie das Dateihandling
werden zu einem Standardproblem, das auf allen Eingangskanälen gleichermaßen (gleich leicht) gelöst werden kann.

Wenn eine solche Plattform wächst, stellt sich früher oder später die Frage, wer die Arbeit machen soll. DigiWF wurde
konsequent so aufgebaut, dass Menschen, die in der Lage sind, automatisierte Workflows zu erstellen (aber nicht
programmieren können), dies selbst tun können. Ermöglicht wird dies durch eine eigene „Low Code“-Komponente – die
Co-Creation. Dort können Prozesse und Entscheidungstabellen auf Basis von BPMN und DMN erstellt werden.
Backend-Systeme (sofern für diese ein Integrationsartefakt verfügbar ist) werden deklarativ über Input- und
Output-Templates angebunden. Neben den Prozessen können auch Formulare für Benutzeraufgaben per Drag & Drop erstellt
werden.

Wenn Sie – ähnlich wie in der Landeshauptstadt München – viele Prozesse mit vielen Benutzeraufgaben haben. Und dann ein
heterogene Backend-Landschaft, die Sie in Ihre Prozesse integrieren müssen, dann sollten Sie unbedingt eine nehmen schau
mal bei DigiWF. [Kontaktieren Sie uns einfach per E-Mail](/contact).
