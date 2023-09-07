# Digiwf ALW Integration

Provides integration to ALW (Ausländerwesen) service for retrieval of responsible employees.

## Request

For the valid request an AZR number must be provided. This number must contain 12-digits. If the AZR number
is missing or has a wrong format a VALIDATION ERROR is thrown.

## Manual Test outside of München Network

1. Start the `docker-compose` setup
2. Start application with profiles `local` and `alw-emulation`
3. Use `digiwf-alw-integration-service/rest-api-client/example.http`