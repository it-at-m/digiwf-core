# Objekte

Über Objekte kann die Speicherung von Werten verschachtelt werden. Dies kann genutzt werden, um Daten zu strukturieren.

::: tip
Objekte sollten bei der Modellierung in einer eigenen Gruppe modelliert werden.
Das verwendete Framework zur Anzeige von Formularen setzt Objekte immer ans Ende einer Gruppe,
egal an welche Stelle sie sich bei der Modellierung befinden.
Die trifft auch auf Dateien zu, da es sich hierbei um ein Objekt handelt.
:::

## Dynamisches Objekt
 Ein dynamisches Objekt kann in der Modellierung als Container für Felder verwendet werden.

![Dynamisches Objekt](~@source/modeling/forms/objects/dynamic-object.png)

 Bei der Speicherung der Daten als JSON Objekt, wird für den `Key` des Objektes ein eigener Eintrag mit
 untergeordneten Feldern erstellt, die sich aus den enthaltenen Feldern ergeben.
 
Das folgende Objekt 

![Dynamisches Objekt](~@source/modeling/forms/objects/dynamic-object-example.png)

würde wie folgt gespeichert werden:

```json
{
  "person": {
    "vorname": "Max",
    "nachname": "Mustermann",
    "adresse": "Maxstraße 8"
  }
}
```

## Objekt-Liste
