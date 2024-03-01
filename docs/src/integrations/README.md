# Digiwf Integrationen

Jede Digiwf Integration folgt unserem Integrationskonzept (Spring Boot Starter) mit einem:

- **core**, der die Businesslogik beinhaltet
- **starter** Module, das den *core* verwendet, um Spring Beans zur Verfügung zu stellen
- **example** Anwendung, die die Verwendung des Starters zeigt
- **service** Anwendung, die bereits vorkonfiguriert ist und direkt verwendet werden kann. Diese Service Anwendung ist
  üblicherweise als Docker Image im  [dockerhub](https://hub.docker.com/u/itatm) veröffentlicht. Eine solche Service
  Anwendung ist nur bei generische Integrationen vorhanden.

[Prozessbeispiele und Formulare](/modeling/templates/examples) können für die Verwendung der Integration heruntergeladen
werden.
[Element Templates](/modeling/templates/element-templates) können für eigene Bausteine heruntergeladen werden.

## Verfügbare Integrationen

- [Address Integration](digiwf-address-integration.md)
- [Alw Integration](digiwf-alw-integration.md)
- [CoSys Integration](digiwf-cosys-integration.md)
- [Dms Integration](digiwf-dms-integration.md)
- [Formserver Integration](digiwf-formserver-integration.md)
- [Email Integration](digiwf-mail-integration.md)
- [S3 Integration](digiwf-s3-integration.md)
- [Ticket Integration](digiwf-ticket-integration.md)

## Konzepte

- [Integrationsservices](concept/integration-service.md)
- [Fehlerbehandlung in Integrationen](concept/error-handling.md)

## Guides

- [Eigene Integrationsservices erstellen](guides/custom-integration-service.md)
