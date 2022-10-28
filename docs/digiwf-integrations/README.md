# DigiWF Integrations

## Integration Structure

Each digiwf integration follows our starter concept with a

- **core** that contains the integrations business logic
- **starter** module that uses the *core* and creates the *spring beans*  
- **example** application that uses the starter and demonstrates its usage 
- **service** application that is ready to go and available as docker image on [dockerhub](https://hub.docker.com/u/itatm). The service application is only available for generic integration artifacts.  

## Available Integrations

- [DigiWF Cosys Integration](digiwf-cosys-integration.md)
