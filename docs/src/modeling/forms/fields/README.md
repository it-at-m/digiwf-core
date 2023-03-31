# Formularfelder

Für die Modellierung von Formularen stehen verschiedene Felder zur Verfügung.
Alle Felder haben folgende Konfigurationen gemeinsam, die über den `Edit` Button geöffnet werden können:

- **Key:** Der Schlüssel unter dem der Wert gespeichert wird
- **Titel:** Der Titel des Formularfelds
- **Beschreibung:** Ein Infotext der über ein Tooltip am Formularfeld angezeigt werden kann
- **Readonly:** Gibt an ob der Bearbeiter das Feld nur lesen darf. **!ACHTUNG!** Wenn die Checkbox gesetzt wurde und der
  Haken entfernt wird, ist im Schema *false* als Wert hinterlegt. Dies kann dazu führen, dass *readonly* bei Objekten
  nicht richtig vererbt wird.
- **Default:** Der Wert mit dem das Formular vorbefüllt wird

![Formular Editor](~@source/modeling/forms/fields/edit-field.png)

## Textfeld

## Textarea

## Zahl

## Gleitkommazahl

## Zeit

## Auswahl

## Mehrfachauswahl

## Dateien

## Benutzerauswahl

## Mehrfache Benutzerauswahl

## Schalter

## Liste

## Markdown