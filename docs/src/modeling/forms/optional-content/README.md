# Optionale Inhalte
Optionale Inhalte sind in JSON Schema, das als Framework für die Formularerstellung verwendet wird, als `oneOf` definiert.
In der Modellierung wird ein `oneOf` Element als `Optional Group` bzw. `Optional Fields` definiert.
Diese unterscheiden sich lediglich in der Art und Weise, wie die Daten persistiert werden.
Zusätzlich steht für die Modellierung ein `Optional Container` zur Verfügung.

::: warning
Die Komplexität der Modellierung von optionalen Inhalten ist hoch.
Dies liegt vor allem daran, dass im verwendeten Framework für die Visualisierung, VJSF,
die Darstellung der Elemente, an die Definition der Datenstruktur gekoppelt ist.
Zudem wird das dahinterliegende JSON Schema vom System verwendet, um eine Validierung der Daten durchzuführen.

Die Modellierung von optionalen Inhalten sollte daher nur in Ausnahmefällen verwendet werden.
In zukünftigen Versionen des Systems wird die Modellierung von optionalen Inhalten überarbeitet.
:::

## Optional Group
Eine Optional Group beinhaltet eine Liste an `Optional Select Items`.
Diese kann wie als Feld in einer Gruppe definiert werden.

![Optional Group](~@source/modeling/forms/optional-content/optional-group.png)

in einer Optional Group kann wie bei einem normalen Feld ein `Key` angegeben werden.
Unter diesem `Key` werden die Daten der `Optional Select Items` gespeichert.

```json
{
  "fahrzeug": {
    "typ": "pkw",
    "kennzeichen": "M-AB 1234"
  }
}
```

oder

```json
{
  "fahrzeug": {
    "typ": "bahn",
    "bahnhof": "München Hbf"
  }
}
```

## Optional Fields
Über `Optional Fields` gibt es die Möglichkeit, die Inhalte der `Optional Select Items` auf der obersten Ebene im JSON zu speichern.

::: tip
`Optional Fields` müssen in einem `Optional Container` definiert werden.
Ein `Optional Container` befindet sich auf gleicher Ebene wie eine `Group`
:::

Das folgende Formular:

![Optional Fields](~@source/modeling/forms/optional-content/optional-fields.png)

würde im JSON wie folgt gespeichert werden:

```json
{
    "typ": "pkw",
    "kennzeichen": "M-AB 1234"
}
```

oder

```json
{
    "typ": "bahn",
    "bahnhof": "München Hbf"
}
```

## Optional Select Item
Ein Optional Select Item ist ein Feld, das in einer `Optional Group` oder `Optional Fields` verwendet wird.

![Optional Select Item](~@source/modeling/forms/optional-content/optional-select-item.png)

#### Einstellungen

- **Titel** Der Titel wird verwendet, um im Select-Element die Auswahlmöglichkeit anzuzeigen.

![Optional Select Item](~@source/modeling/forms/optional-content/optional-select-item-title.png)

Über die `selection` wird die Auswahl identifiziert.

![Optional Select Item](~@source/modeling/forms/optional-content/optional-select-item-selection-ident.png)

Der Key `selection` und der dazugehörige Wert kann im Kontextmenü konfiguriert werden.
Im zuvor aufgeführten Beispiel ist der Key `typ` und der Wert `pkw` bzw. `bahn`

![Optional Select Item](~@source/modeling/forms/optional-content/optional-select-item-selection.png)

::: warning
`Selection Items` die sich in der gleichen Gruppe befinden, müssen den gleichen `Key` haben.
:::
