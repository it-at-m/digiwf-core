# Digiwf Integrationen

Jede Digiwf Integration folgt unserem Integrationskonzept (Spring Boot Starter) mit einem:

- **core**, der die Businesslogik beinhaltet
- **starter** Module, das den *core* verwendet, um Spring Beans zur Verfügung zu stellen
- **example** Anwendung, die die Verwendung des Starters zeigt
- **service** Anwendung, die bereits vorkonfiguriert ist und direkt verwendet werden kann. Diese Service Anwendung ist üblicherweise als Docker Image im  [dockerhub](https://hub.docker.com/u/itatm) veröffentlicht. Eine solche Service Anwendung ist nur bei generische Integrationen vorhanden.

Prozessbeispiele und Formulare für die Verwendung der Integration können [hier](src/modeling/templates/examples) heruntergeladen werden.
Element-Templates für eigene Bausteine können [hier](src/modeling/templates/element-templates) heruntergeladen werden.
