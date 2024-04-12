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