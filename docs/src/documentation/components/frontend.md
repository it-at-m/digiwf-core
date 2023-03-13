# Frontend 

Das Frontend wird mittels VueJs und Vite entwickelt. 

Für das Frontend ist es nötig das Api Gateway sowie die Digiwf Engine gestartet zu haben. 

## Wichtigste Bibliotheken

* [Vuex](https://vuex.vuejs.org/): Redux für VueJs
* [tanstack-query](https://tanstack.com/query/latest/docs/vue/overview): Middleware Framework zum Cachen von Netzwerkrequests 
* [vuetify](https://vuetifyjs.com/en/) Component Library basierend auf Material Design 

## LHM eigene Bibliotheken

* @muenchen/digiwf-engine-api-internal: generierter Axios Client mit Typen
* @muenchen/digiwf-form-renderer: Darstellung von Nutzerformularen
* @muenchen/digiwf-multi-file-input: Dateieingabefeld für @muenchen/digiwf-form-renderer
* @muenchen/digiwf-date-input: Datumseingabefeld für @muenchen/digiwf-form-renderer

## Session handling im Frontend

Für die Authentifizierung wird eine Session verwendet, welche im Api Gateway verwaltet wird. Der JWT Token von Keycloak wird nicht an den Client weitergereicht. 

In der folgenden Abbildung ist die Verwaltung der Session abgebildet: 

![Session management frontend](~@source/images/platform/components/frontend/session-mgmt-api-gateway-frontend.png)


## Normalfall bei Netzwerkrequest

Beim Starten der Anwendung zur lokalen Entwicklung muss man als Erstes das Api Gateway (http://localhost:8082) aufrufen (1). 
Dabei erscheint die Keycloak Login Maske (2). Nach erfolgreichen Login sieht man auf der Seite des Api Gateways entweder
* eine HTTP 500 Error Page
* endloses Neuladen von der Seite.
Somit ist Schritt 3 abgeschlossen.

In beiden Fällen wechselt man zur URL des Frontends (http://localhost:8081) (4).
Dabei sollte das Frontend korrekt geladen und die ersten XHR Requests gestartet werden (5).

Jeder Netzwerkrequest vom Browser zum Api Gateway liefert die Cookies und somit die Session mit. Um Requests an andere Domains zu vermeiden wird bei jedem Netzwerkrequest der Proxy Server des Vite Development Servers angesprochen (5). Dieser leitet den Request unverarbeitet weiter zum Api Gateway (6).
Das Api Gateway löscht die Cookie Headers aus dem Request und ergänzt den validen Accesstoken. Der veränderte Request wird weiter an die Digiwf Engine geschickt (8). Nach Verarbeitung des Requests durch das Backend wird eine Response zurück zum Api Gateway geschickt (9). 
Dieses ersetzt wieder den Security Header und setzt ein "set-cookie" Header mit der neuen Session (10).
Die Response wird über den Vite Proxy Server an das Frontend weitergeleitet (11, 12).

Durch den "set-cookie" Header erhält das Frontend die neue Session.

## Request nach Ablauf der Session

Wenn die Session abgelaufen ist, muss diese "Semi-automatisch erneuert werden." 

> **_Note:_** An dieser Stelle ist wichtig, dass hier von der Session und nicht von dem Access- oder Refreshtoken gesprochen wird.

In dem folgenden Diagramm ist der Ablauf der Netzwerkrequests bei einem Session Timeout beschrieben.

![Session timeout in frontend](~@source/images/platform/components/frontend/session-timeoutapi-gateway-frontend.png)

Der Browser sendet einen Api Request mit abgelaufener Session an den Vite Proxy (1). Dieser leitet den Request unverändert an das Api Gateway weiter (2). 
Bei der Überprüfung im Api Gateway wird festgestellt, dass die Session abgelaufen ist. Daraufhin wird eine Response mit Status Code 302 und _location_ als Header an den Client gesendet (4,5). In der _location_ steht der Zielpfad für den erneuten Aufruf statt. Dieser wird automatisch vom Browser aufgerufen (6) und wird mit einer Response mit Status Code 404 beantwortet.
Deswegen wird in Axios eine Exception geworfen und der global definierte Error Interceptor ausgeführt. Dieser führt im Fehlerfall einen Ziel-Ist-URL-Vergleich des gescheiterten Requests durch. Sollten diese nicht übereinstimmen, kann davon ausgegangen werden, dass eine Weiterleitung stattfand (7). 
In diesem Fall wird eine Meldung angezeigt, dass sich der Nutzer über ein Button Klick neu anmelden soll (8).
Wenn der Nutzer auf den Button klickt, öffnet sich ein neuer Tab, welcher das Api Gateway aufruft (9). Bei Erfolg schließt sich das Fenster automatisch.
Bei dem Aufruf wird die Session via Set-Cookie in dem Response Header erneuert. Die Meldung zum Relogin verschwindet (10).
