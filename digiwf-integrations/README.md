# digiwf-integrations

In this module, you can find all integrations for the communication with other services or modules.

## digiwf-email-integration

This is a Spring Boot Starter library to send e-mails in the DigiWF environment.
It can be used to send mails asynchronously through a spring cloud stream compatible event broker.<br>For attachments to not clutter the EventBus,
paths to file locations on the S3-Filer are expected. The file is then grabbed by this library to avoid sending
large files repeatedly through the EventBus.

The goal of this library is enabling async mail dispatching with a EventBus and a S3-Filer as your environment.

Features:

* Can be used to dispatch emails asynchronously through an eventbus.
* Can inform the receiver through an eventbus if the email has been sent or if there was a problem.
* Can get attachment files from an S3-Filer, no need to get them yourself and send it through the EventBus several
  times. Less clutter in your EventBus!

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

## digiwf-verification-integration

The goal of this library is to provide a service able to get a verification registered and return a verification link which later can be used to verify any data by confirmation.

## digiwf-ok.ewo-integration

The goal of this library is to provide a service through which it is possible to request OK.EWO data. 
