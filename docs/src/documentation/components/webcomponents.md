# Web Components

Die von DigiWF bereitgestellten [Web Components](https://www.webcomponents.org/introduction) werden mit VueJS und Vite entwickelt.

Um die Web Components nutzen zu können muss das API-Gateway gestartet werden.
Außerdem ist je nach Komponente auch noch das Starten der Digiwf Engine sowie des Task Managements notwendig, um einen Zugriff auf die von den Web Components angesprochenen Schnittstellen zu ermöglichen.

## Wichtigste Bibliotheken

* [CoreUI](https://coreui.io/): Modulare Component Library geeignet für Web Component-Entwicklung
* [VueUse](https://vueuse.org/): Bibliothek mit Sammlung an hilfreichen Vue Composables

## LHM eigene Bibliotheken

* [@muenchen/digiwf-engine-api-internal](https://www.npmjs.com/package/@muenchen/digiwf-engine-api-internal): Generierter HTTP-Client für die Engine-API basierend auf [Axios](https://axios-http.com/) inklusive TypeScript-Typen

## Überblick über verfügbare WebComponents und deren Nutzung

Aktuell werden folgende WebComponents angeboten:

- **digiwf-service-instances-webcomponent**: Ermöglicht die Anzeige der DigiWF-eigenen Vorgänge

Die angebotenen Web Components können per Custom Properties im Verhalten angepasst werden. 
Zusätzlich lässt sich das Aussehen der Komponenten (insbesondere Farben und Fonts) über das Setzen von Custom CSS-Variablen an die einbettende Anwendung anpassen, wodurch eine optisch nahtlose Integration ermöglicht wird.
Die Komponenten nutzen Responsive Design und sind daher auf Endgeräten mit verschiedenen Bildschirmgrößen nutzbar.
Zudem wurden bei der Entwicklung auf eine barrierefreihe Umsetzung geachtet.

Genauere Informationen zur der Einbindung und den individuellen Konfigurationsmöglichkeiten der Web Components kann der technischen [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-apps/packages/apps/digiwf-webcomponent/README.md) auf GitHub entnommen werden.

## Vorteile von Web Components

- **Skalierbarkeit**: Web Components können effizienter skaliert werden, da sie in kleinen Einheiten betrieben und damit mehrere parallele Anfragen gleichzeitig beantwortet werden können.
- **Performanz**: Web Components werden als kleine Einheiten an den Browser ausgeliefert und sind damit deutlich performanter als schwergewichtige Single-Page-Applications. Dies macht sich zum Beispiel in der Ladezeit bemerkbar.
- **Konfigurierbarkeit**: Web Components erlauben durch den Einsatz von Custom Properties ein hohes Maß an Konfigurierbarkeit des Ausführungsverhaltens aus der einbettenden Anwendung heraus.
- **Anpassbarkeit**: Durch Techniken wie Custom CSS-Variablen kann das Aussehen von Web Components dynamisch an die einbettende Anwendung angepasst werden, sodass diese als fester Bestandteil wahrgenommen werden können.
- **Wartbarkeit**: Web Components sind in sich geschlossen und können unabhängig voneinander entwickelt und gewartet werden. Das macht die Wartung und Weiterentwicklung einfacher und effizienter als bei einer in sich verwobenen Single-Page-Application.
- **Sicherheit**: Web Components werden in einem isolierten Kontext ausgeführt wodurch Interferenzen zwischen verschiedenen Web-Elementen verhindert werden und das Risiko von Sicherheitslücken gesenkt wird.
- **Integration**: Web Components lassen sich als einfaches HTML-Element nahtlos in bestehenden Anwendungen integrieren. Es erfolgt kein Kontextwechsel bei einer Interaktion mit der Web Component.
- **Wiederverwendbarkeit**: Web Components sind wiederverwendbare GUI-Teile, die unabhängig von der einbettenden Anwendung eingesetzt werden können.

## Visualisierung der Kommunikation

![Communication between components](~@source/images/platform/components/webcomponents/webcomps-flow.png)

Erklärung der einzelnen Schritte:
1. Nachdem die Domäne der einbettenden Anwendung im Browser aufgerufen wurde, wird zunächst das darin enthaltene `<script>`-Element für das Laden der Web Component ausgewertet und ein HTTP GET-Request an das API-Gateway gesendet. Dieser Request enthält den Subpfad `/public/`, wodurch Sicherheits-relevante Mechanismen für diese Anfrage seitens des API-Gateway abgeschalten werden.
2. Das API-Gateway frägt (ebenfalls per HTTP GET) die angeforderten statischen Ressourcen bei einem nginx-Webserver an, der für die Auslieferung der statischen Ressourcen zuständig ist. Anschließend liefert das Gateway die Web Component an den Client aus und die diese wird im Browser angezeigt.
3. Wegen notwendiger Authentifizierung wartet die Web Component auf ein definiertes Custom Event, welches ein OIDC-Access-Token in der Payload enthalten muss. Erst danach beginnt die Web Component mit der Abfrage von DigiWF-spezifischen Daten. Das Token kann die einbettende Applikation beispielsweise über Logik innerhalb der Webapplikation selbst oder über eine zweite von der Applikation bereitgestellte Web Component (in der Grafik als "Auth Component" bezeichnet) geliefert werden. Die Web Component nutzt das erhaltene Access-Token im HTTP-Header für künftige Request und aktualisiert Authentifizierungsdaten automatisch, sobald ein neues Token über selbiges Event bereit gestellt wird.
4. Die Web Component sendet mit dem erhaltenen Access Token REST-Anfragen zum Abruf von Nutzdaten an das API-Gateway. Diese Requests enthalten den Subpfad `/clients/`, wodurch das API-Gateway auf eine Session-basierte Kommunikation verzichtet und das Access Token direkt entgegennimmt. Dieses Token wird über den SSO-Provider geprüft.
5. Nach Prüfung der Gültigkeit wird die Anfrage vom API-Gateway an den entsprechenden dahinterliegenden Service weitergeleitet (z.B. DigiWF Engine). Hierbei wird das Token weiter durchgereicht und erneut vom Service über den SSO Provider ausgewertet. Nach erfolgreicher Prüfung werden die angefragten Daten vom Service über das API-Gateway zurück an den Client ausgeliefert. Die Web Component zeigt schließlich die erhaltenen Daten an.