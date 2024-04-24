# Web Components

Die von DigiWF bereitgestellten [Web Components](https://www.webcomponents.org/introduction) werden mit VueJS und Vite entwickelt.

Um die Web Components nutzen zu können, muss das API-Gateway gestartet werden.
Je nach Komponente ist auch das Starten der DigiWF-Engine sowie des Task Managements erforderlich, um auf die von den Web Components angesprochenen Schnittstellen zugreifen zu können.

## Wichtigste Bibliotheken

* [CoreUI](https://coreui.io/): Modulare Component Library geeignet für Web Component-Entwicklung
* [VueUse](https://vueuse.org/): Bibliothek mit Sammlung an hilfreichen Vue Composables

## LHM eigene Bibliotheken

* [@muenchen/digiwf-engine-api-internal](https://www.npmjs.com/package/@muenchen/digiwf-engine-api-internal): Generierter HTTP-Client für die Engine-API basierend auf [Axios](https://axios-http.com/) inklusive TypeScript-Typen

## Überblick über verfügbare WebComponents und deren Nutzung

Aktuell werden folgende Web Components angeboten:

- **digiwf-service-instances-webcomponent**: Ermöglicht die Anzeige der DigiWF-eigenen Vorgänge

Die angebotenen Web Components können per Custom Properties in ihrem Verhalten angepasst werden.
Darüber hinaus kann das Aussehen der Komponenten (insbesondere Farben und Schriftarten) durch das Setzen von Custom CSS-Variablen an die einbettende Anwendung angepasst werden, was eine visuell nahtlose Integration ermöglicht.
Die Komponenten nutzen Responsive Design und sind daher auf Endgeräten mit verschiedenen Bildschirmgrößen verwendbar.
Bei der Entwicklung wurde auf eine barrierefreie Umsetzung geachtet.

Weitere Informationen zur Integration und Konfiguration der Web Components sind in der technischen [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-apps/packages/apps/digiwf-webcomponent/README.md) auf GitHub zu finden.

## Vorteile von Web Components

- **Skalierbarkeit**: Web Components können effizienter skaliert werden, da sie in kleinen Einheiten betrieben werden und mehrere parallele Anfragen gleichzeitig beantworten können.
- **Performanz**: Web Components werden als kleine Einheiten an den Browser ausgeliefert und sind damit deutlich performanter als schwergewichtige Single-Page-Applications. Dies macht sich zum Beispiel in der Ladezeit bemerkbar.
- **Konfigurierbarkeit**:  Durch den Einsatz von Custom Properties erlauben Web Components ein hohes Maß an Konfigurierbarkeit ihres Ausführungsverhaltens aus der einbettenden Anwendung heraus.
- **Anpassbarkeit**: Durch Techniken wie Custom CSS-Variablen kann das Aussehen von Web Components dynamisch an die einbettende Anwendung angepasst werden, sodass sie als fester Bestandteil wahrgenommen werden können.
- **Wartbarkeit**: Web Components sind in sich geschlossen und können unabhängig voneinander entwickelt und gewartet werden. Dies vereinfacht die Wartung und Weiterentwicklung im Vergleich zu einer verwobenen Single-Page-Application.
- **Sicherheit**: Web Components werden in einem isolierten Kontext ausgeführt, wodurch Interferenzen zwischen verschiedenen Web-Elementen verhindert und das Risiko von Sicherheitslücken gesenkt wird.
- **Integration**: Web Components lassen sich als einfache HTML-Elemente nahtlos in bestehende Anwendungen integrieren. Es erfolgt kein Kontextwechsel bei der Interaktion mit der Web Component.
- **Wiederverwendbarkeit**: Web Components sind wiederverwendbare GUI-Komponenten, die unabhängig von der einbettenden Anwendung eingesetzt werden können.

## Visualisierung der Kommunikation

![Communication between components](~@source/images/platform/components/webcomponents/webcomps-flow.png)

Erklärung der einzelnen Schritte:
1. Nachdem die Domain der einbettenden Anwendung im Browser aufgerufen wurde, wird zunächst das darin enthaltene `<script>`-Element für das Laden der Web Component ausgewertet und ein HTTP GET-Request an das API-Gateway gesendet. Dieser Request enthält den Subpfad `/public/`, wodurch sicherheitsrelevante Mechanismen für diese Anfrage seitens des API-Gateway deaktiviert werden.
2. Das API-Gateway ruft (ebenfalls per HTTP GET) die angeforderten Daten von einem nginx-Webserver ab, der für die Auslieferung der statischen Ressourcen zuständig ist. Das Gateway liefert dann die Web Component an den Client aus und diese wird im Browser angezeigt.
3. Aufgrund der erforderlichen Authentifizierung wartet die Web Component auf ein definiertes Custom Event, das ein OIDC-Access-Token in der Payload enthalten muss. Erst danach beginnt die Web Component mit der Abfrage von DigiWF-spezifischen Daten. Das Token kann der einbettenden Applikation beispielsweise über Logik innerhalb der Webapplikation selbst oder über eine zweite von der Applikation bereitgestellte Web Component (in der Grafik als "Auth Component" bezeichnet) zur Verfügung gestellt werden. Die Web Component verwendet das erhaltene Access-Token im HTTP-Header für künftige Anfragen und aktualisiert die Authentifizierungsdaten automatisch, sobald ein neues Token über dasselbe Ereignis bereitgestellt wird.
4. Mit dem erhaltenen Access-Token sendet die Web Component REST-Anfragen zum Abruf von Nutzdaten an das API-Gateway. Diese Anfragen enthalten den Subpfad `/clients/`, wodurch das API-Gateway auf eine sessionbasierte Kommunikation verzichtet und das Access-Token direkt entgegennimmt. Dieses Token wird über den SSO-Provider geprüft.
5. Nach der Prüfung der Gültigkeit leitet das API-Gateway die Anfrage an den entsprechenden dahinterliegenden Service weiter (z. B. DigiWF Engine). Hierbei wird das Token weitergegeben und erneut vom Service über den SSO-Provider ausgewertet. Nach erfolgreicher Prüfung liefert der Service die angeforderten Daten über das API-Gateway zurück an den Client. Schließlich zeigt die Web Component die erhaltenen Daten an.