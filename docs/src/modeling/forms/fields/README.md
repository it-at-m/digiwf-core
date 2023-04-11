# Formularfelder

Für die Modellierung von Formularen stehen verschiedene Felder zur Verfügung.
Alle Felder haben folgende Konfigurationen gemeinsam, die über den `Edit` Button geöffnet werden können:

### Allgemein

- **Key:** Der Schlüssel unter dem der Wert gespeichert wird
- **Titel:** Der Titel des Formularfelds
- **Beschreibung:** Ein Infotext der über ein Tooltip am Formularfeld angezeigt werden kann
- **Readonly:** Gibt an ob der Bearbeiter das Feld nur lesen darf. **!ACHTUNG!** Wenn die Checkbox gesetzt wurde und der
  Haken entfernt wird, ist im Schema *false* als Wert hinterlegt. Dies kann dazu führen, dass *readonly* bei Objekten
  nicht richtig vererbt wird.
- **Default:** Der Wert mit dem das Formular vorbefüllt wird

![Formular Editor](~@source/modeling/forms/fields/edit-field.png)

Außer dem **Allgemein** Tab, gibt es noch weiter Einstellungsmöglichkeiten.

### Optionen
In den Optionen können Oberflächenelemente konfiguriert und Übersetzungstexte konfiguriert werden.

- **Dense:** Gibt an ob das Formularfeld schmaler angeordnet werden soll
- **Outlined:** Gibt an ob das Formularfeld mit einer Rahmen angezeigt werden soll
- **Standardgröße:** Die Größe auf normalen Endgeräten. 
- **Größe auf kleinen Geräten:** Die Größe auf mobilen Endgeräten

![Formular Editor](~@source/modeling/forms/fields/edit-field-options.png)

### Validierung
Validierungen schränken den Wertebereich der Eingabe ein.

- **Pattern:** Ein regulärer Ausdruck, der die Eingabe einschränkt
- **required:** Gibt an ob das Feld ein Pflichtfeld ist
- **min. Länge:** Die minimale Länge der Eingabe
- **max. Länge:** Die maximale Länge der Eingabe

Die Validierungen können je nach Feldtyp variieren.

![Formular Editor](~@source/modeling/forms/fields/edit-field-validation.png)

## Textfeld

Ein Textfeld ist ein Feld, in das ein Text eingegeben werden kann.
Dieses besteht aus einer Zeile.

![Formular Editor](~@source/modeling/forms/fields/textfield.png)

Im Formular wird das Feld wie folgt angezeigt:

![Formular Editor](~@source/modeling/forms/fields/textfield-form.png)

## Textarea

Eine Textarea ist ein Feld, in das ein Text eingegeben werden kann.
Im Gegensatz zum Textfeld besteht dieses aus mehreren Zeilen.

![Formular Editor](~@source/modeling/forms/fields/textarea.png)

Im Formular wird das Feld wie folgt angezeigt:

![Formular Editor](~@source/modeling/forms/fields/textarea-form.png)


## Zahl

Ein Zahl ist ein Feld, in das eine Ganzzahl eingegeben werden kann.

![Formular Editor](~@source/modeling/forms/fields/number.png)

Im Formular wird das Feld wie folgt angezeigt:

![Formular Editor](~@source/modeling/forms/fields/number-form.png)

## Gleitkommazahl
In dieses Feld kann eine Gleitkommazahl eingegeben werden.
Im Gegensatz zum Zahl Feld, ist hier ein ``,`` als Trennzeichen erlaubt.

![Formular Editor](~@source/modeling/forms/fields/float.png)

Im Formular wird das Feld wie folgt angezeigt:

![Formular Editor](~@source/modeling/forms/fields/float-form.png)

## Checkbox

Eine Checkbox ist ein Feld, das ein Ja/Nein Wert repräsentiert.


## Datum

## Zeit
Über dieses Feld kann eine Uhrzeit eingegeben werden.

![Formular Editor](~@source/modeling/forms/fields/time.png)

Im Formular wird das Feld wie folgt angezeigt, über das Kontextmenü kann die Uhrzeit ausgewählt werden:

![Formular Editor](~@source/modeling/forms/fields/time-form.png)

## Auswahl
In diesem Feld kann eine Auswahl aus vordefinierten Werten getroffen werden.
Diese werden können bei der Erstellung im Contextmenü hinzugefügt werden.

![Formular Editor](~@source/modeling/forms/fields/select.png)

Im Formular wird das Feld wie folgt angezeigt:

![Formular Editor](~@source/modeling/forms/fields/select-form.png)

![Formular Editor](~@source/modeling/forms/fields/select-form2.png)

## Mehrfachauswahl

## Dateien

## Benutzerauswahl

## Mehrfache Benutzerauswahl

## Schalter

## Liste

## Markdown