# ADR-003 Use hexagonal architecture for services

## Status

accepted

## Context

Der Aufbau der Anwendung kann verschiedenen Architekturstilen folgen. Diese Entscheidung bestimmt den
Standardarchitekturstil eines Self-contained Services.

## Decision

Wir verwenden die hexagonal / port-adapter / onion / clean-Architektur. Dabei gelten folgende Regeln:

- Die Komponenten der Anwendung sind Schichten zugeordnet, wobei jede Schicht eine Verantwortlichkeit übernimmt.
- Es gibt folgende Schichten: Infrastruktur, Adapter, Application, Domain.
- Die Schichten sind so angeordnet, dass bestimmte Abhängigkeiten nicht erlaubt sind. Ordnet man die Schichten als
  konzentrische Kreise (von außen nach innen: Infrastruktur, Adapter, Application, Domain), so sind nur die
  Abhängigkeiten von außen nach innen erlaubt.
- Die Infrastrukturschicht ist für die Konfiguration der Infrastruktur zuständig.
- Adapter sind für die Anbindung der Anwendung an die technische Infrastruktur zuständig (HTTP, Messaging, RDBMS usw.).
- Adapter werden in "treibende" (engl. driving, inbound, primary) und getriebene (engl. driven, outbound, secondary)
  unterschieden, abhängig von der Rolle in der Anwendung.
- Um eine klare Trennung zwischen Adapter und Application zu garantieren, werden Ports (In und Out) bereitgestellt, die
  von den Driving Adapter aufgerufen bzw. von Driven Adapter implementiert werden.
- Der Driving Port wird von Services im Application-Tier implementiert. Diese Services stellen fachliche Use-Cases dar
  und werden Use-Case-Services genannt.

## Consequences

Durch die Verwendung von Komponenten mit klarer Zuständigkeit ist die Navigation im Code einfach.