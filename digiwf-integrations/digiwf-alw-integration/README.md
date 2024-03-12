# Digiwf ALW Integration

Provides integration to ALW (Ausländerwesen) service for retrieval of responsible employees.

## Getting started

1. Build it with `mvn clean install`
2. Run Stack using `docker-compose`

## Request

For the valid request an AZR number must be provided. This number must contain 12-digits. If the AZR number
is missing or has a wrong format a VALIDATION ERROR is thrown.

## Manual Test outside of München Network

1. Start the `docker-compose` setup
2. Start application with profiles `local` and `alw-emulation`
3. Use `digiwf-alw-integration-service/rest-api-client/example.http`

## Manual Test inside of München Network

1. Set Spring Properties:
    ```
   io:
     muenchendigital:
       digiwf:
         alw:
           personeninfo:
             sachbearbeitung-config-url: /config/alw-sachbearbeitung.properties
           baseurl: <host>
           restendpoint: <endpoint url>
           timeout: 15000
           username: <username>
           password: <password>
   ```
2. Start the applications in the following order:
    1. EngineServiceApplication
        - Activate Spring profile `local,no-ldap,streaming`
        - Add Environment values from `stack/local-docker.env`
    2. DigiWFConnectorApplication
        - Activate Spring profile `local,streaming`
        - Add Environment values from `stack/local-docker.env`
    4. TaskListApplication
       - Activate Spring profile `local,no-ldap,streaming`
       - Add Environment values from `stack/local-docker.env`
    5. AwlServiceApplication
       - Activate Spring profile `local`
       - Add Environment values from `stack/local-docker.env`
3. Test the functionality with the
   process [alw-integration](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/alw-integration)