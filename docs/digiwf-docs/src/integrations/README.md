# Digiwf Integrationen

Jede Digiwf Integration folgt unserem Integrationskonzept (Spring Boot Starter) mit einem:

- **core**, der die Businesslogik beinhaltet
- **starter** Module, das den *core* verwendet, um Spring Beans zur Verfügung zu stellen
- **example** Anwendung, die die Verwendung des Starters zeigt
- **service** Anwendung, die bereits vorkonfiguriert ist und direkt verwendet werden kann. Diese Service Anwendung ist üblicherweise als Docker Image im  [dockerhub](https://hub.docker.com/u/itatm) veröffentlicht. Eine solche Service Anwendung ist nur bei generische Integrationen vorhanden.

## Verfügbare Integrationen

- [DigiWF ALW Integration](digiwf-alw-integration/README.md)
- [DigiWF Cosys Integration](digiwf-cosys-integration/README.md)
- [DigiWF Mail Integration](digiwf-mail-integration/README.md)
- [DigiWF S3 Integration](digiwf-s3-integration/README.md)
- [DigiWF Verification Integration](digiwf-verification-integration/README.md)
