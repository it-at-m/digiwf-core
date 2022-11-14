# digiwf-integrations

In this module, you can find all integrations for the communication with other services or modules.

## digiwf-s3-integration

There are several ways to store files in S3 compatible storage. Each project often has to implement the same
functionalities and solve the same problems. With this library, we create the possibility to store and clean up files to
in a structured and simple way. Here's why:

* Files often need to be stored in folder structures
* Files often must be stored in a structured way and enriched with metadata
* Cleaning up the data must be done in a structured way
* Synchronous and asynchronous interfaces are often required

Of course, one service is not suitable for all projects, as your needs may be different. That's why we decided to
provide a Spring Boot Starter library for an integration service that can be easily customized.
Additionally, a second starter library is included, which serves as a client library to handle files and folders
with the above-mentioned starter.

## digiwf-alw-integration

The goal of this library is to enable async communication with the ALW System dispatched by an EventBus of your environment.
