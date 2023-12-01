# DigiWF OK.EWO Integration

## Documentation

Basically, two artifacts are available.
This is on the one hand the Spring-Boot-Starter `digiwf-okewo-integration-starter`
and on the other hand the service `digiwf-okewo-integration-service` which is provided as an
[Image](https://hub.docker.com/repository/docker/itatm/digiwf-okewo-integration-service).

#### Error handling

The errors occurring during the rest request are divided into three error categories.
These are client-side errors, server-side errors and errors that cannot be assigned to either the client or the server.
Each of these three error categories is assigned its own exception, which is then thrown when the methods in the
repository classes `OkEwoPersonRepository` and `OkEwoPersonErweitertRepository` are called.

### Service provided as an image

The service is provided via Dockerhub as
an [Image](https://hub.docker.com/repository/docker/itatm/digiwf-okewo-integration-service).
The source code for the service can be found in
submodule [digiwf-okewo-integration-service](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-okewo-integration/digiwf-okewo-integration-service)
.

The requests to OK.EWO are expected by the service over kafka event bus messages.
To provide the event bus functionality via kafka,
the [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils) are used.

#### Service Configuration

An example configuration can be found within the properties files.

* `application.yml`: `digiwf-okewo-integration-service/src/main/resources/application.yml`
* `application-local.yml`: `digiwf-okewo-integration-service/src/main/resources/application.yml`

The file `application.yml` contains the configuration according graceful shutdown, metrics, ports, ...

The file `application-local.yml` provides the event bus and OK.EWO relevant configuration.
The event bus configuration is implemented
according [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started).

#### Service API usage

The OK.EWO requests have to be made via the element template defined in `okEwoIntegration.json`.
The service and the template are providing four types of requests.
The request type can be defined via the element template dropdown in field `Event Type`.

* `getPerson`
* `searchPerson`
* `getPersonErweitert`
* `searchPersonErweitert`

For each request type, the payload has to be defined in the element templates request field as a JSON object.
The response is also an JSON object.
A crucial and mandatory JSON object attribute for a request is `eventType`.
This attribute is necessary for correct deserialization of the requests JSON payload within the integration service.

The date and time information within the request and response payloads are provided in ISO standard.

##### getPerson

Gets a Person on the basis of an Ordnungsmerkmal.

The following JSON object shows the example payload set at the element templates request field.

```json
{
  "eventType": "getPerson",
  "ordnungsmerkmal": "99"
}
```

The response is as follows.

```json
{
  "auskunftssperre": "ut exercitation fugiat",
  "ordnungsmerkmal": "dolore eu sed deserunt",
  "personstatus": "KEIN_STATUS",
  "geburtsdaten": {
    "datum": {
      "datum": "minim consequat cu"
    },
    "ort": "dolor cillum deserunt",
    "staat": "adipisicing pariatur"
  },
  "geschlecht": "officia nostrud",
  "sterbedaten": {
    "datum": "1969-12-24T12:54:58.875Z",
    "ort": "Excepteur eu"
  },
  "familienstanddaten": {
    "familienstand": "dolore incididunt Excepteur",
    "datum": "1987-01-18T07:11:03.989Z"
  },
  "namen": {
    "vornamen": [
      {
        "vorname": "cillum eiusmod elit nulla",
        "rufname": true
      },
      {
        "vorname": "ut magna",
        "rufname": true
      }
    ],
    "familienname": {
      "name": "labore consectetur sint quis",
      "namensbestandteil": "proident consequat",
      "unstrukturierteSchreibweise": "ipsum velit officia aute in"
    },
    "geburtsname": {
      "name": "tempor est",
      "namensbestandteil": "laboris nulla",
      "unstrukturierteSchreibweise": "deserunt dolor"
    },
    "ehename": {
      "name": "sunt occaecat sit",
      "namensbestandteil": "exercitation tempor",
      "unstrukturierteSchreibweise": "deserunt"
    },
    "familiennameNachPass": {
      "name": "eu ut",
      "namensbestandteil": "minim",
      "unstrukturierteSchreibweise": "labore ipsum"
    },
    "ordensname": "ullamco ut",
    "kuenstlername": "occaecat consequat irure",
    "doktorgrad": "ex fugiat culpa"
  },
  "melderegisterAnschrift": {
    "gemeindeschluessel": "reprehenderit dolor sed Excepteur id",
    "hausnummer": "qui officia do",
    "buchstabeHausnummer": "laborum nostrud Duis mollit",
    "teilnummerHausnummer": "et laborum",
    "ort": "irure labore",
    "ortsteil": "aliqua ipsum aliquip",
    "postleitzahl": "est reprehenderit id",
    "stockwerk": "incididunt dolore",
    "appartmentnummer": "exercitation irure voluptate labore",
    "strasse": "amet cillum Ut",
    "wohnungsgeber": "cillum sunt",
    "zusatz": "dolore ad deserunt",
    "staat": "au",
    "anschriftUnbekannt": true,
    "sperrvermerk": true
  },
  "sprengstoffrechtlicheErlaubnis": {
    "angelegtAm": "1947-07-03T13:06:05.751Z"
  },
  "waffenrechtlicheErlaubnis": {
    "angelegtAm": "1985-04-03T20:37:50.080Z"
  },
  "waffenbesitzVerbot": {
    "angelegtAm": "2008-11-12T22:45:47.690Z",
    "behoerde": "anim magna aute",
    "aktenzeichen": "ipsum adipisicing Lorem"
  },
  "staatsangehoerigkeiten": [
    {
      "schluessel": "Ut non qui",
      "text": "pariatur id incididunt"
    },
    {
      "schluessel": "aliquip",
      "text": "consequat elit ut ex"
    }
  ]
}
```

##### searchPerson

Searches Persons based on search parameters.

The following JSON object shows the example payload set at the element templates request field.
The search parameters within JSON object allocated to JSON key `searchPerson` are optional,
if parameters are not needed, they can be omitted.

A mandatory attribute is `datensatzstatus` with the characteristics `AKTUELL`, `INAKTUELL` or `OHNE_EINSCHRAENKUNG`.

```json
{
  "eventType": "searchPerson",
  "searchPerson": {
    "suchkriterien": {
      "datensatzstatus": "AKTUELL",
      "familienname": "veniam elit cupidatat nostrud",
      "geburtsdatum": {
        "datum": "proident Ut quis"
      },
      "geschlecht": "esse enim",
      "vorname": "aute eu laboris",
      "buchstabeVon": "tempor",
      "buchstabeBis": "non ipsum nostrud",
      "hausnummerVon": "in laboris dolore et ipsum",
      "hausnummerBis": "in sed commodo consectetur",
      "strassenschluessel": "nulla minim esse pariatur",
      "teilnummerVon": "incididunt anim",
      "teilnummerBis": "labore incididunt laboris id",
      "wohnort": "et incididunt",
      "postleitzahl": "Ut mollit nisi"
    }
  }
}
```

The response is as follows.

```json
{
  "personen": [
    {
      "auskunftssperre": "exercitati",
      "ordnungsmerkmal": "cillum dolor",
      "personstatus": "AKTUELLER_NICHTMELDEPFLICHTIGER_FESTER_EINWOHNER",
      "geburtsdaten": {
        "datum": {
          "datum": "fugiat irure"
        },
        "ort": "culpa mollit quis fugiat",
        "staat": "eu deserunt nisi esse"
      },
      "geschlecht": "deserunt in ullamco",
      "sterbedaten": {
        "datum": "1984-07-31T20:01:56.061Z",
        "ort": "quis veniam"
      },
      "familienstanddaten": {
        "familienstand": "magna est ulla",
        "datum": "2001-10-08T11:10:06.520Z"
      },
      "namen": {
        "vornamen": [
          {
            "vorname": "cillum laborum Lorem aliqua",
            "rufname": true
          },
          {
            "vorname": "ea nostrud voluptate",
            "rufname": false
          }
        ],
        "familienname": {
          "name": "nulla",
          "namensbestandteil": "id",
          "unstrukturierteSchreibweise": "nostrud fugiat"
        },
        "geburtsname": {
          "name": "cillum dolore",
          "namensbestandteil": "Ut elit deserunt",
          "unstrukturierteSchreibweise": "irure fugiat eiusmod aliqua"
        },
        "ehename": {
          "name": "nostrud aute",
          "namensbestandteil": "et eu adipisicing esse Excepteur",
          "unstrukturierteSchreibweise": "magna"
        },
        "familiennameNachPass": {
          "name": "sed nulla ut",
          "namensbestandteil": "exercita",
          "unstrukturierteSchreibweise": "labore nostrud"
        },
        "ordensname": "ad ut",
        "kuenstlername": "veniam amet cillum consectetur",
        "doktorgrad": "nisi exercitation dolor offi"
      },
      "melderegisterAnschrift": {
        "gemeindeschluessel": "aliqua",
        "hausnummer": "Ut amet dolor ut",
        "buchstabeHausnummer": "deserunt ea",
        "teilnummerHausnummer": "tempor adipisicing",
        "ort": "esse dolore non",
        "ortsteil": "reprehenderit",
        "postleitzahl": "ullamco",
        "stockwerk": "e",
        "appartmentnummer": "consequat in ullamco tempor adipisicing",
        "strasse": "sed reprehenderit officia nostrud elit",
        "wohnungsgeber": "ex",
        "zusatz": "et sint aliquip deserunt laborum",
        "staat": "exercit",
        "anschriftUnbekannt": false,
        "sperrvermerk": false
      },
      "sprengstoffrechtlicheErlaubnis": {
        "angelegtAm": "1951-11-02T05:30:06.919Z"
      },
      "waffenrechtlicheErlaubnis": {
        "angelegtAm": "2019-11-10T07:12:54.539Z"
      },
      "waffenbesitzVerbot": {
        "angelegtAm": "1992-08-21T10:15:08.650Z",
        "behoerde": "est sed proident ullamco",
        "aktenzeichen": "consectetu"
      },
      "staatsangehoerigkeiten": [
        {
          "schluessel": "sit non anim dolor amet",
          "text": "adipisicing quis tempor sed do"
        },
        {
          "schluessel": "ea",
          "text": "do in et veniam"
        }
      ]
    },
    {
      "auskunftssperre": "exercitation occaecat esse",
      "ordnungsmerkmal": "consequ",
      "personstatus": "AKTUELLER_FESTER_EINWOHNER",
      "geburtsdaten": {
        "datum": {
          "datum": "Duis id sunt sit"
        },
        "ort": "in",
        "staat": "deserunt nostrud"
      },
      "geschlecht": "sunt tempor minim",
      "sterbedaten": {
        "datum": "1989-09-05T02:51:39.845Z",
        "ort": "aliqua laborum anim"
      },
      "familienstanddaten": {
        "familienstand": "ex cillum officia tempor in",
        "datum": "1982-09-08T06:57:33.239Z"
      },
      "namen": {
        "vornamen": [
          {
            "vorname": "deserunt ad nostru",
            "rufname": false
          },
          {
            "vorname": "anim in deserunt",
            "rufname": true
          }
        ],
        "familienname": {
          "name": "pariatur qui ullamco irure eu",
          "namensbestandteil": "elit culpa",
          "unstrukturierteSchreibweise": "cupidatat"
        },
        "geburtsname": {
          "name": "esse nulla",
          "namensbestandteil": "Lorem est elit in in",
          "unstrukturierteSchreibweise": "dolore Lorem sed dolore"
        },
        "ehename": {
          "name": "nulla laborum",
          "namensbestandteil": "nisi sint aliqua exercitation",
          "unstrukturierteSchreibweise": "est sint"
        },
        "familiennameNachPass": {
          "name": "eiusmod cillum ea Duis",
          "namensbestandteil": "eu mollit",
          "unstrukturierteSchreibweise": "reprehenderit voluptate"
        },
        "ordensname": "occaecat",
        "kuenstlername": "Ut est",
        "doktorgrad": "dolore veniam"
      },
      "melderegisterAnschrift": {
        "gemeindeschluessel": "ad dolore ex",
        "hausnummer": "laboris ea",
        "buchstabeHausnummer": "dolor veniam adipisicing dolore",
        "teilnummerHausnummer": "dolor officia aute",
        "ort": "ea commodo ipsum",
        "ortsteil": "consequat quis fugiat",
        "postleitzahl": "eiusmod tempor",
        "stockwerk": "pariatur incididunt laborum do non",
        "appartmentnummer": "volu",
        "strasse": "Duis",
        "wohnungsgeber": "incididunt elit",
        "zusatz": "inci",
        "staat": "non",
        "anschriftUnbekannt": false,
        "sperrvermerk": true
      },
      "sprengstoffrechtlicheErlaubnis": {
        "angelegtAm": "1973-07-24T20:04:13.074Z"
      },
      "waffenrechtlicheErlaubnis": {
        "angelegtAm": "1982-05-03T07:48:04.262Z"
      },
      "waffenbesitzVerbot": {
        "angelegtAm": "1984-10-11T03:09:27.105Z",
        "behoerde": "cupidatat elit aliquip",
        "aktenzeichen": "ipsum Ut amet"
      },
      "staatsangehoerigkeiten": [
        {
          "schluessel": "eu velit aliqua ex adipisicing",
          "text": "tempor fugiat"
        },
        {
          "schluessel": "exercitation et ad",
          "text": "ut"
        }
      ]
    }
  ]
}
```

##### getPersonErweitert

Gets a PersonErweitert on the basis of an Ordnungsmerkmal.

The following JSON object shows the example payload set at the element templates request field.

```json
{
  "eventType": "getPersonErweitert",
  "ordnungsmerkmal": "99"
}
```

The response is as follows.

```json
{
  "auskunftssperre": "ut ipsum",
  "ordnungsmerkmal": "dolor cillum nisi",
  "personstatus": "AKTUELLER_FESTER_EINWOHNER",
  "geburtsdaten": {
    "datum": {
      "datum": "aute ea culpa fugiat"
    },
    "ort": "in ea labore anim",
    "staat": "nulla"
  },
  "geschlecht": "Ut elit aliquip exercitation",
  "sterbedaten": {
    "datum": "1969-12-25T00:20:01.516Z",
    "ort": "laboris in"
  },
  "familienstanddaten": {
    "familienstand": "sint et proident ex",
    "datum": "2003-10-05T20:53:01.507Z"
  },
  "namen": {
    "vornamen": [
      {
        "vorname": "adipisicing qui aliqua",
        "rufname": false
      },
      {
        "vorname": "cupidatat eiusmod et do ex",
        "rufname": true
      }
    ],
    "familienname": {
      "name": "in reprehenderit",
      "namensbestandteil": "tempor aute qui",
      "unstrukturierteSchreibweise": "consequat i"
    },
    "geburtsname": {
      "name": "dolore labore laboris eiusmod in",
      "namensbestandteil": "cupidatat ex",
      "unstrukturierteSchreibweise": "reprehenderit Duis"
    },
    "ehename": {
      "name": "fugiat ut",
      "namensbestandteil": "",
      "unstrukturierteSchreibweise": "adipisicing laboris"
    },
    "familiennameNachPass": {
      "name": "officia nostrud",
      "namensbestandteil": "commodo",
      "unstrukturierteSchreibweise": "dolore esse et eu"
    },
    "ordensname": "commodo aliquip a",
    "kuenstlername": "pariatur",
    "doktorgrad": "d"
  },
  "doktorgradNachweis": "nulla",
  "sperren": [
    {
      "frist": "1943-04-12T11:52:24.809Z",
      "grund": "sunt minim labore"
    },
    {
      "frist": "2011-10-26T09:46:55.721Z",
      "grund": "nisi"
    }
  ],
  "religion": "enim laborum proid",
  "wahldaten": {
    "wvzEintrag": {
      "gebietskoerperschaft": "cupidatat labore",
      "staat": "dolore"
    },
    "ausschluesse": [
      {
        "grund": "ullamco sed",
        "behoerde": "amet pro",
        "aktenzeichen": "exercitation",
        "wahlauschlussBis": "1988-12-20T15:02:53.091Z"
      },
      {
        "grund": "aliquip mollit id",
        "behoerde": "null",
        "aktenzeichen": "qui veniam ex voluptate",
        "wahlauschlussBis": "1964-10-05T23:27:38.398Z"
      }
    ]
  },
  "zuzugsdaten": {
    "zuzugEu": "2004-07-29T20:35:40.158Z",
    "zuzugSamtgemeinde": "1983-07-26T19:08:40.628Z",
    "zuzugOrtsteil": "2018-09-11T01:38:34.969Z",
    "zuzugBund": "1946-08-16T02:25:04.196Z",
    "zuzugLand": "2011-06-01T15:57:57.838Z",
    "zuzugRegierungsbezirk": "1969-04-24T01:43:44.821Z",
    "zuzugKreis": "2002-02-08T21:16:58.059Z",
    "zuzugGemeinde": "2000-12-06T13:40:03.841Z",
    "mitHauptwohnungGemeldetSeit": "1994-09-17T02:15:40.742Z"
  },
  "wohnungen": [
    {
      "melderegisterAnschrift": {
        "gemeindeschluessel": "nulla anim fugiat minim incididunt",
        "hausnummer": "non anim",
        "buchstabeHausnummer": "reprehenderit dolore Duis in",
        "teilnummerHausnummer": "consequat aliquip",
        "ort": "culpa eiusmod reprehenderit do est",
        "ortsteil": "voluptate",
        "postleitzahl": "nulla commodo voluptate sed",
        "stockwerk": "eu",
        "appartmentnummer": "deserunt ipsum",
        "strasse": "quis",
        "wohnungsgeber": "non",
        "zusatz": "dolore velit tempor",
        "staat": "et voluptate",
        "anschriftUnbekannt": true,
        "sperrvermerk": false,
        "strassenschluessel": "Ut culpa"
      },
      "einzug": "2014-03-27T00:09:58.055Z",
      "auszug": {
        "jahr": "qui laboris",
        "jahrMonat": "enim laborum deserunt",
        "datum": "1952-12-17T21:21:27.900Z"
      },
      "statuswechsel": "1967-06-17T12:10:12.666Z",
      "meldungStatuswechsel": {
        "art": "PERSOENLICHE_MELDUNG",
        "datum": "1983-07-13T19:28:21.169Z"
      },
      "meldungEinzug": {
        "art": "MELDUNG_VON_AMTS_WEGEN",
        "datum": "1948-05-13T04:47:07.687Z"
      },
      "meldungAuszug": {
        "art": "MELDUNG_VON_AMTS_WEGEN",
        "datum": "1988-12-26T16:10:10.494Z"
      },
      "innerhalb": false,
      "belegGebunden": false,
      "gefoerdert": false,
      "scheinadresse": true
    },
    {
      "melderegisterAnschrift": {
        "gemeindeschluessel": "Ut mollit",
        "hausnummer": "consequat voluptate sunt sint dolor",
        "buchstabeHausnummer": "nostrud ut ullamco nisi",
        "teilnummerHausnummer": "aute",
        "ort": "pariatur sunt dolor velit dolore",
        "ortsteil": "irure",
        "postleitzahl": "eu reprehenderit elit",
        "stockwerk": "eiusmod et culpa",
        "appartmentnummer": "ea nostrud",
        "strasse": "occaeca",
        "wohnungsgeber": "Duis aliquip tempor in",
        "zusatz": "id aute",
        "staat": "in dolore amet magna",
        "anschriftUnbekannt": false,
        "sperrvermerk": true,
        "strassenschluessel": "proident in sint"
      },
      "einzug": "1944-07-24T12:37:35.931Z",
      "auszug": {
        "jahr": "et consequat eiusmod",
        "jahrMonat": "sit Lorem elit non",
        "datum": "2005-09-22T23:52:55.484Z"
      },
      "statuswechsel": "1974-05-19T17:57:34.704Z",
      "meldungStatuswechsel": {
        "art": "PERSOENLICHE_MELDUNG",
        "datum": "1962-09-11T03:59:31.174Z"
      },
      "meldungEinzug": {
        "art": "PERSOENLICHE_MELDUNG",
        "datum": "2009-04-07T06:44:26.482Z"
      },
      "meldungAuszug": {
        "art": "PERSOENLICHE_MELDUNG",
        "datum": "1943-04-08T12:44:37.914Z"
      },
      "innerhalb": false,
      "belegGebunden": false,
      "gefoerdert": true,
      "scheinadresse": false
    }
  ],
  "passdaten": [
    {
      "art": "ut Lorem",
      "serienummer": "nostrud sint ad",
      "geschlecht": "veniam culpa velit",
      "ausstellendeBehoerde": "fugiat do adipisicing proident esse",
      "ausstellungsdatum": "1981-06-03T21:08:23.914Z",
      "ablaufdatum": "1983-02-12T16:10:49.977Z"
    },
    {
      "art": "ex non dolore",
      "serienummer": "amet Duis",
      "geschlecht": "ut eu aliquip nostrud",
      "ausstellendeBehoerde": "pariatur Duis reprehenderit dolor cillum",
      "ausstellungsdatum": "2021-06-05T11:45:47.841Z",
      "ablaufdatum": "1994-12-29T07:09:28.728Z"
    }
  ],
  "partner": {
    "namen": {
      "vornamen": [
        {
          "vorname": "ad ullamco veniam",
          "rufname": true
        },
        {
          "vorname": "incididunt mollit eu irure",
          "rufname": true
        }
      ],
      "familienname": {
        "name": "in",
        "namensbestandteil": "amet consequat",
        "unstrukturierteSchreibweise": "amet dolor in mollit"
      },
      "geburtsname": {
        "name": "culpa aute Ut",
        "namensbestandteil": "elit Duis non esse exercitation",
        "unstrukturierteSchreibweise": "voluptate incidi"
      },
      "ehename": {
        "name": "cillum qui et minim amet",
        "namensbestandteil": "aute non exercitation consequat",
        "unstrukturierteSchreibweise": "deserunt id laborum cupidatat labore"
      },
      "familiennameNachPass": {
        "name": "elit consectetur officia commodo",
        "namensbestandteil": "ex officia",
        "unstrukturierteSchreibweise": "est irure"
      },
      "ordensname": "nulla aliquip reprehenderit",
      "kuenstlername": "est consectetur irure adipisicing deserunt",
      "doktorgrad": "tempor est ullamc"
    },
    "art": "LP",
    "ordnungsmerkmal": "dolor occaec",
    "sperren": [
      {
        "frist": "1955-06-19T13:05:52.105Z",
        "grund": "elit sint"
      },
      {
        "frist": "1953-06-23T16:09:21.351Z",
        "grund": "dolor commodo ipsum"
      }
    ],
    "personstatus": "AKTUELLER_FESTER_EINWOHNER",
    "geburtsdatum": {
      "datum": "dolore pariatur commodo deserunt"
    },
    "geschlecht": "nulla",
    "anschrift": {
      "gemeindeschluessel": "magna tempor laboris ea veniam",
      "hausnummer": "proident la",
      "buchstabeHausnummer": "laboris est Ut quis",
      "teilnummerHausnummer": "Excepteur do laborum",
      "ort": "eiusmod labore incididunt est",
      "ortsteil": "dolore culpa",
      "postleitzahl": "sed enim",
      "stockwerk": "ut exe",
      "appartmentnummer": "dolo",
      "strasse": "voluptate enim",
      "wohnungsgeber": "commodo ut sed ullamco",
      "zusatz": "sint pariatur",
      "staat": "dolor aute minim laboris amet",
      "anschriftUnbekannt": true,
      "sperrvermerk": false,
      "strassenschluessel": "Lorem deserunt"
    },
    "sterbedaten": {
      "datum": "1977-02-26T01:04:59.944Z",
      "ort": "veniam Duis"
    }
  },
  "kinder": [
    {
      "namen": {
        "vornamen": [
          {
            "vorname": "incididunt",
            "rufname": true
          },
          {
            "vorname": "pariatur ex sint",
            "rufname": false
          }
        ],
        "familienname": {
          "name": "cillum dolore",
          "namensbestandteil": "quis",
          "unstrukturierteSchreibweise": "non aute"
        },
        "geburtsname": {
          "name": "laboris",
          "namensbestandteil": "Lore",
          "unstrukturierteSchreibweise": "dolore irure officia velit ut"
        },
        "ehename": {
          "name": "aliqua occaecat Ut fugiat",
          "namensbestandteil": "dolor",
          "unstrukturierteSchreibweise": "ipsum cillum ad cupi"
        },
        "familiennameNachPass": {
          "name": "veniam fugiat velit",
          "namensbestandteil": "Lorem",
          "unstrukturierteSchreibweise": "sint"
        },
        "ordensname": "nostrud Ut ad qui",
        "kuenstlername": "aliquip culpa eiusmod velit",
        "doktorgrad": "dolore adipisicing aute"
      },
      "ordnungsmerkmal": "quis",
      "sperren": [
        {
          "frist": "1964-07-20T03:57:27.438Z",
          "grund": "ad est sun"
        },
        {
          "frist": "1988-09-03T06:18:55.837Z",
          "grund": "commodo enim"
        }
      ],
      "personstatus": "INAKTUELLER_FESTER_EINWOHNER",
      "geburtsdatum": {
        "datum": "deserunt ipsum mollit minim"
      },
      "sterbedaten": {
        "datum": "2006-07-24T20:10:49.600Z",
        "ort": "irure nisi dolore aliqua minim"
      },
      "geschlecht": "veniam consect"
    },
    {
      "namen": {
        "vornamen": [
          {
            "vorname": "ut labori",
            "rufname": false
          },
          {
            "vorname": "adipisicing deserunt aliqua minim",
            "rufname": false
          }
        ],
        "familienname": {
          "name": "amet fugiat proident ut",
          "namensbestandteil": "est pariatur et nulla officia",
          "unstrukturierteSchreibweise": "ullamco in do"
        },
        "geburtsname": {
          "name": "incididunt dolore et",
          "namensbestandteil": "non sit nostrud enim culpa",
          "unstrukturierteSchreibweise": "in"
        },
        "ehename": {
          "name": "eu magna dolore",
          "namensbestandteil": "qui Ut",
          "unstrukturierteSchreibweise": "magna adipisicing"
        },
        "familiennameNachPass": {
          "name": "et dolor proident in sint",
          "namensbestandteil": "elit in consequat",
          "unstrukturierteSchreibweise": "in sed proident incididunt"
        },
        "ordensname": "non sit",
        "kuenstlername": "eu id",
        "doktorgrad": "voluptate eiusmod adipisicing cupidatat id"
      },
      "ordnungsmerkmal": "nisi reprehenderit esse proident",
      "sperren": [
        {
          "frist": "1965-08-27T17:27:22.258Z",
          "grund": "adipisicing deserunt"
        },
        {
          "frist": "2021-09-10T15:05:18.218Z",
          "grund": "tempor sit eu qui"
        }
      ],
      "personstatus": "INAKTUELLER_NICHTMELDEPFLICHTIGER_FESTER_EINWOHNER",
      "geburtsdatum": {
        "datum": "ut commodo cillum sint aliquip"
      },
      "sterbedaten": {
        "datum": "1962-03-22T16:04:38.465Z",
        "ort": "exercitation Ut"
      },
      "geschlecht": "sunt Duis qui esse fugiat"
    }
  ],
  "vertreter": [
    {
      "ordnungsmerkmal": "deserunt et exercitation cillum sint",
      "personstatus": "EXTERNE_PERSON",
      "namen": {
        "vornamen": [
          {
            "vorname": "incididunt labore",
            "rufname": false
          },
          {
            "vorname": "anim dolore eiusmod",
            "rufname": false
          }
        ],
        "familienname": {
          "name": "aute ex qui dolore veniam",
          "namensbestandteil": "adipisicing in irure",
          "unstrukturierteSchreibweise": "in qui Ut"
        },
        "geburtsname": {
          "name": "proident ea id eu",
          "namensbestandteil": "tempor Duis quis sunt",
          "unstrukturierteSchreibweise": "enim dolore ea officia"
        },
        "ehename": {
          "name": "cul",
          "namensbestandteil": "elit proident commodo",
          "unstrukturierteSchreibweise": "incididunt sunt Excepte"
        },
        "familiennameNachPass": {
          "name": "aute anim commodo",
          "namensbestandteil": "Lorem magna sint",
          "unstrukturierteSchreibweise": "Lorem ut"
        },
        "ordensname": "Ut in in",
        "kuenstlername": "nisi anim ",
        "doktorgrad": "id ut"
      },
      "art": "dolore id ad amet",
      "geburtsdatum": {
        "datum": "sed"
      },
      "geschlecht": "nisi consectetur eu tempor",
      "sterbedaten": {
        "datum": "2017-05-19T02:30:17.583Z",
        "ort": "in quis"
      },
      "endeVertretung": "2019-04-04T21:36:55.643Z",
      "sperren": [
        {
          "frist": "1994-12-11T04:04:57.893Z",
          "grund": "id eu sunt"
        },
        {
          "frist": "2012-04-04T06:00:05.571Z",
          "grund": "elit non"
        }
      ],
      "anschrift": {
        "gemeindeschluessel": "no",
        "hausnummer": "tempor officia amet ut enim",
        "buchstabeHausnummer": "est in",
        "teilnummerHausnummer": "labore ea occaecat",
        "ort": "in elit",
        "ortsteil": "in",
        "postleitzahl": "ex officia nulla",
        "stockwerk": "proident dolor ut",
        "appartmentnummer": "labore consectetur et dolor",
        "strasse": "proident irure amet elit",
        "wohnungsgeber": "fugiat aute laborum",
        "zusatz": "reprehenderit elit",
        "staat": "",
        "anschriftUnbekannt": false,
        "sperrvermerk": false,
        "strassenschluessel": "tempor"
      },
      "bezeichnungJuristischePerson": "exercitation sit do",
      "natuerlichePerson": false
    },
    {
      "ordnungsmerkmal": "commodo deserunt reprehenderit",
      "personstatus": "AKTUELLER_FESTER_EINWOHNER",
      "namen": {
        "vornamen": [
          {
            "vorname": "fugiat exercitation dolore",
            "rufname": true
          },
          {
            "vorname": "Ut ullamco Lorem",
            "rufname": false
          }
        ],
        "familienname": {
          "name": "Ut culpa",
          "namensbestandteil": "consectetur officia amet ad",
          "unstrukturierteSchreibweise": "laborum qui"
        },
        "geburtsname": {
          "name": "Lorem ad aute veniam",
          "namensbestandteil": "nulla tempor proident sit",
          "unstrukturierteSchreibweise": "consectetur ex aliqua"
        },
        "ehename": {
          "name": "nisi quis ipsum",
          "namensbestandteil": "dolor",
          "unstrukturierteSchreibweise": "ea qui aliquip nulla"
        },
        "familiennameNachPass": {
          "name": "ea do commodo",
          "namensbestandteil": "fugiat amet culpa",
          "unstrukturierteSchreibweise": "magna proident dolor tempor nostrud"
        },
        "ordensname": "consectetur aute magna nulla",
        "kuenstlername": "in aute",
        "doktorgrad": "Lorem in veniam magna"
      },
      "art": "irure et",
      "geburtsdatum": {
        "datum": "dolor ullam"
      },
      "geschlecht": "",
      "sterbedaten": {
        "datum": "2008-07-23T23:52:31.036Z",
        "ort": "sit nulla irure"
      },
      "endeVertretung": "1997-09-20T05:21:03.277Z",
      "sperren": [
        {
          "frist": "2018-10-01T03:37:09.032Z",
          "grund": "sint in sed"
        },
        {
          "frist": "1989-08-29T18:34:15.895Z",
          "grund": "in ipsum ut eu"
        }
      ],
      "anschrift": {
        "gemeindeschluessel": "in Duis elit laboris nulla",
        "hausnummer": "officia laborum",
        "buchstabeHausnummer": "ut sit aliqua ipsum Lorem",
        "teilnummerHausnummer": "eu amet sed",
        "ort": "fugiat adip",
        "ortsteil": "reprehenderit laboris et commodo nulla",
        "postleitzahl": "veniam cillum ut in",
        "stockwerk": "do fugiat sint minim in",
        "appartmentnummer": "amet est sunt",
        "strasse": "in aliquip consequat",
        "wohnungsgeber": "sunt ut",
        "zusatz": "commodo id",
        "staat": "enim occaecat nulla eiusmod",
        "anschriftUnbekannt": true,
        "sperrvermerk": false,
        "strassenschluessel": "Ut culpa ut ex"
      },
      "bezeichnungJuristischePerson": "ad sit eiusmod aliqua magna",
      "natuerlichePerson": true
    }
  ],
  "fruehereNamen": {
    "fruehererFamilienname": [
      {
        "familienname": {
          "name": "Ut esse id",
          "namensbestandteil": "in quis ut in",
          "unstrukturierteSchreibweise": "laborum amet fugiat"
        },
        "aenderungsdatum": "1948-03-08T18:58:28.328Z"
      },
      {
        "familienname": {
          "name": "proident nulla",
          "namensbestandteil": "do qui sit",
          "unstrukturierteSchreibweise": "consectetur laboris"
        },
        "aenderungsdatum": "1999-09-05T20:52:04.574Z"
      }
    ],
    "fruehererVorname": [
      {
        "vorname": [
          {
            "vorname": "in sit irure ut ipsum",
            "rufname": false
          },
          {
            "vorname": "qui",
            "rufname": false
          }
        ],
        "aenderungsdatum": "2000-02-15T06:57:38.711Z"
      },
      {
        "vorname": [
          {
            "vorname": "sunt",
            "rufname": true
          },
          {
            "vorname": "in laboris cupidatat culpa eiusmod",
            "rufname": false
          }
        ],
        "aenderungsdatum": "1998-06-06T12:37:58.949Z"
      }
    ]
  },
  "waffenrechtlicheErlaubnisErweitert": [
    {
      "angelegtAm": "2005-12-21T01:45:38.331Z",
      "behoerde": "dolore sed fugiat laborum",
      "aktenzeichen": "non sit sunt"
    },
    {
      "angelegtAm": "1984-03-01T04:45:09.781Z",
      "behoerde": "ut in",
      "aktenzeichen": "proident aliquip"
    }
  ],
  "sprengstoffrechtlicheErlaubnisErweitert": [
    {
      "angelegtAm": "1980-04-23T15:46:06.019Z",
      "behoerde": "anim enim nostrud Excepteur",
      "aktenzeichen": "aliquip ea adip"
    },
    {
      "angelegtAm": "1996-11-10T12:50:59.992Z",
      "behoerde": "commodo est ea oc",
      "aktenzeichen": "velit sint"
    }
  ],
  "waffenbesitzVerbot": [
    {
      "angelegtAm": "1978-03-23T08:58:57.828Z",
      "behoerde": "sit eiusmod pariatur tempor do",
      "aktenzeichen": "voluptate exercitation cil"
    },
    {
      "angelegtAm": "2006-07-29T05:58:59.167Z",
      "behoerde": "dolor anim sint",
      "aktenzeichen": "cupidatat exercitation ut mollit Excepteur"
    }
  ],
  "verlustDeutscheStaatsangehoerigkeit": "1988-10-04T01:40:31.075Z",
  "optionsDeutscherDaten": {
    "befristungBis": "1994-01-05T05:28:26.813Z"
  },
  "artStaatsangehoerigkeit": "ipsum dolore elit occaecat",
  "frueheresOm": "deserunt ut fugiat dolor in",
  "zukuenftigesOm": "quis eiusmod",
  "staatsangehoerigkeiten": [
    {
      "schluessel": "p",
      "text": "laboris",
      "art": "tempor pariatur dolore enim ex",
      "nachweisdaten": {
        "datum": "1994-07-21T17:49:43.100Z",
        "aktenzeichen": "s",
        "behoerde": "veniam voluptate"
      },
      "nichtEuBuerger": false,
      "staatEuMitglied": true
    },
    {
      "schluessel": "dolore",
      "text": "in do eu",
      "art": "laboris eiusmod",
      "nachweisdaten": {
        "datum": "2005-09-09T00:12:51.462Z",
        "aktenzeichen": "fugiat",
        "behoerde": "irure proident eiusmod"
      },
      "nichtEuBuerger": false,
      "staatEuMitglied": false
    }
  ]
}
```

##### searchPersonErweitert

Searches PersonErweitert based on search parameters.

The following JSON object shows the example payload set at the element templates request field.
The search parameters within JSON object allocated to JSON key `searchPersonErweitert` are optional,
if parameters are not needed, they can be omitted.

A mandatory attribute is `datensatzstatus` with the characteristics `AKTUELL`, `INAKTUELL` or `OHNE_EINSCHRAENKUNG`.

```json
{
  "eventType": "searchPersonErweitert",
  "searchPersonErweitert": {
    "suchkriterien": {
      "datensatzstatus": "INAKTUELL",
      "familienname": "dolore ",
      "geburtsdatum": {
        "datum": "dolor ull"
      },
      "geschlecht": "ullamco amet voluptate",
      "vorname": "proident",
      "buchstabeVon": "non cupidatat",
      "buchstabeBis": "in",
      "hausnummerVon": "sint anim eiusmod",
      "hausnummerBis": "Ut dolore fugiat",
      "strassenschluessel": "officia Duis ut ut",
      "teilnummerVon": "amet",
      "teilnummerBis": "enim esse in aute",
      "wohnort": "dolore fugiat",
      "postleitzahl": "sed tempor qui nulla",
      "ordnungsmerkmal": "nostrud qui aliqua"
    }
  }
}
```

The response is as follows.

```json
{
  "personen": [
    {
      "auskunftssperre": "et adipis",
      "ordnungsmerkmal": "ea",
      "personstatus": "KEIN_STATUS",
      "geburtsdaten": {
        "datum": {
          "datum": "elit"
        },
        "ort": "laborum labore",
        "staat": "non aliqua"
      },
      "geschlecht": "labore magna in",
      "sterbedaten": {
        "datum": "1996-10-08T14:51:37.035Z",
        "ort": "Duis enim"
      },
      "familienstanddaten": {
        "familienstand": "voluptate",
        "datum": "2018-10-07T00:05:05.645Z"
      },
      "namen": {
        "vornamen": [
          {
            "vorname": "labore reprehenderit ea fugiat",
            "rufname": false
          },
          {
            "vorname": "dolor deserunt ex reprehenderit",
            "rufname": true
          }
        ],
        "familienname": {
          "name": "in anim eu sunt",
          "namensbestandteil": "amet cupidatat velit magna nulla",
          "unstrukturierteSchreibweise": "sunt eiusmod"
        },
        "geburtsname": {
          "name": "magna aute eiusmod consectetur",
          "namensbestandteil": "velit cupidatat enim in",
          "unstrukturierteSchreibweise": "ut aliquip qui Duis"
        },
        "ehename": {
          "name": "irure velit",
          "namensbestandteil": "eiusmod velit qui",
          "unstrukturierteSchreibweise": "dolor nulla minim magna"
        },
        "familiennameNachPass": {
          "name": "consectetur voluptate",
          "namensbestandteil": "ut cillum",
          "unstrukturierteSchreibweise": "magna do aliquip in enim"
        },
        "ordensname": "dolor mollit",
        "kuenstlername": "non commodo ex reprehenderit",
        "doktorgrad": "adipisicing labore mo"
      },
      "doktorgradNachweis": "nisi",
      "sperren": [
        {
          "frist": "2013-01-06T22:56:32.474Z",
          "grund": "consequat"
        },
        {
          "frist": "2012-01-24T06:48:35.777Z",
          "grund": "qui nulla ut"
        }
      ],
      "religion": "veniam mollit",
      "wahldaten": {
        "wvzEintrag": {
          "gebietskoerperschaft": "culpa laboris esse",
          "staat": "cillum sed sint proident"
        },
        "ausschluesse": [
          {
            "grund": "occaecat ut Duis aliquip",
            "behoerde": "in sit aliqua ut",
            "aktenzeichen": "irure ea velit ullamco minim",
            "wahlauschlussBis": "2011-03-03T13:55:16.892Z"
          },
          {
            "grund": "dolore officia",
            "behoerde": "mollit",
            "aktenzeichen": "ut cillum",
            "wahlauschlussBis": "1974-04-06T18:10:32.286Z"
          }
        ]
      },
      "zuzugsdaten": {
        "zuzugEu": "2021-08-24T13:25:06.447Z",
        "zuzugSamtgemeinde": "1952-04-06T15:23:24.468Z",
        "zuzugOrtsteil": "1975-07-01T09:00:13.426Z",
        "zuzugBund": "1945-12-16T09:20:57.060Z",
        "zuzugLand": "1999-02-12T05:52:02.592Z",
        "zuzugRegierungsbezirk": "1951-04-25T18:18:10.571Z",
        "zuzugKreis": "1962-12-21T05:21:20.574Z",
        "zuzugGemeinde": "1966-03-31T10:03:05.825Z",
        "mitHauptwohnungGemeldetSeit": "1945-12-13T21:21:50.005Z"
      },
      "wohnungen": [
        {
          "melderegisterAnschrift": {
            "gemeindeschluessel": "ad eiusmod nulla laborum",
            "hausnummer": "sed nulla minim eu",
            "buchstabeHausnummer": "aliquip non reprehenderit",
            "teilnummerHausnummer": "est",
            "ort": "sit reprehenderit",
            "ortsteil": "adipisicing eu",
            "postleitzahl": "in qui",
            "stockwerk": "amet Lorem est",
            "appartmentnummer": "est",
            "strasse": "exercitation nostrud dolore cupidatat dolore",
            "wohnungsgeber": "dolor Excepteur",
            "zusatz": "mollit ullamco nulla voluptate dolor",
            "staat": "laborum ex ut do",
            "anschriftUnbekannt": false,
            "sperrvermerk": true,
            "strassenschluessel": "veniam id"
          },
          "einzug": "1958-10-11T17:06:59.440Z",
          "auszug": {
            "jahr": "minim sint exercitation enim tempor",
            "jahrMonat": "mollit Lorem officia",
            "datum": "1969-11-13T00:01:06.389Z"
          },
          "statuswechsel": "1999-05-24T06:02:20.162Z",
          "meldungStatuswechsel": {
            "art": "PERSOENLICHE_MELDUNG",
            "datum": "1977-01-29T03:08:15.778Z"
          },
          "meldungEinzug": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "1966-06-28T12:28:19.665Z"
          },
          "meldungAuszug": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "1990-12-28T10:28:54.281Z"
          },
          "innerhalb": false,
          "belegGebunden": true,
          "gefoerdert": true,
          "scheinadresse": true
        },
        {
          "melderegisterAnschrift": {
            "gemeindeschluessel": "proident",
            "hausnummer": "Excepte",
            "buchstabeHausnummer": "adipisicing enim aliquip",
            "teilnummerHausnummer": "sint anim do",
            "ort": "ea velit ullamco",
            "ortsteil": "labore commodo esse Duis",
            "postleitzahl": "ad ullamco",
            "stockwerk": "ad dolor sit",
            "appartmentnummer": "in reprehenderit tempor adipisicing",
            "strasse": "deserunt in veni",
            "wohnungsgeber": "eu pariatur iru",
            "zusatz": "aute quis esse",
            "staat": "sed dolore",
            "anschriftUnbekannt": true,
            "sperrvermerk": false,
            "strassenschluessel": "magna Excepteur"
          },
          "einzug": "1956-06-25T17:17:20.184Z",
          "auszug": {
            "jahr": "enim et anim labore",
            "jahrMonat": "dolore sint",
            "datum": "2009-05-27T22:09:30.986Z"
          },
          "statuswechsel": "1958-11-07T00:36:53.612Z",
          "meldungStatuswechsel": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "2007-12-20T01:46:46.441Z"
          },
          "meldungEinzug": {
            "art": "PERSOENLICHE_MELDUNG",
            "datum": "1960-08-10T21:51:54.626Z"
          },
          "meldungAuszug": {
            "art": "PERSOENLICHE_MELDUNG",
            "datum": "1968-08-11T08:59:40.535Z"
          },
          "innerhalb": false,
          "belegGebunden": false,
          "gefoerdert": false,
          "scheinadresse": false
        }
      ],
      "passdaten": [
        {
          "art": "occaecat ea sunt",
          "serienummer": "adipisicing labore qui ullamco",
          "geschlecht": "aliqua laboris",
          "ausstellendeBehoerde": "in dolor sint cillum",
          "ausstellungsdatum": "2009-07-09T12:13:16.661Z",
          "ablaufdatum": "1988-04-10T12:13:35.922Z"
        },
        {
          "art": "reprehenderit",
          "serienummer": "aute ut fugiat",
          "geschlecht": "Lorem",
          "ausstellendeBehoerde": "ut exercitation",
          "ausstellungsdatum": "1960-09-10T03:15:42.513Z",
          "ablaufdatum": "1964-06-06T09:27:55.004Z"
        }
      ],
      "partner": {
        "namen": {
          "vornamen": [
            {
              "vorname": "occaecat in ea",
              "rufname": false
            },
            {
              "vorname": "aliquip",
              "rufname": false
            }
          ],
          "familienname": {
            "name": "reprehenderit labore magna",
            "namensbestandteil": "ex",
            "unstrukturierteSchreibweise": "adipisicing dolor minim"
          },
          "geburtsname": {
            "name": "deserunt anim veniam nisi",
            "namensbestandteil": "sunt",
            "unstrukturierteSchreibweise": "consec"
          },
          "ehename": {
            "name": "adipisicing dolor esse",
            "namensbestandteil": "dolor irure",
            "unstrukturierteSchreibweise": "laborum"
          },
          "familiennameNachPass": {
            "name": "exercitation dolore cupidatat",
            "namensbestandteil": "quis ad id labore consectetur",
            "unstrukturierteSchreibweise": "fugiat amet id"
          },
          "ordensname": "qui enim labore",
          "kuenstlername": "sit officia enim laboris",
          "doktorgrad": "ut pariatur"
        },
        "art": "VH",
        "ordnungsmerkmal": "ad veniam adipisicing sunt",
        "sperren": [
          {
            "frist": "1963-10-11T00:06:53.893Z",
            "grund": "labore consectetur est"
          },
          {
            "frist": "2018-04-08T17:35:33.933Z",
            "grund": "al"
          }
        ],
        "personstatus": "EXTERNE_PERSON",
        "geburtsdatum": {
          "datum": "aliqua quis in anim"
        },
        "geschlecht": "id esse",
        "anschrift": {
          "gemeindeschluessel": "ex consequat",
          "hausnummer": "in",
          "buchstabeHausnummer": "minim",
          "teilnummerHausnummer": "magna anim elit",
          "ort": "in eiusmod nulla consequat",
          "ortsteil": "sint commodo ut",
          "postleitzahl": "elit dolor Ut dolore",
          "stockwerk": "fugiat culpa",
          "appartmentnummer": "v",
          "strasse": "ad",
          "wohnungsgeber": "ut exercitation aliqua",
          "zusatz": "dolore est commodo",
          "staat": "aute in et exercitation veniam",
          "anschriftUnbekannt": true,
          "sperrvermerk": false,
          "strassenschluessel": "irure sunt cillum"
        },
        "sterbedaten": {
          "datum": "1988-11-08T12:32:18.286Z",
          "ort": "commodo fugiat"
        }
      },
      "kinder": [
        {
          "namen": {
            "vornamen": [
              {
                "vorname": "consectetur pariatur irure ex",
                "rufname": true
              },
              {
                "vorname": "occaecat aliqua Duis",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "cupidatat ex in ut deserunt",
              "namensbestandteil": "tempor",
              "unstrukturierteSchreibweise": "sunt et reprehenderit"
            },
            "geburtsname": {
              "name": "exercitation anim in",
              "namensbestandteil": "pariatur culpa exercitation ea",
              "unstrukturierteSchreibweise": "esse ipsum"
            },
            "ehename": {
              "name": "in reprehenderit tempor",
              "namensbestandteil": "aliqua fugiat",
              "unstrukturierteSchreibweise": "ea amet"
            },
            "familiennameNachPass": {
              "name": "Duis",
              "namensbestandteil": "amet laboris",
              "unstrukturierteSchreibweise": "amet"
            },
            "ordensname": "nulla reprehenderit fugiat mollit",
            "kuenstlername": "laboris sit",
            "doktorgrad": "ex ipsum s"
          },
          "ordnungsmerkmal": "tempor veniam",
          "sperren": [
            {
              "frist": "1950-05-31T20:28:11.818Z",
              "grund": "dolore adipisicing consectetur"
            },
            {
              "frist": "2011-07-02T20:04:42.011Z",
              "grund": "fugiat"
            }
          ],
          "personstatus": "EXTERNE_PERSON",
          "geburtsdatum": {
            "datum": "nostrud eu"
          },
          "sterbedaten": {
            "datum": "1968-01-17T21:50:04.971Z",
            "ort": "eu dolore"
          },
          "geschlecht": "dolore nostrud irure"
        },
        {
          "namen": {
            "vornamen": [
              {
                "vorname": "laborum ut",
                "rufname": false
              },
              {
                "vorname": "labore laboris do",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "nulla in ullamco elit",
              "namensbestandteil": "in amet ad aliquip nulla",
              "unstrukturierteSchreibweise": "amet nulla sit cillum"
            },
            "geburtsname": {
              "name": "aliqua Excepteur",
              "namensbestandteil": "ipsum officia minim ex",
              "unstrukturierteSchreibweise": "dolore sit"
            },
            "ehename": {
              "name": "minim c",
              "namensbestandteil": "aliqua id dolor",
              "unstrukturierteSchreibweise": "do"
            },
            "familiennameNachPass": {
              "name": "sit dolor Duis sunt",
              "namensbestandteil": "magna non irure",
              "unstrukturierteSchreibweise": "adipisicing commodo deserunt exercitation"
            },
            "ordensname": "nisi ut elit",
            "kuenstlername": "dolor eiusmod mol",
            "doktorgrad": "fugiat proident"
          },
          "ordnungsmerkmal": "velit",
          "sperren": [
            {
              "frist": "1967-10-05T10:32:42.796Z",
              "grund": "cupidatat sunt"
            },
            {
              "frist": "1967-10-12T05:47:06.640Z",
              "grund": "in fugiat veniam non"
            }
          ],
          "personstatus": "INAKTUELLER_NICHTMELDEPFLICHTIGER_FESTER_EINWOHNER",
          "geburtsdatum": {
            "datum": "amet consectetur Lorem"
          },
          "sterbedaten": {
            "datum": "1950-05-17T21:31:40.387Z",
            "ort": "Lorem culpa Ut pariatur"
          },
          "geschlecht": "nisi dolore"
        }
      ],
      "vertreter": [
        {
          "ordnungsmerkmal": "ut dolore Duis et",
          "personstatus": "INAKTUELLER_NICHTMELDEPFLICHTIGER_FESTER_EINWOHNER",
          "namen": {
            "vornamen": [
              {
                "vorname": "pariatur quis rep",
                "rufname": true
              },
              {
                "vorname": "minim dolor",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "dolor nulla proident",
              "namensbestandteil": "dolor quis in consequat",
              "unstrukturierteSchreibweise": "ex commodo officia"
            },
            "geburtsname": {
              "name": "et",
              "namensbestandteil": "dolor et in",
              "unstrukturierteSchreibweise": "ipsum dolor culpa"
            },
            "ehename": {
              "name": "dolo",
              "namensbestandteil": "no",
              "unstrukturierteSchreibweise": "ea in in"
            },
            "familiennameNachPass": {
              "name": "irure est",
              "namensbestandteil": "ex dolore minim amet",
              "unstrukturierteSchreibweise": "minim amet ad aliqua sunt"
            },
            "ordensname": "volupt",
            "kuenstlername": "in elit pariatur reprehenderit consequat",
            "doktorgrad": "dolore dolor Ut minim"
          },
          "art": "dolor non",
          "geburtsdatum": {
            "datum": "enim exercitation cillum"
          },
          "geschlecht": "sint voluptate ipsum",
          "sterbedaten": {
            "datum": "1948-02-20T01:24:47.685Z",
            "ort": "eiusmod consectetur ea"
          },
          "endeVertretung": "1985-08-21T20:16:53.831Z",
          "sperren": [
            {
              "frist": "1964-08-06T10:24:09.677Z",
              "grund": "sed incididunt pariatur"
            },
            {
              "frist": "1967-03-06T06:09:43.180Z",
              "grund": "irure ex"
            }
          ],
          "anschrift": {
            "gemeindeschluessel": "aliquip ut in",
            "hausnummer": "exercitation i",
            "buchstabeHausnummer": "nostrud labor",
            "teilnummerHausnummer": "anim ut ullamco",
            "ort": "Duis v",
            "ortsteil": "laborum dolor Ut",
            "postleitzahl": "reprehenderit",
            "stockwerk": "do Lorem veniam eu",
            "appartmentnummer": "mollit eiusmod aute laboris",
            "strasse": "cupidatat et dolore est",
            "wohnungsgeber": "pariatur irure dolor culpa nulla",
            "zusatz": "veniam occaecat elit",
            "staat": "voluptate",
            "anschriftUnbekannt": false,
            "sperrvermerk": false,
            "strassenschluessel": "ut amet Lorem"
          },
          "bezeichnungJuristischePerson": "ex do amet non",
          "natuerlichePerson": false
        },
        {
          "ordnungsmerkmal": "paria",
          "personstatus": "AKTUELLER_SEEMANN",
          "namen": {
            "vornamen": [
              {
                "vorname": "dolore sunt",
                "rufname": true
              },
              {
                "vorname": "adipisicing in pariatur qui velit",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "sunt deserunt amet reprehenderit exercitation",
              "namensbestandteil": "dolor in sed dolore Lorem",
              "unstrukturierteSchreibweise": "ut"
            },
            "geburtsname": {
              "name": "occaecat ea est deserunt",
              "namensbestandteil": "incididunt commodo reprehenderit ea",
              "unstrukturierteSchreibweise": "consequat est"
            },
            "ehename": {
              "name": "Ut veniam esse minim",
              "namensbestandteil": "in elit ex",
              "unstrukturierteSchreibweise": "consectetur non"
            },
            "familiennameNachPass": {
              "name": "dolor elit enim cupidatat",
              "namensbestandteil": "consequat aute ad dolor",
              "unstrukturierteSchreibweise": "mollit veniam "
            },
            "ordensname": "in laborum ad in",
            "kuenstlername": "commodo in laborum in",
            "doktorgrad": "consequat id"
          },
          "art": "esse",
          "geburtsdatum": {
            "datum": "exercitation velit in"
          },
          "geschlecht": "repre",
          "sterbedaten": {
            "datum": "1949-08-24T16:41:12.283Z",
            "ort": "eu pariatur ut aute"
          },
          "endeVertretung": "1989-06-11T07:40:17.040Z",
          "sperren": [
            {
              "frist": "1970-11-12T03:34:43.287Z",
              "grund": "ut aliquip exercitation labore minim"
            },
            {
              "frist": "1944-12-24T19:04:57.777Z",
              "grund": "enim est incididunt"
            }
          ],
          "anschrift": {
            "gemeindeschluessel": "dolor",
            "hausnummer": "qui anim tempor est sit",
            "buchstabeHausnummer": "aliquip pariatur aliqua laborum",
            "teilnummerHausnummer": "consectetur cillum ut velit",
            "ort": "ea sit veniam laborum esse",
            "ortsteil": "ullamco",
            "postleitzahl": "Excepteur elit ut",
            "stockwerk": "dolore",
            "appartmentnummer": "culpa ut consectetur",
            "strasse": "eiusmod voluptate exercitation",
            "wohnungsgeber": "eiusmod Excepteur",
            "zusatz": "aliquip ex Lorem labore",
            "staat": "in",
            "anschriftUnbekannt": true,
            "sperrvermerk": false,
            "strassenschluessel": "nulla "
          },
          "bezeichnungJuristischePerson": "Excepteur voluptate",
          "natuerlichePerson": false
        }
      ],
      "fruehereNamen": {
        "fruehererFamilienname": [
          {
            "familienname": {
              "name": "ex culpa dolore",
              "namensbestandteil": "irure in elit",
              "unstrukturierteSchreibweise": "officia irure reprehenderit"
            },
            "aenderungsdatum": "1955-01-01T22:14:59.291Z"
          },
          {
            "familienname": {
              "name": "in magna dolore",
              "namensbestandteil": "est",
              "unstrukturierteSchreibweise": "in consectetu"
            },
            "aenderungsdatum": "2003-03-25T16:32:12.759Z"
          }
        ],
        "fruehererVorname": [
          {
            "vorname": [
              {
                "vorname": "qui enim voluptate labore",
                "rufname": false
              },
              {
                "vorname": "nulla reprehenderit dolor",
                "rufname": false
              }
            ],
            "aenderungsdatum": "1972-06-26T08:13:18.563Z"
          },
          {
            "vorname": [
              {
                "vorname": "ex exercita",
                "rufname": false
              },
              {
                "vorname": "Lorem magna ullamco irure",
                "rufname": false
              }
            ],
            "aenderungsdatum": "2013-05-08T02:23:30.745Z"
          }
        ]
      },
      "waffenrechtlicheErlaubnisErweitert": [
        {
          "angelegtAm": "1951-11-07T17:53:01.130Z",
          "behoerde": "tempor occaecat eu sed",
          "aktenzeichen": "incididunt commodo aute eu"
        },
        {
          "angelegtAm": "2014-05-26T21:43:07.376Z",
          "behoerde": "do",
          "aktenzeichen": "in laborum aute eu"
        }
      ],
      "sprengstoffrechtlicheErlaubnisErweitert": [
        {
          "angelegtAm": "2008-09-12T01:43:07.488Z",
          "behoerde": "est Ut pariatur cupidatat",
          "aktenzeichen": "laborum occaecat consectetur consequat"
        },
        {
          "angelegtAm": "2015-03-17T11:46:34.813Z",
          "behoerde": "d",
          "aktenzeichen": "exercitation minim"
        }
      ],
      "waffenbesitzVerbot": [
        {
          "angelegtAm": "1991-03-07T18:53:43.274Z",
          "behoerde": "veniam labore consequat eu mollit",
          "aktenzeichen": "deserunt"
        },
        {
          "angelegtAm": "1956-05-27T13:40:51.106Z",
          "behoerde": "consectetur",
          "aktenzeichen": "quis irure ea non"
        }
      ],
      "verlustDeutscheStaatsangehoerigkeit": "2011-10-27T21:39:38.359Z",
      "optionsDeutscherDaten": {
        "befristungBis": "1966-08-09T09:46:38.849Z"
      },
      "artStaatsangehoerigkeit": "minim",
      "frueheresOm": "ad",
      "zukuenftigesOm": "fugia",
      "staatsangehoerigkeiten": [
        {
          "schluessel": "voluptate in",
          "text": "aute id",
          "art": "est ut",
          "nachweisdaten": {
            "datum": "1986-10-01T08:49:50.213Z",
            "aktenzeichen": "reprehenderit adipisicing",
            "behoerde": "ex mollit ad fugiat"
          },
          "nichtEuBuerger": true,
          "staatEuMitglied": false
        },
        {
          "schluessel": "exercitation aute Lorem",
          "text": "mollit sunt commo",
          "art": "ullamco fugiat dolore",
          "nachweisdaten": {
            "datum": "2005-11-29T07:26:25.806Z",
            "aktenzeichen": "dolore reprehenderit",
            "behoerde": "quis id consectetur labore"
          },
          "nichtEuBuerger": true,
          "staatEuMitglied": false
        }
      ]
    },
    {
      "auskunftssperre": "Duis pariatur",
      "ordnungsmerkmal": "sunt adipisicing do consectetur pariatur",
      "personstatus": "AKTUELLER_SEEMANN",
      "geburtsdaten": {
        "datum": {
          "datum": "ea sit minim ad"
        },
        "ort": "aute quis veniam",
        "staat": "Duis ut nisi deserunt"
      },
      "geschlecht": "consequat Lorem minim officia",
      "sterbedaten": {
        "datum": "1952-02-11T05:43:16.076Z",
        "ort": "laboris"
      },
      "familienstanddaten": {
        "familienstand": "non cupidatat aliqua est commodo",
        "datum": "1947-12-23T15:52:10.430Z"
      },
      "namen": {
        "vornamen": [
          {
            "vorname": "voluptate fugiat Lorem non",
            "rufname": false
          },
          {
            "vorname": "fugiat laborum pariatur",
            "rufname": true
          }
        ],
        "familienname": {
          "name": "sit aliquip quis aute",
          "namensbestandteil": "quis pariatur dolore",
          "unstrukturierteSchreibweise": "proident eu dolore"
        },
        "geburtsname": {
          "name": "dolore nostrud",
          "namensbestandteil": "commodo consequat ut et",
          "unstrukturierteSchreibweise": "deserunt velit quis"
        },
        "ehename": {
          "name": "anim quis sed esse Lorem",
          "namensbestandteil": "et",
          "unstrukturierteSchreibweise": "Exc"
        },
        "familiennameNachPass": {
          "name": "ut dolor non laboris",
          "namensbestandteil": "id Excep",
          "unstrukturierteSchreibweise": "ex officia quis"
        },
        "ordensname": "quis sit sunt",
        "kuenstlername": "ad dolor reprehenderit Excepteur",
        "doktorgrad": "cillum commodo adipisicing magna officia"
      },
      "doktorgradNachweis": "nisi veniam",
      "sperren": [
        {
          "frist": "2019-05-19T14:24:50.367Z",
          "grund": "eiusmod sit"
        },
        {
          "frist": "1947-04-02T22:45:50.450Z",
          "grund": "cil"
        }
      ],
      "religion": "in pariatur reprehenderit",
      "wahldaten": {
        "wvzEintrag": {
          "gebietskoerperschaft": "sed officia enim",
          "staat": "irure adipisic"
        },
        "ausschluesse": [
          {
            "grund": "nulla sint esse",
            "behoerde": "amet laboris qui dolore",
            "aktenzeichen": "ad nulla aliquip ut dolore",
            "wahlauschlussBis": "1955-08-12T14:30:19.723Z"
          },
          {
            "grund": "nulla ipsum",
            "behoerde": "Duis exercitation et",
            "aktenzeichen": "veniam",
            "wahlauschlussBis": "2019-08-17T22:51:07.218Z"
          }
        ]
      },
      "zuzugsdaten": {
        "zuzugEu": "1978-12-17T01:47:38.873Z",
        "zuzugSamtgemeinde": "1994-04-02T03:51:05.008Z",
        "zuzugOrtsteil": "2020-04-05T14:12:38.874Z",
        "zuzugBund": "2018-02-14T08:26:35.478Z",
        "zuzugLand": "1985-12-22T15:50:00.769Z",
        "zuzugRegierungsbezirk": "1968-02-12T23:16:05.031Z",
        "zuzugKreis": "2004-07-24T23:36:15.993Z",
        "zuzugGemeinde": "1965-06-18T18:59:05.061Z",
        "mitHauptwohnungGemeldetSeit": "1955-02-25T15:22:21.238Z"
      },
      "wohnungen": [
        {
          "melderegisterAnschrift": {
            "gemeindeschluessel": "consectetur quis esse",
            "hausnummer": "Ut incididunt deserunt laboris",
            "buchstabeHausnummer": "cupidatat qui ut",
            "teilnummerHausnummer": "laboris dolore ipsum anim quis",
            "ort": "dolore",
            "ortsteil": "sunt qui enim anim ipsum",
            "postleitzahl": "ipsum dolor quis",
            "stockwerk": "aliquip labore pariatur",
            "appartmentnummer": "dolor proident",
            "strasse": "reprehende",
            "wohnungsgeber": "qui pariatur aute proident",
            "zusatz": "qui laboris",
            "staat": "sed do elit dolor",
            "anschriftUnbekannt": false,
            "sperrvermerk": false,
            "strassenschluessel": "sed"
          },
          "einzug": "1961-01-12T15:40:53.041Z",
          "auszug": {
            "jahr": "ut velit non",
            "jahrMonat": "proident commodo et",
            "datum": "2021-06-13T11:09:37.307Z"
          },
          "statuswechsel": "1979-04-29T13:06:01.672Z",
          "meldungStatuswechsel": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "1957-06-01T22:57:36.366Z"
          },
          "meldungEinzug": {
            "art": "PERSOENLICHE_MELDUNG",
            "datum": "2020-06-08T04:46:46.093Z"
          },
          "meldungAuszug": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "1943-01-27T00:06:28.015Z"
          },
          "innerhalb": false,
          "belegGebunden": true,
          "gefoerdert": false,
          "scheinadresse": true
        },
        {
          "melderegisterAnschrift": {
            "gemeindeschluessel": "tempor officia et Excepteur aliquip",
            "hausnummer": "quis veniam exercitation",
            "buchstabeHausnummer": "anim aliqua Duis",
            "teilnummerHausnummer": "",
            "ort": "officia nostrud",
            "ortsteil": "dolor consequat",
            "postleitzahl": "adipisicing dolore consectetur",
            "stockwerk": "tempor eiusmod aliquip laborum",
            "appartmentnummer": "eiusmod d",
            "strasse": "et",
            "wohnungsgeber": "voluptate e",
            "zusatz": "qui consequat",
            "staat": "irure laboris deserunt",
            "anschriftUnbekannt": false,
            "sperrvermerk": false,
            "strassenschluessel": "elit eu fugiat dolore irure"
          },
          "einzug": "1970-12-03T03:42:44.510Z",
          "auszug": {
            "jahr": "officia commodo in",
            "jahrMonat": "laboris esse qui",
            "datum": "2010-07-13T21:09:40.333Z"
          },
          "statuswechsel": "2007-04-27T13:23:11.736Z",
          "meldungStatuswechsel": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "1996-04-15T05:18:44.735Z"
          },
          "meldungEinzug": {
            "art": "PERSOENLICHE_MELDUNG",
            "datum": "1973-10-19T01:59:22.076Z"
          },
          "meldungAuszug": {
            "art": "MELDUNG_VON_AMTS_WEGEN",
            "datum": "1995-12-28T19:38:50.808Z"
          },
          "innerhalb": false,
          "belegGebunden": false,
          "gefoerdert": false,
          "scheinadresse": false
        }
      ],
      "passdaten": [
        {
          "art": "adipisicing amet",
          "serienummer": "enim ea id",
          "geschlecht": "Lorem fugiat dolore",
          "ausstellendeBehoerde": "quis sit laboris consectetur",
          "ausstellungsdatum": "2014-11-18T11:31:56.608Z",
          "ablaufdatum": "1946-09-20T00:59:47.035Z"
        },
        {
          "art": "qui sin",
          "serienummer": "consectetur sed voluptate ut",
          "geschlecht": "irure amet et",
          "ausstellendeBehoerde": "occaecat Lorem nulla",
          "ausstellungsdatum": "1998-02-15T06:11:50.383Z",
          "ablaufdatum": "2006-01-13T09:48:54.040Z"
        }
      ],
      "partner": {
        "namen": {
          "vornamen": [
            {
              "vorname": "nisi pariatur officia",
              "rufname": false
            },
            {
              "vorname": "qui culpa do laboris",
              "rufname": true
            }
          ],
          "familienname": {
            "name": "tempor deseru",
            "namensbestandteil": "Ut officia",
            "unstrukturierteSchreibweise": "ipsum"
          },
          "geburtsname": {
            "name": "ea consequat et ad",
            "namensbestandteil": "do ipsum",
            "unstrukturierteSchreibweise": "pariatur esse aute commodo"
          },
          "ehename": {
            "name": "Ut pariatu",
            "namensbestandteil": "cupidatat minim",
            "unstrukturierteSchreibweise": "esse nulla adipisicing dolor"
          },
          "familiennameNachPass": {
            "name": "dolor in",
            "namensbestandteil": "eiusmod consequat ut et",
            "unstrukturierteSchreibweise": "minim aute"
          },
          "ordensname": "deserunt occaecat",
          "kuenstlername": "qui voluptate ullamco",
          "doktorgrad": "minim dolor"
        },
        "art": "LP",
        "ordnungsmerkmal": "ad et ea",
        "sperren": [
          {
            "frist": "2003-03-14T10:24:03.654Z",
            "grund": "reprehenderit sit"
          },
          {
            "frist": "1977-03-24T22:35:24.810Z",
            "grund": "adipisicing ullamco id anim Excepteur"
          }
        ],
        "personstatus": "EXTERNE_PERSON",
        "geburtsdatum": {
          "datum": "occaecat magna"
        },
        "geschlecht": "ut nisi non",
        "anschrift": {
          "gemeindeschluessel": "amet in sint",
          "hausnummer": "dolore",
          "buchstabeHausnummer": "aliqua adipisicing in aute incididunt",
          "teilnummerHausnummer": "sint ",
          "ort": "adipisicing ullamco veniam dolore fugiat",
          "ortsteil": "ullamco est",
          "postleitzahl": "non labore quis consequat",
          "stockwerk": "irure aute",
          "appartmentnummer": "do anim ut laborum",
          "strasse": "qui cillum elit ipsum nisi",
          "wohnungsgeber": "do dolore commod",
          "zusatz": "do sint veniam ea",
          "staat": "Lorem",
          "anschriftUnbekannt": true,
          "sperrvermerk": true,
          "strassenschluessel": ""
        },
        "sterbedaten": {
          "datum": "1957-08-18T10:34:16.361Z",
          "ort": "commodo Excepteur"
        }
      },
      "kinder": [
        {
          "namen": {
            "vornamen": [
              {
                "vorname": "et",
                "rufname": true
              },
              {
                "vorname": "velit amet sit deserunt",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "sunt dolor pariatur",
              "namensbestandteil": "aute magna",
              "unstrukturierteSchreibweise": "nostrud nisi sunt c"
            },
            "geburtsname": {
              "name": "ut deserunt qui occaecat",
              "namensbestandteil": "irure aliquip tempor",
              "unstrukturierteSchreibweise": "elit ullamco"
            },
            "ehename": {
              "name": "pariatur dolor deserunt",
              "namensbestandteil": "Ut tempor dolor commodo eiusmod",
              "unstrukturierteSchreibweise": "consectetur sed"
            },
            "familiennameNachPass": {
              "name": "deserunt Ut",
              "namensbestandteil": "exercitation sit pariatur",
              "unstrukturierteSchreibweise": "dolore"
            },
            "ordensname": "in",
            "kuenstlername": "cillum incididunt in adipisicing",
            "doktorgrad": "est"
          },
          "ordnungsmerkmal": "Lo",
          "sperren": [
            {
              "frist": "2014-12-30T10:44:10.140Z",
              "grund": "do irure ea ipsum"
            },
            {
              "frist": "1986-03-06T20:46:44.826Z",
              "grund": "eu nostrud"
            }
          ],
          "personstatus": "EXTERNE_PERSON",
          "geburtsdatum": {
            "datum": "tempor occaecat"
          },
          "sterbedaten": {
            "datum": "1962-01-30T15:11:32.709Z",
            "ort": "in vel"
          },
          "geschlecht": "consectetur id do cupidatat reprehenderit"
        },
        {
          "namen": {
            "vornamen": [
              {
                "vorname": "in enim Duis",
                "rufname": false
              },
              {
                "vorname": "tempor elit ad offici",
                "rufname": false
              }
            ],
            "familienname": {
              "name": "do ut tempor",
              "namensbestandteil": "reprehenderit do",
              "unstrukturierteSchreibweise": "in aliqua ullamco Excepteur"
            },
            "geburtsname": {
              "name": "consect",
              "namensbestandteil": "id tempor",
              "unstrukturierteSchreibweise": "voluptate"
            },
            "ehename": {
              "name": "aute non nisi nulla minim",
              "namensbestandteil": "magna laboris veniam occaecat qui",
              "unstrukturierteSchreibweise": "dolore sit eiusmod culpa"
            },
            "familiennameNachPass": {
              "name": "quis do dolor nulla",
              "namensbestandteil": "ut veniam et adipisicing in",
              "unstrukturierteSchreibweise": "dolore sunt"
            },
            "ordensname": "Excepteur id",
            "kuenstlername": "cillum consectetur Ut",
            "doktorgrad": "in culpa aliquip"
          },
          "ordnungsmerkmal": "sunt anim",
          "sperren": [
            {
              "frist": "1942-10-07T23:26:41.382Z",
              "grund": "dolore quis"
            },
            {
              "frist": "2002-08-24T13:43:42.817Z",
              "grund": "sint laboris nisi"
            }
          ],
          "personstatus": "EXTERNE_PERSON",
          "geburtsdatum": {
            "datum": "Lorem sit"
          },
          "sterbedaten": {
            "datum": "1996-02-16T00:34:04.468Z",
            "ort": "de"
          },
          "geschlecht": "minim nisi nulla ullamco"
        }
      ],
      "vertreter": [
        {
          "ordnungsmerkmal": "nostrud non officia dolore",
          "personstatus": "INAKTUELLER_FESTER_EINWOHNER",
          "namen": {
            "vornamen": [
              {
                "vorname": "quis exercitation",
                "rufname": true
              },
              {
                "vorname": "laboris voluptate",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "proident eiusmod",
              "namensbestandteil": "anim adipisicing velit",
              "unstrukturierteSchreibweise": "sed officia Lorem"
            },
            "geburtsname": {
              "name": "minim reprehenderit",
              "namensbestandteil": "enim sint ",
              "unstrukturierteSchreibweise": "consectetur reprehenderit ut sed ut"
            },
            "ehename": {
              "name": "magna mollit id nisi non",
              "namensbestandteil": "fugiat",
              "unstrukturierteSchreibweise": "inc"
            },
            "familiennameNachPass": {
              "name": "in pariatur velit nisi",
              "namensbestandteil": "incididunt ",
              "unstrukturierteSchreibweise": "nostrud deserunt laboris id"
            },
            "ordensname": "dolore amet",
            "kuenstlername": "veniam ad sint proident",
            "doktorgrad": "esse amet dolor"
          },
          "art": "quis ut sunt et",
          "geburtsdatum": {
            "datum": "ut adipisicing"
          },
          "geschlecht": "dolore nisi labore",
          "sterbedaten": {
            "datum": "1992-12-05T04:13:54.238Z",
            "ort": "dolor veniam"
          },
          "endeVertretung": "1990-08-29T09:17:19.340Z",
          "sperren": [
            {
              "frist": "1962-07-20T15:01:57.498Z",
              "grund": "id amet laboris"
            },
            {
              "frist": "1970-09-02T01:47:13.432Z",
              "grund": "culpa dolore esse commodo"
            }
          ],
          "anschrift": {
            "gemeindeschluessel": "laborum proident commodo",
            "hausnummer": "ad ipsum",
            "buchstabeHausnummer": "dolor ea anim cupidatat",
            "teilnummerHausnummer": "sunt in",
            "ort": "nulla cupidatat",
            "ortsteil": "sit",
            "postleitzahl": "ut minim",
            "stockwerk": "quis",
            "appartmentnummer": "veniam ut dolo",
            "strasse": "tempor ex eiusmod deserunt",
            "wohnungsgeber": "esse",
            "zusatz": "occaecat",
            "staat": "amet velit",
            "anschriftUnbekannt": true,
            "sperrvermerk": false,
            "strassenschluessel": "laborum"
          },
          "bezeichnungJuristischePerson": "est",
          "natuerlichePerson": false
        },
        {
          "ordnungsmerkmal": "sed ut Lorem culpa",
          "personstatus": "JURISTISCHE_PERSON",
          "namen": {
            "vornamen": [
              {
                "vorname": "Lorem mollit",
                "rufname": false
              },
              {
                "vorname": "commodo non fugiat Duis",
                "rufname": true
              }
            ],
            "familienname": {
              "name": "labore nulla dolor quis sunt",
              "namensbestandteil": "reprehenderit labore id commodo",
              "unstrukturierteSchreibweise": "Excepteur adipisicing"
            },
            "geburtsname": {
              "name": "consequat laboris voluptate enim Lorem",
              "namensbestandteil": "do dolore esse",
              "unstrukturierteSchreibweise": "ut dolore fugiat"
            },
            "ehename": {
              "name": "Duis irure nostrud",
              "namensbestandteil": "dolore ipsum occaecat sit",
              "unstrukturierteSchreibweise": "consequat in tempor ex"
            },
            "familiennameNachPass": {
              "name": "adipisicing esse non",
              "namensbestandteil": "voluptate qui",
              "unstrukturierteSchreibweise": "sunt dolor ut dolore"
            },
            "ordensname": "Lorem laboris",
            "kuenstlername": "nostrud ut anim do",
            "doktorgrad": "sint"
          },
          "art": "est Lorem",
          "geburtsdatum": {
            "datum": "in cupidatat cillum sed"
          },
          "geschlecht": "id esse",
          "sterbedaten": {
            "datum": "1945-05-25T21:14:00.271Z",
            "ort": "occaecat ex nostrud"
          },
          "endeVertretung": "1951-01-25T20:08:56.798Z",
          "sperren": [
            {
              "frist": "1956-10-16T04:17:35.835Z",
              "grund": "non dolor in fugiat"
            },
            {
              "frist": "1960-04-03T22:11:18.046Z",
              "grund": "et deserunt"
            }
          ],
          "anschrift": {
            "gemeindeschluessel": "elit in",
            "hausnummer": "cupidatat consequat sit Ut",
            "buchstabeHausnummer": "dolor ut do",
            "teilnummerHausnummer": "culpa enim quis",
            "ort": "irure officia",
            "ortsteil": "cillum consectetur est dolore eu",
            "postleitzahl": "veniam consequat",
            "stockwerk": "in",
            "appartmentnummer": "ut enim do",
            "strasse": "Excepteur ea nisi",
            "wohnungsgeber": "non aute nostrud",
            "zusatz": "aute",
            "staat": "est aliquip ipsum pariatur",
            "anschriftUnbekannt": false,
            "sperrvermerk": false,
            "strassenschluessel": "ipsum d"
          },
          "bezeichnungJuristischePerson": "ut incididunt amet",
          "natuerlichePerson": false
        }
      ],
      "fruehereNamen": {
        "fruehererFamilienname": [
          {
            "familienname": {
              "name": "ipsum laboris deserunt",
              "namensbestandteil": "ut magna est adipisicing",
              "unstrukturierteSchreibweise": "et sint cupidatat in occa"
            },
            "aenderungsdatum": "2004-11-16T06:51:48.159Z"
          },
          {
            "familienname": {
              "name": "exercitation eiusmod esse aliqua dolor",
              "namensbestandteil": "consequat proident quis elit",
              "unstrukturierteSchreibweise": "nulla aute"
            },
            "aenderungsdatum": "2011-08-22T15:13:40.071Z"
          }
        ],
        "fruehererVorname": [
          {
            "vorname": [
              {
                "vorname": "aliquip Duis consequat enim",
                "rufname": true
              },
              {
                "vorname": "Duis incididunt",
                "rufname": false
              }
            ],
            "aenderungsdatum": "1974-10-17T19:08:59.273Z"
          },
          {
            "vorname": [
              {
                "vorname": "Duis",
                "rufname": true
              },
              {
                "vorname": "eu",
                "rufname": false
              }
            ],
            "aenderungsdatum": "1982-05-24T22:10:33.139Z"
          }
        ]
      },
      "waffenrechtlicheErlaubnisErweitert": [
        {
          "angelegtAm": "1957-05-28T06:25:59.464Z",
          "behoerde": "Lorem sint occaecat",
          "aktenzeichen": "nostrud dolor"
        },
        {
          "angelegtAm": "1973-09-17T03:51:24.948Z",
          "behoerde": "dolore eu",
          "aktenzeichen": "ex"
        }
      ],
      "sprengstoffrechtlicheErlaubnisErweitert": [
        {
          "angelegtAm": "1991-10-20T03:00:12.088Z",
          "behoerde": "proident do",
          "aktenzeichen": "eu dolore proident"
        },
        {
          "angelegtAm": "2014-07-11T15:50:36.750Z",
          "behoerde": "est velit proident",
          "aktenzeichen": "aute amet"
        }
      ],
      "waffenbesitzVerbot": [
        {
          "angelegtAm": "2011-03-28T23:45:59.813Z",
          "behoerde": "deserunt nulla aliqua aliquip",
          "aktenzeichen": "est consequat"
        },
        {
          "angelegtAm": "2018-09-28T21:18:03.612Z",
          "behoerde": "aliquip veniam ut exercitation eiusmod",
          "aktenzeichen": "laborum minim labore veniam"
        }
      ],
      "verlustDeutscheStaatsangehoerigkeit": "1954-03-24T00:11:57.494Z",
      "optionsDeutscherDaten": {
        "befristungBis": "2019-03-23T17:09:21.401Z"
      },
      "artStaatsangehoerigkeit": "occaecat ut ipsum",
      "frueheresOm": "irure veniam ut in",
      "zukuenftigesOm": "sed ea id",
      "staatsangehoerigkeiten": [
        {
          "schluessel": "Duis dolor",
          "text": "Lorem id minim",
          "art": "pariatur ipsum proident",
          "nachweisdaten": {
            "datum": "1999-08-17T16:26:58.454Z",
            "aktenzeichen": "do commodo Ut magna",
            "behoerde": "dolore"
          },
          "nichtEuBuerger": false,
          "staatEuMitglied": false
        },
        {
          "schluessel": "consectetur consequat mini",
          "text": "voluptate dolor",
          "art": "consectetur incididunt",
          "nachweisdaten": {
            "datum": "1992-01-09T11:29:38.433Z",
            "aktenzeichen": "enim nulla",
            "behoerde": "aliquip aute magna laboris"
          },
          "nichtEuBuerger": true,
          "staatEuMitglied": true
        }
      ]
    }
  ]
}
```

#### Error handling

If client-side errors, server-side errors or errors that cannot be assigned to either the client or the server occur
during the rest request within the service, an error response is returned to the caller via the event bus.

```json
{
  "message": "THE GENERIC ERROR MESSAGE"
}
```