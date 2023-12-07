# ADR-004 Use Java Packaging following Hexagonal Architecture Style

## Status

accepted

## Context

Die Struktur von Java packages ermöglicht eine schnelle Navigation im Quelltext und kann zur Qualität und Einheitlichkeit des 
Quelltextes beitragen. Es gibt verschiedene Stile die Java Packages zu strukturieren - diese Entscheidung legt den Strukturstil fest.

## Decision

Durch die Verwendung von Hexagonal Architecture [ADR003](adr003-use-hexagonal-architecture) ist der Architekturstil vorgegeben. Die Benennung der Java Packages 
folgt dem gewählten Architekturstil. Die Einhaltung dieser Benennung sollte mithilfe eines ArchUnit Tests geprüft werden.

Hier ist ein Beispiel wie die Package Struktur aussehen kann:

``` 
   └── <context-name>
        ├── adapter
        │   ├── in
        │   │   ├── rest
        │   │   │   ├── dto
        │   │   │   │   ├── ...Dto.java
        │   │   │   │   └── ...Dto.java
        │   │   │   ├── ...Controller.java (injects 1InPort)
        │   │   │   ├── ...Controller.java (injects 2InPort)
        │   │   │   ├── mapper
        │   │   │   │   ├── ...Mapper.java
        │   │   │   │   ├── ...Mapper.java (maps between domain model classes and DTOs)
        │   │   │   ├── RestExceptionMapping.java
        │   │   │   └── validation
        │   │   │       ├── ....java
        │   │   │       └── ...Validator.java
        │   │   └── streaming
        │   │       ├── ...Event.java
        │   │       └── MessageProcessor.java (injects 3InPort)
        │   └── out
        │       ├── integration
        │       │   └── IntegrationOutAdapter.java (implements IntegrationOutPort.java)
        │       ├── storage
        │       │   ├── BaseEntity.java
        │       │   ├── ...Entity.java
        │       │   ├── ...Repository.java
        │       │   ├── ...Mapper.java (maps between domain model classes and entities)
        │       │   ├── StorageOutAdapter.java (implements StorageOutPort.java)
        │       └── ...
        │           └── ....java
        ├── application
        │   ├── ...1UseCase.java  (implements ...1InPort.java)
        │   ├── ...2UseCase.java  (implements ...2InPort.java)
        │   ├── ...3UseCase.java  (implements ...3InPort.java)
        │   └── port
        │       ├── in
        │       │   ├── ...Exception.java
        │       │   ├── ...1InPort.java (uses domain model classes)
        │       │   ├── ...2InPort.java (uses domain model classes)
        │       │   └── ...3InPort.java (uses domain model classes)
        │       └── out
        │           ├── StorageOutPort.java (uses domain model classes)
        │           └── IntegrationOutPort.java (uses domain model classes)
        ├── domain
        │   └── model
        │       ├── ....java
        │       └── ....java
        └── infrastructure
            ├── dialect
            │   └── NoToastPostgresSQLDialect.java
            └── mapper
                └── MapstructConfiguration.java

```



