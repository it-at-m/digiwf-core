# Funktionen

DigiWF stellt für die Modellierung verschiedene Basisfunktionen bereit. Die folgenden Funktionen können keinem
spezfischen Kapitel im Modellierungshandbuch zugewiesen werden und sind deshalb unter diesem Abschnitt zusammengefasst.

::: tip
In den jeweiligen Abschnitten können sich weitere Funktionen enthalten, beispielsweise für Prozesse oder
Konfigurationen.
:::

## DigiWF

| Funktion               | Beschreibung                                         | Beispiel                      |
|------------------------|------------------------------------------------------|-------------------------------|
| `urlMeineAufgaben()`   | Liefert die URL zur Ansicht *Meine Aufgaben*         | `${app.urlMeineAufgaben()}`   |
| `urlGruppenaufgaben()` | Liefert die URL zur Ansicht *Offene Gruppenaufgaben* | `${app.urlGruppenaufgaben()}` |
| `getFrontendUrl()`     | Liefert die Basis-URL zum DigiWF-Portal              | `${app.getFrontendUrl()}`     |

## User

Über User Funktionen können bestimmte Informationen zu einem Benutzer abgerufen werden.

| Funktion              | Beschreibung                             | Beispiel                     |
|-----------------------|------------------------------------------|------------------------------|
| `firstname(userId)`   | Liefert den Vornamen des Benutzers       | `${user.firstname('260')}`   |
| `lastname(userId)`    | Liefert den Nachnamen des Benutzers      | `${user.lastname('260')}`    |
| `username(userId)`    | Liefert den Usernamen des Benutzers      | `${user.username('260')}`    |
| `email(userId)`       | Liefert die E-Mail-Adresse des Benutzers | `${user.email('260')}`       |
| `salutation(userId)`  | Liefert die Anrede des Benutzers         | `${user.salutation('260')}`  |
| `phone(userId)`       | Liefert die Telefonnummer des Benutzers  | `${user.phone('260')}`       |
| `ou(userId)`          | Liefert die OU des Benutzers             | `${user.ou('260')}`          |
| `get(userId)`         | Liefert den gesamten Benutzer            | `${user.get('260')}`         |

Als Parameter können auch Variablen übergeben werden. In dieser Variable muss sich jedoch eine `lhmObjectID` befinden.
Beispiel: `${user.firstname(starterOfInstance)}`

## Execution

Die `execution` ist eine Komponente, die von Camunda bereitgestellt wird, um mit dem aktuellen Ausführungskontext zu
interagieren. Über die `execution` sind eine Vielzahl an Funktionen bereit, folgende dürfen verwendet werden:

| Funktion                  | Beschreibung                                               | Beispiel                                                 |
|---------------------------|------------------------------------------------------------|----------------------------------------------------------|
| `getVariable(key)`        | Lädt eine bestimmte Variable aus dem Execution Context     | `${execution.getVariable('starterOfInstance')}`          |
| `setVariable(key, value)` | Setzt eine bestimmte Variable im Execution Context         | `${execution.setVariable('meineVariable', 'Mein Wert')}` |
| `hasVariable(key)`        | Prüft, ob eine Variable im Execution Context vorhanden ist | `${execution.hasVariable('meineVariable')}`              |

Um auf die Elemente einer Liste zugreifen zu können, muss die Variable zunächst geparst werden. Das unten stehende Beispiel aus einem Script-Task zeigt den Zugriff auf das erste Element einer Liste, das in eine neue Variable gespeichert wird.
```
/*
* Erstes Element aus der Liste der DMS-COOs
* an eine Variable des Execution-Context uebergeben.
*/
const tFileCoos = JSON.parse(execution.getVariable('sachakten'));
const tFirstCoo = tFileCoos[0];
execution.setVariable('firstCoo', tFirstCoo);
```