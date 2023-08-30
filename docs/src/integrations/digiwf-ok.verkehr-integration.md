# DigiWF OK.Verkehr Integration
## Documentation

Basically, two artifacts are available.
This is on the one hand the Spring-Boot-Starter `digiwf-okverkehr-integration-starter`
and on the other hand the service `digiwf-okverkehr-integration-service` which is provided as an
[Image](https://hub.docker.com/repository/docker/itatm/digiwf-okverkehr-integration-service).

#### Error handling

The errors occurring during the rest request are divided into three error categories.
These are client-side errors, server-side errors and errors that cannot be assigned to either the client or the server.
Each of these three error categories is assigned its own exception, which is then thrown when the methods in the
repository class `OkVerkehrHalterRepository` are called.

If client-side errors, server-side errors or errors that cannot be assigned to either the client or the server occur
during the rest request within the service, an error response is returned to the caller via the event bus.

```json
{
  "message": "THE GENERIC ERROR MESSAGE"
}
```


### Service provided as an image

The service is provided via Dockerhub as
an [Image](https://hub.docker.com/repository/docker/itatm/digiwf-okverkehr-integration-service).
The source code for the service can be found in
submodule [digiwf-okverkehr-integration-service](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-okverkehr-integration/digiwf-okverkehr-integration-service).

The requests to OK.VERKEHR are expected by the service over kafka event bus messages.
To provide the event bus functionality via kafka,
the [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils) are used.

#### Service Configuration

An example configuration can be found within the properties files.

* `application.yml`: `digiwf-okverkehr-integration-service/src/main/resources/application.yml`
* `application-local.yml`: `digiwf-okverkehr-integration-service/src/main/resources/application.yml`

The file `application.yml` contains the configuration according graceful shutdown, metrics, ports, ...

The file `application-local.yml` provides the event bus and OK.VERKEHR relevant configuration.
The event bus configuration is implemented
according [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started).

#### Service API usage

The OK.VERKEHR requests have to be made via the element template defined in `okVerkehrIntegration.json`.
The service and the template are providing the following types of requests.
The request type can be defined via the element template dropdown in field `Event Type`.

* `getHalter`

For each request type, the payload has to be defined in the element templates request field as a JSON object.
The response is also an JSON object.
A crucial and mandatory JSON object attribute for a request is `eventType`.
This attribute is necessary for correct deserialization of the requests JSON payload within the integration service.

##### getHalter

Searches for Fahrzeughalter (vehicle owner) is based on search parameters.

The following JSON object shows the example payload set at the element templates request field.
The search parameters within JSON object allocated to JSON key `searchPerson` are optional,
if parameters are not needed, they can be omitted.

Mandatory attributes are `kennzeichen` and `suchzeitpunkt`.
The attribute `suchzeitpunkt` has to be a date in ISO format.
Optional but recommended attributes are `benutzer` and `verfahren`.
These two recommended attributes are for logging purposes.

```json
{
  "eventType": "getHalter",
  "halterPersonAnfrage": {
    "benutzer": "testuser",
    "kennzeichen": "M AA1000",
    "suchzeitpunkt": "1955-12-13",
    "verfahren": "VES"
  }
}
```

The response is as follows.

```json
{
  "geaenderterHalter": {
    "adressierungszusaetze": "e",
    "ags": "sed reprehe",
    "berufschluessel": "veniam ipsum dolore consectetur",
    "doktorgrad": "eiusmod elit",
    "geburtsdatum": "do et proident commodo pariatur",
    "geburtsname": "elit",
    "geburtsnamensbestandteil": "laborum",
    "geburtsort": "proident in ut ullamco",
    "geschlechtSchluessel": "aliquip c",
    "hausnummer": "occaecat",
    "kuenstlername": "sit incididunt in",
    "nachname": "minim irure",
    "nachnameNormiert": "reprehenderit tempor pariatur nisi eiusmod",
    "nameJuristischePerson": "ex culpa",
    "nameJuristischePersonNormiert": "ea est cons",
    "nameVereinigung": "labore velit eiusmod",
    "nameVereinigungNormiert": "consequat incididunt",
    "namensbestandteil": "minim ut nulla reprehenderit",
    "ordensname": "voluptate non",
    "personenTypSchluessel": "mollit nulla",
    "postleitzahl": "reprehenderit ea",
    "staatSchluessel": "elit",
    "strasse": "qui eiusmod pariatur",
    "strassenschluessel": "commodo reprehenderit nulla",
    "vorname": "irure mollit dolor",
    "vornameNormiert": "nostrud",
    "wohnort": "minim ipsum Lorem in id",
    "wohnungsgeber": "sint"
  },
  "halter": {
    "adressierungszusaetze": "velit dolore ipsum sint",
    "ags": "cillum non",
    "berufschluessel": "consectetur sint esse",
    "doktorgrad": "ut eiusmod",
    "geburtsdatum": "mollit do n",
    "geburtsname": "Exc",
    "geburtsnamensbestandteil": "sunt eu ve",
    "geburtsort": "Lorem et labore",
    "geschlechtSchluessel": "in",
    "hausnummer": "Duis",
    "kuenstlername": "reprehenderit tempor amet dolore",
    "nachname": "nulla sit in",
    "nachnameNormiert": "ut incididunt commodo in",
    "nameJuristischePerson": "deser",
    "nameJuristischePersonNormiert": "Ut adipisicing sunt incididunt",
    "nameVereinigung": "vel",
    "nameVereinigungNormiert": "dolor magna",
    "namensbestandteil": "aute anim sunt esse",
    "ordensname": "Ut qui eu in",
    "personenTypSchluessel": "qui reprehenderit in aute",
    "postleitzahl": "dolor dolore esse anim elit",
    "staatSchluessel": "sunt aliquip proident",
    "strasse": "Ut",
    "strassenschluessel": "pariatur ullamco",
    "vorname": "ad incididunt proident ex",
    "vornameNormiert": "commodo",
    "wohnort": "aute dolore id",
    "wohnungsgeber": "elit laborum ea"
  },
  "meldung": {
    "text": "deserunt velit",
    "typ": "et Duis sit laboris"
  },
  "standort": {
    "adressierungszusaetze": "officia eu",
    "ags": "do in pariatur ipsum",
    "berufschluessel": "elit sit id labore in",
    "doktorgrad": "consequat qui nostrud",
    "geburtsdatum": "pariatur consectetur esse",
    "geburtsname": "commodo laboris sit nulla in",
    "geburtsnamensbestandteil": "nisi eiusmod ullamc",
    "geburtsort": "ullamco",
    "geschlechtSchluessel": "exercitation deserunt Lorem Ut",
    "hausnummer": "aute",
    "kuenstlername": "laboris consectetur proident magna exercitation",
    "nachname": "elit in q",
    "nachnameNormiert": "adipisicing labore commodo aliqua",
    "nameJuristischePerson": "cupidatat nostrud est consectetur aliquip",
    "nameJuristischePersonNormiert": "eiusmod consectetur",
    "nameVereinigung": "proident consectetur sunt enim",
    "nameVereinigungNormiert": "voluptate nulla officia",
    "namensbestandteil": "veniam",
    "ordensname": "esse nulla voluptate irure",
    "personenTypSchluessel": "Lorem",
    "postleitzahl": "culpa officia tempor ipsum sint",
    "staatSchluessel": "Ut",
    "strasse": "in sed pariatur aliquip",
    "strassenschluessel": "sit ea Duis",
    "vorname": "laboris aute commodo",
    "vornameNormiert": "irure dolore anim in aliqua",
    "wohnort": "cupidatat adipisicing ut",
    "wohnungsgeber": "sit Duis pariatur"
  },
  "technik": {
    "abeMerkmalSchluessel": "reprehenderit consectetur",
    "abgasText": "ex proident ipsum",
    "achslast1": -708442,
    "achslast2": 98276889,
    "achslast3": -51566926,
    "anhlastgebremst": 87938659,
    "anhlastungebremst": 48884669,
    "antriebsachsen": -60188571,
    "anzahlachsen": -80580112,
    "aufbauSchluessel": "ad ut",
    "aufbauText": "eiusmod in consequat",
    "beiblatt": "in",
    "bemerkungen": "aute consequat ad incididunt",
    "bereifachse1": "sed in",
    "bereifachse2": "cillum ut voluptate laboris",
    "bereifachse3": "esse nisi",
    "breitemax": -95364346,
    "breitemin": 72427772,
    "co2Kombi": -77758568,
    "drehzleistung": 3521842,
    "drehzstandgeraeusch": 73757722,
    "emiklasseSchluessel": "officia elit reprehenderit",
    "emiklasseText": "officia do qui",
    "fahrgeraeusch": "nisi irure",
    "fahrzeugklasseSchluessel": "in Duis sed eiusmod",
    "fahrzeugklasseText": "anim amet do cillum",
    "fin": "pariatur sunt commod",
    "genehmigungsdatum": "2015-12-18",
    "genehmigungsnr": "Lorem consectetur est adipisicing",
    "handelsname": "consectetur aute n",
    "hauptfarbeSchluessel": "nostrud cillum laboris",
    "herstellerSchluessel": "fugiat sit officia aliqua",
    "herstellerText": "esse in",
    "hoechstgeschwindigkeit": -9497770,
    "hoehemax": -9572795,
    "hoehemin": -49779726,
    "hubraum": -81402455,
    "kraftstoffSchluessel": "adipisicing minim",
    "kraftstoffText": "in sit est",
    "laengemax": -93221543,
    "laengemin": 34696789,
    "leistungsgewicht": -14732894.477481157,
    "markeText": "dolore cupidatat Lorem id",
    "massemax": -97860075,
    "massemin": 6623981,
    "nebenfarbeSchluessel": "non officia consectetur",
    "nennleistung": -898793,
    "pzfin": "amet",
    "pzhertypvvs": "Lorem laboris",
    "rauminhalt": -78113252.72489938,
    "sitzplaetze": -73916447,
    "standgeraeusch": "voluptate adipisicing ipsum eu",
    "stehplaetze": 78334119,
    "stuetzlast": 16299572,
    "techachslast1": 94428093,
    "techachslast2": -76464979,
    "techachslast3": -24146743,
    "techzulgesamtmasse": -43104277,
    "typSchluessel": "ea irure incididunt",
    "typText": "Excepteur dolor ipsum",
    "varianteText": "pariatur cillum",
    "versionText": "elit ad adipisicing minim",
    "vvsSchluessel": "nulla cupidatat",
    "zulgesamtmasse": 65223557
  },
  "vertreter": {
    "adressierungszusaetze": "cupidatat exercitation nulla",
    "ags": "dolore commodo",
    "berufschluessel": "aliquip sunt vo",
    "doktorgrad": "",
    "geburtsdatum": "nulla elit ad nostrud",
    "geburtsname": "ea qui sed non",
    "geburtsnamensbestandteil": "qui est id",
    "geburtsort": "esse aliquip",
    "geschlechtSchluessel": "exercitation veniam nulla",
    "hausnummer": "cillum est magna",
    "kuenstlername": "deserunt consequat",
    "nachname": "enim anim",
    "nachnameNormiert": "sint sunt sed",
    "nameJuristischePerson": "aliqua cillum",
    "nameJuristischePersonNormiert": "amet est id occaecat",
    "nameVereinigung": "sed voluptate tempor",
    "nameVereinigungNormiert": "deserunt in",
    "namensbestandteil": "proident ut",
    "ordensname": "culpa incididunt ",
    "personenTypSchluessel": "in sint",
    "postleitzahl": "consectetur elit",
    "staatSchluessel": "sit",
    "strasse": "incid",
    "strassenschluessel": "in ipsum commodo",
    "vorname": "quis veniam nulla e",
    "vornameNormiert": "culpa est eiusmod Ut exercitation",
    "wohnort": "Excepteur cillu",
    "wohnungsgeber": "magna qui Lorem"
  },
  "vorlaeufigerHalter": {
    "adressierungszusaetze": "mollit ipsu",
    "ags": "et Duis",
    "berufschluessel": "incididunt consequat id",
    "doktorgrad": "repre",
    "geburtsdatum": "ea ex sit",
    "geburtsname": "eu nisi tempor Excepteur",
    "geburtsnamensbestandteil": "Lorem nisi do deserunt dolor",
    "geburtsort": "id veniam",
    "geschlechtSchluessel": "nulla quis",
    "hausnummer": "fugiat",
    "kuenstlername": "",
    "nachname": "anim nisi aute adipisicing qui",
    "nachnameNormiert": "fugiat consequat id incididunt",
    "nameJuristischePerson": "ad ut incididunt cillum est",
    "nameJuristischePersonNormiert": "eu est elit laboris",
    "nameVereinigung": "sint exercitation proident ut",
    "nameVereinigungNormiert": "proident nulla",
    "namensbestandteil": "voluptate eu",
    "ordensname": "nisi eiusmod cupidatat",
    "personenTypSchluessel": "in dolor velit Lor",
    "postleitzahl": "voluptate ullamco incididunt v",
    "staatSchluessel": "ut esse aliquip",
    "strasse": "ullamco aliqua",
    "strassenschluessel": "dolor tempor",
    "vorname": "consequat",
    "vornameNormiert": "ullamco",
    "wohnort": "amet sint et",
    "wohnungsgeber": "dolore commodo"
  },
  "zulassung": {
    "aenderungZeitpunkt": "1943-08-08",
    "auskunftsperreMerkmal": true,
    "ausserbetriebsetzungDatum": "1990-07-14",
    "entstempelungDatum": "1975-07-12",
    "erstzulassungDatum": "1988-08-16",
    "fahrzeugDiebstahlMerkmal": true,
    "fahrzeugKennzeichenStatus": "minim quis sit",
    "kennzeichen": "dolore laborum",
    "kennzeichenDiebstahlMerkmal": false,
    "naechsteHU": "incididunt ut",
    "naechsteSP": "ut veniam Ut in dolor",
    "tarnkennzeichenMerkmal": true,
    "verkaufDatum": "1949-12-18",
    "verkaufsanzeigeMerkmal": true,
    "verwendungSchluessel": "id in ullamco",
    "verwendungText": "laboris adipisicing in esse do",
    "zb2Nummer": "eiusmod ad aliquip",
    "zulassungAufHalterDatum": "1968-12-27"
  }
}
```