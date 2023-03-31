# Expressionlanguage

Diese Abschnitt umfasst Grundlagen für die Modellierung / Konfiguration von technischen Workflows.

## Java Expression Language

Beim Konfigurieren von Prozessen kann im `Properties Panel` die `Java Expression Language` verwendet werden. Diese
bietet verschiedene Möglichkeiten:

* Funktionen aufrufen, die DigiWF bereitstellt
* auf Variablen zuzugreifen
* Operationen auf Variablen ausführen
* und vieles mehr. +

Im Folgenden werden grundlegende Elemente der JEL erläutert und deren Verwendung anhang von Beispielen aufgezeigt.

## Operatoren

Über Operatoren lassen sich logische Ausdrücke erstellen, die in Expressions verwendet werden können, um bspw. an
Gateways Entscheidungen zu modellieren.

**Vergleichsoperatoren**

| Operator | Funktion                 |
|----------|--------------------------|
| ==       | prüft auf Gleichheit     |
| !=       | prüft auf Ungleichheit   |
| >=       | prüft auf größer gleich  |
| >        | prüft auf größer         |
| <=       | prüft auf kleiner gleich |
| <        | prüft auf kleiner        |

**Logische Operatoren**

| Operator | Funktion                                                                                                                             |
|----------|--------------------------------------------------------------------------------------------------------------------------------------|
| &&       | Verschiedene Vergleiche werden mit einem logischen *Und* verknüpft und müssen alle erfüllt sein, damit der gesamte Ausdruck wahr ist |
| \        | \                                                                                                                                    | | Verschiedene Vergleiche werden mit einem logischen *Oder* verknüpft. Sobald ein Vergleich wahr ist, ist der gesamte Ausdruck wahr |

**Beispiele**

| Beispiel                          |
|-----------------------------------|
| `${B == "Kind"}`                  | Die Variable *B* enthält den Wert *Kind* |
| `${B != "" }`                     | Die Variable *B* ist *nicht leer* |
| `${FormField_Enum == 'Value_Ja'}` | Die Variable *FormField_Enum* ist gleich dem Wert *Value_Ja* |
| `${FormField_Count >= 10}`        | Die Variable *FormField_Count* ist *größer gleich 10* |
| `${B != "" && A < 100 }`          | Die Variable *B* ist *nicht leer* und *A* ist kleiner *100* |
