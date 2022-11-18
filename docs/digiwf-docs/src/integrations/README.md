# Digiwf Integrationen

Jede Digiwf Integration folgt unserem Integrationskonzept (Spring Boot Starter) mit einem:

- **core**, der die Businesslogik beinhaltet
- **starter** Module, das den *core* verwendet, um Spring Beans zur Verfügung zu stellen
- **example** Anwendung, die die Verwendung des Starters zeigt
- **service** Anwendung, die bereits vorkonfiguriert ist und direkt verwendet werden kann. Diese Service Anwendung ist üblicherweise als Docker Image im  [dockerhub](https://hub.docker.com/u/itatm) veröffentlicht. Eine solche Service Anwendung ist nur bei generische Integrationen vorhanden.

## Verfügbare Integrationen

- [DigiWF ALW Integration](src/integrations/ALW/README.md)
- [DigiWF Cosys Integration](cosys.md)
- [DigiWF Mail Integration](mail.md)
- [DigiWF S3 Integration](s3.md)
- [DigiWF Verification Integration](verification.md)
