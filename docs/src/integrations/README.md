# Digiwf-Integrationen

Jede Digiwf-Integration folgt unserem Integrationskonzept (Spring Boot Starter) mit einem:

- **core**, der die Businesslogik beinhaltet
- **ctarter**-Modul, das den *core* verwendet, um Spring Beans zur Verfügung zu stellen
- **example**-Anwendung, die die Verwendung des Starters zeigt
- **service**-Anwendung, die bereits vorkonfiguriert ist und direkt verwendet werden kann. Diese Service-Anwendung ist
  üblicherweise als Docker-Image im [dockerhub](https://hub.docker.com/u/itatm) veröffentlicht. Eine solche
  Service-Anwendung ist nur bei generischen Integrationen vorhanden.

(/modeling/templates/examples) können für die Verwendung der Integration heruntergeladen werden.
(/modeling/templates/element-templates) können für eigene Bausteine heruntergeladen werden.

## Verfügbare Integrationen

- (digiwf-address-integration.md)
- (digiwf-alw-integration.md)
- (digiwf-cosys-integration.md)
- (digiwf-dms-integration.md)
- (digiwf-formserver-integration.md)
- (digiwf-mail-integration.md)
- (digiwf-s3-integration.md)
- (digiwf-ticket-integration.md)

## Konzepte

- [Integrationsservices](concept/integration-service.md)
- [Fehlerbehandlung in Integrationen](concept/error-handling.md)

## Guides

- [Eigene Integrationsservices erstellen](guides/custom-integration-service.md)
