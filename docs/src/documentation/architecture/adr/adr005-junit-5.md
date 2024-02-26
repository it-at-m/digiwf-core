# ADR-004 JUnit 5 für Java Unit-Tests

## Status

accepted

## Context

Bei der Hebung auf Spring Boot 3 wurde ebenfalls ein Großteil der Unit-Tests von JUnit 4 auf JUnit 5 gehoben. 
Es gibt aber bei Altbereichen die Möglichkeit auf JUnit 4 Tests. Das kann zu Problemen bei der Ausführung von Tests führen.

## Decision

1. Es wird nur noch JUnit 5 verwendet.
2. Es wird bei dem Hinzufügen von neuen Dependencies darauf geachtet, dass JUnit 4 ausgeschlossen wird sofern vorhanden.
3. Entdeckt man bei der Entwicklung noch bestehende JUnit 4 Tests, werden diese sofort auf JUnit 5 migriert und JUnit 4 aus dem Classpath entfernt.


## Consequences

Die Anwendung beinhaltet weniger Legacy Abhängigkeiten.
