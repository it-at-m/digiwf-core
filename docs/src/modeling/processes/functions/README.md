# Funktionen

DigiWF stellt verschiedene Funktionen für die Modellierung bereit, um mit einer Prozessinstanz zu interagieren.
Diese können bei der Modellierung von Prozessen verwendet werden.

## Status setzen

::: warning

Die API wird sich wahrscheinlich ändern und durch ein Template ersetzt werden.

:::

Über die [Konfiguration](/modeling/processes/config/) ist es möglich eine Statuskonfiguration zu hinterlegen.
Diese wird verwendet, um dem Sachbearbeiter den aktuellen Status des Vorgangs zu visualisieren.

Der Status kann in DigiWF über die Funktion `process.setStatus('statusKey')` gesetzt werden. Diese Expression kann an
unterschiedlichen Stellen im Modell verwendet werden. Bspw. kann über ein Zwischenereignis das Setzen des Status
visualisiert werden.

![Set Status](~@source/modeling/processes/functions/set_status.png)

## Beschreibung setzen

::: warning

Die API wird sich wahrscheinlich ändern

:::

Um Prozessinstanzen in der Ansicht *Meine Vorgänge* besser suchen zu können, ist es möglich eine Beschreibung zu
hinterlegen. Diese wird als Text angezeigt und kann über die Suche gefunden werden.

Hierfür biete DigiWF die Funktion `process.setDescription('description')`. Diese kann an unterschiedlichen Stellen im
Modell verwendet werden, bspw. als Listener in jedem beliebigen Element.

![Set Description](~@source/modeling/processes/functions/set_description.png)

::: tip
Beim Setzen einer Beschreibung kann auf beliebige Daten zugreifen, die sich in der Prozessinstanz befinden. Folgender
Aufruf ist bspw. möglich: `process.setDescription(FormField_Name+' '+FormField_Nachname)`
:::