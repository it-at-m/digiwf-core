# Task list service based on Polyflow

## Requirements

The service needs a RDBMS (currently postgresql), Kafka and SSO to work properly. Please start the `docker-compose` stack located in 
`/stack` before starting the application.

## Start from IDEA

In order to start the task list service, please run the `service.de.muenchen.oss.digiwf.task.TaskListApplication` from your 
IDE. The application relies on several environment variables, so either copy the content of the `local.env` into your environment
variables or use the [.EnvFile plugin](https://plugins.jetbrains.com/plugin/7861-envfile) and add the file to your execution.

## REST API

To access the REST API of the task list service, check the Open API definition. Locally, 
it is available under http://localhost:38765/swagger-ui/index.html.  

