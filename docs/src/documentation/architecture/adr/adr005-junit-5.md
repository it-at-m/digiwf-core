# ADR-004 Verwendung von JUnit 5 für Java Unit-Tests

## Status

accepted

## Context

Bei der Migration auf Spring Boot 3 wurden auch viele Unit-Tests von JUnit 4 auf JUnit 5 aktualisiert. Es gibt jedoch
noch Altbereiche, die JUnit 4-Tests verwenden. Dies kann zu Problemen bei der Ausführung von Tests führen.

## Decision

1. Es wird ausschließlich JUnit 5 verwendet.
2. Beim Hinzufügen neuer Dependencies wird darauf geachtet, dass JUnit 4 ausgeschlossen wird, sofern es vorhanden ist.
3. Wenn bei der Entwicklung noch bestehende JUnit 4-Tests entdeckt werden, werden diese sofort auf JUnit 5 migriert und
   JUnit 4 aus dem Classpath entfernt.

## Consequences

Die Anwendung enthält weniger Legacy-Abhängigkeiten.