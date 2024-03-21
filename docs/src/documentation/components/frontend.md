# Frontend

Das Frontend wird mit VueJs und Vite entwickelt.

Um das Frontend nutzen zu können, müssen das Api Gateway sowie die Digiwf Engine gestartet werden.

## Wichtigste Bibliotheken

* [Vuex](https://vuex.vuejs.org/): Redux für VueJs
* [tanstack-query](https://tanstack.com/query/latest/docs/vue/overview): Middleware Framework zum Cachen von Netzwerkrequests 
* [vuetify](https://vuetifyjs.com/en/) Component Library basierend auf Material Design 

## LHM eigene Bibliotheken

* @muenchen/digiwf-engine-api-internal: generierter Axios Client mit Typen
* @muenchen/digiwf-form-renderer: Darstellung von Nutzerformularen
* @muenchen/digiwf-multi-file-input: Dateieingabefeld für @muenchen/digiwf-form-renderer
* @muenchen/digiwf-date-input: Datumseingabefeld für @muenchen/digiwf-form-renderer

## Session-Handling im Frontend

Für die Authentifizierung wird eine Session verwendet, welche im Api Gateway verwaltet wird. Der JWT Token von Keycloak
wird nicht an den Client weitergeleitet.

Die folgende Abbildung zeigt die Verwaltung der Session:

![Session management frontend](~@source/images/platform/components/frontend/session-mgmt-api-gateway-frontend.png)

## Normalfall bei Netzwerkrequest

Beim Starten der Anwendung zur lokalen Entwicklung muss zuerst das Api Gateway (http://localhost:8083) aufgerufen
werden (1).
Dabei erscheint die Keycloak Login Maske (2). Nach erfolgreicher Anmeldung sieht man entweder auf der Seite des Api
Gateways

* eine HTTP 500 Error Page oder 
* endloses Neuladen der Seite

Damit ist Schritt 3 abgeschlossen.

In beiden Fällen wechselt man zur URL des Frontends (http://localhost:8084) (4).
Das Frontend sollte nun korrekt geladen und die ersten XHR Requests gestartet werden (5).

Jeder Netzwerkrequest vom Browser zum Api Gateway enthält die Cookies und somit die Session. Um Requests an andere
Domains zu vermeiden, wird bei jedem Netzwerkrequest der Proxyserver des Vite Development Servers angesprochen (5).
Dieser leitet den Request unverarbeitet weiter zum Api Gateway (6).
Das Api Gateway entfernt die Cookie Headers aus dem Request und fügt den gültigen Access-Token hinzu. Der veränderte
Request wird an die Digiwf Engine weitergeleitet (8). Nach Verarbeitung des Requests durch das Backend sendet die Engine
eine Response zurück zum Api Gateway (9).
Dieses ersetzt wieder den Security Header und setzt einen "set-cookie" Header mit der neuen Session (10).
Die Response wird über den Vite Proxy Server an das Frontend weitergeleitet (11, 12).

Durch den "set-cookie" Header erhält das Frontend die neue Session.

## Request nach Ablauf der Session

Wenn die Session abgelaufen ist, muss diese "halbautomatisch erneuert werden".

> **_Note:_** Hier ist wichtig, dass es sich um die Session und nicht um den Access- oder Refreshtoken handelt.

Das folgende Diagramm beschreibt den Ablauf der Netzwerkrequests bei Verfall der Session.

![Session timeout in frontend](~@source/images/platform/components/frontend/session-timeoutapi-gateway-frontend.png)

Der Browser sendet einen Api Request mit abgelaufener Session an den Vite Proxy (1). Dieser leitet den Request
unverändert an das Api Gateway weiter (2).
Bei der Überprüfung im Api Gateway wird festgestellt, dass die Session abgelaufen ist. Daraufhin wird eine Response mit
dem Status-Code 302 und einem _location_-Header an den Client gesendet (4,5). Der _location_-Header enthält den Zielpfad
für den erneuten Aufruf. Dieser wird automatisch vom Browser aufgerufen (6) und erhält eine Response mit dem Status-Code
404.
Daher wird in Axios eine Exception geworfen und der global definierte Error Interceptor ausgeführt. Dieser führt im
Fehlerfall einen Vergleich zwischen Ziel-URL und Ist-URL des gescheiterten Requests durch. Wenn sie nicht
übereinstimmen, bedeutet das, dass eine Weiterleitung stattgefunden hat (7).
In diesem Fall wird eine Meldung angezeigt, die den Nutzer auffordert, sich erneut anzumelden, wenn er auf die
Schaltfläche klickt (8).
Wenn der Nutzer auf die Schaltfläche klickt, öffnet sich ein neuer Tab, der das Api Gateway aufruft (9). Wenn die
Anmeldung erfolgreich ist, schließt sich das Fenster automatisch.
Bei diesem Aufruf wird die Session über den Set-Cookie-Header im Response Header erneuert. Die Meldung zum erneuten
Anmelden verschwindet (10).
