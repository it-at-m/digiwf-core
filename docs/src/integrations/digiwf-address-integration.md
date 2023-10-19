# DigiWF Address Integration

Die Address Integration bindet den Address-Service der Stadt München an die DigiWF Plattform an, wodurch Informationen
zu Adressen sowie Straßen abgerufen werden können.

## Verwendung

Die Address Integration bietet 3 verschiedene APIs an, um Adressen und Straßen abzurufen.

### Adressen bundesweit

Die Adressdienstanfragen müssen über die in `addressServiceIntegration.json` definierte Element-Template gemacht werden.
Der Dienst und die Vorlage bieten acht Arten von Anfragen.
Der Anfragetyp kann über das Dropdown-Menü der Elementvorlage im Feld `Event Type` festgelegt werden.

* `searchAdressenBundesweit`
* `checkAdresseMuenchen`
* `listAdressenMuenchen`
* `listAenderungenMuenchen`
* `searchAdressenMuenchen`
* `searchAdressenGeoMuenchen`
* `findStrasseByIdMuenchen`
* `listStrassenMuenchen`

Für jeden Anfragetyp muss das Datenpaket als JSON-Objekt im Anfragefeld der Elementvorlage definiert werden.
Die Antwort ist ebenfalls ein JSON-Objekt.
Ein entscheidendes und obligatorisches JSON-Objektattribut für eine Anfrage ist `eventType`.
Dieses Attribut ist für die korrekte Deserialisierung des JSON-Datenpakets der Anfrage innerhalb des Integrationsservices erforderlich

Die genauen Eigenschaften der Anfrage- und Antwortattribute, die unten beschrieben sind, finden Sie unter folgendem Link:

https://address-service-test.muenchen.de/swagger-ui/index.html

#### searchAdressenBundesweit

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "searchAdressenBundesweit",
  "query": "culpa incididunt occaecat ut",
  "plz": "conse",
  "ortsname": "culpa incididunt occaecat ut",
  "gemeindeschluessel": "culpa incididunt occaecat ut",
  "hausnummerfilter": "71564634",
  "hausnummerfilter": "48360614",
  "buchstabefilter": "mollit eu",
  "buchstabefilter": "anim sed quis consequat est",
  "sort": "culpa incididunt occaecat ut",
  "sortdir": "culpa incididunt occaecat ut",
  "page": "0",
  "pagesize": "20"
}
```

Die Antwort lautet wie folgt:

```json
{
  "page": {
    "size": 20,
    "number": 4711999,
    "totalElements": 86180198,
    "totalPages": -97380737,
    "numberOfElements": 11904480
  },
  "content": [
    {
      "score": 3966515.834937349,
      "adresse": {
        "adresse": "esse reprehenderit tempor",
        "hausnummer": -202318,
        "ortsname": "nostrud",
        "position": {
          "utm": {
            "north": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "east": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wgs": {
            "lat": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "lon": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "geohash": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "fragment": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        },
        "strasseId": "consectetur ",
        "strassenname": "dolore ad ullamco",
        "buchstabe": "dolor ",
        "geozuordnungen": {
          "postleitzahl": "in nulla nostrud sunt",
          "gemeinde": "Duis enim",
          "landesschluessel": "aute deserunt ad commodo do",
          "kreis": "anim labore voluptate su",
          "ortsteil": "ad magna qui",
          "ortsteilname": "Lorem",
          "regierungsbezirk": "E",
          "gemeindeschluessel": "ut deserunt amet in"
        }
      }
    },
    {
      "score": 8113004.566452727,
      "adresse": {
        "adresse": "labore occaecat",
        "hausnummer": -5232851,
        "ortsname": "aute ullamco ut ut cillum",
        "position": {
          "utm": {
            "north": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "east": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wgs": {
            "lat": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "lon": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "geohash": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "fragment": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        },
        "strasseId": "culpa i",
        "strassenname": "culpa",
        "buchstabe": "cillum ea occaecat reprehenderit",
        "geozuordnungen": {
          "postleitzahl": "qui pariatur non",
          "gemeinde": "iru",
          "landesschluessel": "pariatur",
          "kreis": "eu in sint dolore",
          "ortsteil": "nostrud mollit Ut nisi sunt",
          "ortsteilname": "id sint esse consequat",
          "regierungsbezirk": "veniam Duis voluptate",
          "gemeindeschluessel": "Lorem velit amet"
        }
      }
    }
  ]
}
```

### Adressen München

#### checkAdresseMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "checkAdresseMuenchen",
  "adresse": "culpa incididunt occaecat ut",
  "strassenname": "culpa incididunt occaecat ut",
  "strasseId": "303",
  "hausnummer": "culpa incididunt occaecat ut",
  "zusatz": "culpa incididunt occaecat ut",
  "plz": "culpa incididunt occaecat ut",
  "ortsname": "culpa incididunt occaecat ut",
  "gemeindeschluessel": "culpa incididunt occaecat ut"
}

```

Die Antwort lautet wie folgt:

```json
{
  "adressId": "nostrud",
  "adresse": "qui pariatur irure",
  "hausnummer": 88350807,
  "ortsname": "id dolor deserunt cupidatat dolore",
  "position": {
    "utm": {
      "north": -33535251.057741977,
      "east": 57590168.9756417
    },
    "wgs": {
      "lat": -26620807.119553417,
      "lon": 76802579.26506698,
      "geohash": "consequat irure occaecat",
      "fragment": true
    }
  },
  "strasseId": "Lorem aliquip in",
  "strassenname": "cupidatat officia ullamco Lorem Ut",
  "strassennameAbgekuerzt": "nisi aliqua enim elit a",
  "strassennameKurz": "voluptate nisi",
  "wirkung": {
    "status": "AKTIV",
    "vorgang": "UMNUMMERIERUNG",
    "wirkungsdatum": "1990-01-27"
  },
  "buchstabe": "reprehenderit cillum sunt ad",
  "ehemaligeAdresse": "eiusmod non magna consectetur",
  "geozuordnungen": {
    "baublock": "pariatur reprehenderit",
    "erhaltungssatzung": "nulla velit",
    "gemarkung": "in esse adipisicing",
    "grundschule": "ut non irure nisi",
    "kaminkehrerbezirk": "consequat ex",
    "mittelschule": "adipisicing offici",
    "parklizenzgebietId": -94121994,
    "parklizenzgebietName": "tempor deserunt consectetur",
    "polizeiinspektion": "Lorem sunt",
    "postleitzahl": "in officia deserunt esse sit",
    "verwaltungszuteilung": {
      "gemeinde": "culpa consectetur quis exercitation sed",
      "landesschluessel": "sint in ullamco",
      "kreis": "culpa sit dolore adipisicing",
      "ortsteil": "non",
      "regierungsbezirk": "est in sint",
      "stadtbezirk": "pariatur sit non Excepteur",
      "stadtbezirksteil": "sit non anim laboris",
      "stadtbezirksviertel": "cillum non cup",
      "gemeindeschluessel": "commodo nisi consequat ipsum"
    },
    "wahleinteilungen": {
      "stimmbezirk": 83357636,
      "stimmkreis": 88488065,
      "wahlkreis": 18979577,
      "wahlbezirk": 93264949
    }
  }
}
```

#### listAdressenMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "listAdressenMuenchen",
  "baublock": "amet Lorem fugiat",
  "erhaltungssatzung": "amet Lorem fugiat",
  "gemarkung": "amet Lorem fugiat",
  "kaminkehrerbezirk": "amet Lorem fugiat",
  "plz": "amet Lorem fugiat",
  "mittelschule": "amet Lorem fugiat",
  "grundschule": "amet Lorem fugiat",
  "polizeiinspektio": "amet Lorem fugiat",
  "polizeiinspektion": "eu incididunt",
  "stimmbezirk": -93305045,
  "stimmkreis": -93305045,
  "wahlbezirk": -93305045,
  "wahlkreis": -93305045,
  "stadtbezirk": "amet Lorem fugiat",
  "stadtbezirksteil": "amet Lorem fugiat",
  "stadtbezirksviertel": "amet Lorem fugiat",
  "sort": "culpa incididunt occaecat ut",
  "sortdir": "culpa incididunt occaecat ut",
  "page": 0,
  "pagesize": 20
}

```

Die Antwort lautet wie folgt:

```json
{
  "page": {
    "size": 20,
    "number": 15947864,
    "totalElements": 47352921,
    "totalPages": 44413736,
    "numberOfElements": -62364652
  },
  "content": [
    {
      "score": 92866295.74778003,
      "adresse": {
        "adressId": "ad cillum",
        "adresse": "in adipisicing",
        "hausnummer": -55290609,
        "ortsname": "ut",
        "position": {
          "utm": {
            "north": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "east": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wgs": {
            "lat": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "lon": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "geohash": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "fragment": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        },
        "strasseId": "minim anim non",
        "strassenname": "reprehenderit sed qu",
        "strassennameAbgekuerzt": "tempor do id sunt magna",
        "strassennameKurz": "elit tempor consectetur",
        "wirkung": {
          "status": "AKTIV",
          "vorgang": "EINZIEHUNG",
          "wirkungsdatum": "1965-01-17"
        },
        "buchstabe": "voluptate culpa",
        "ehemaligeAdresse": "proident cillum ad Ut",
        "geozuordnungen": {
          "baublock": "sit aliquip magna",
          "erhaltungssatzung": "qui fugiat",
          "gemarkung": "Duis consequat anim consectetur",
          "grundschule": "laborum nulla ipsum qui cupidatat",
          "kaminkehrerbezirk": "voluptate quis",
          "mittelschule": "culpa sit",
          "parklizenzgebietId": -91228677,
          "parklizenzgebietName": "occaecat labore proident",
          "polizeiinspektion": "culpa aliquip",
          "postleitzahl": "cillum aute",
          "verwaltungszuteilung": {
            "gemeinde": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "landesschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "kreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "ortsteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "regierungsbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksviertel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "gemeindeschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wahleinteilungen": {
            "stimmbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stimmkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        }
      }
    },
    {
      "score": -55518562.8487318,
      "adresse": {
        "adressId": "exercitation veniam qui esse do",
        "adresse": "id sunt",
        "hausnummer": -50110105,
        "ortsname": "eiusmod culpa aliquip labor",
        "position": {
          "utm": {
            "north": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "east": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wgs": {
            "lat": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "lon": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "geohash": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "fragment": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        },
        "strasseId": "cupidatat ad sed est non",
        "strassenname": "deserunt nulla cillum ex aute",
        "strassennameAbgekuerzt": "est Duis dolore velit",
        "strassennameKurz": "occaecat Ut",
        "wirkung": {
          "status": "HISTORISCH",
          "vorgang": "UMNUMMERIERUNG",
          "wirkungsdatum": "1943-07-28"
        },
        "buchstabe": "amet ",
        "ehemaligeAdresse": "nisi aliqua cillum nostrud",
        "geozuordnungen": {
          "baublock": "labore culpa et Duis",
          "erhaltungssatzung": "sunt laborum",
          "gemarkung": "veniam dolor",
          "grundschule": "exercitation qui nisi",
          "kaminkehrerbezirk": "proident Duis",
          "mittelschule": "cillum",
          "parklizenzgebietId": 59096451,
          "parklizenzgebietName": "Lorem anim fugiat",
          "polizeiinspektion": "in do",
          "postleitzahl": "la",
          "verwaltungszuteilung": {
            "gemeinde": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "landesschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "kreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "ortsteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "regierungsbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksviertel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "gemeindeschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wahleinteilungen": {
            "stimmbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stimmkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        }
      }
    }
  ]
}
```

#### listAenderungenMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "listAenderungenMuenchen",
  "wirkungsdatumvon": "1945-07-31",
  "wirkungsdatumbis": "1945-07-31",
  "strassenname": "culpa incididunt occaecat ut",
  "hausnummer": 5,
  "plz": "culpa incididunt occaecat ut",
  "zusatz": "culpa incididunt occaecat ut",
  "sort": "culpa incididunt occaecat ut",
  "sortdir": "culpa incididunt occaecat ut",
  "page": 0,
  "pagesize": 20
}
```

Die Antwort lautet wie folgt:

```json
{
  "page": {
    "size": 20,
    "number": -37723010,
    "totalElements": -63979338,
    "totalPages": -148968,
    "numberOfElements": 6810440
  },
  "content": [
    {
      "adresse": {
        "adressId": "laborum reprehenderit",
        "adresse": "aliqua dolore",
        "hausnummer": -55076544,
        "ortsname": "Lorem et",
        "position": {
          "utm": {
            "north": 14389892.193495378,
            "east": 49344980.28482449
          },
          "wgs": {
            "lat": -69938036.92859699,
            "lon": 90689722.18807697,
            "geohash": "ad ullamco",
            "fragment": false
          }
        },
        "strasseId": "do nulla et ea",
        "strassenname": "",
        "strassennameAbgekuerzt": "eiusmod off",
        "strassennameKurz": "velit cupidatat sed",
        "wirkung": {
          "status": "AKTIV",
          "vorgang": "EINZIEHUNG",
          "wirkungsdatum": "2003-07-24"
        },
        "buchstabe": "ut dolore",
        "ehemaligeAdresse": "mollit Duis qui",
        "geozuordnungen": {
          "baublock": "ea in",
          "erhaltungssatzung": "magna veniam occaecat quis ut",
          "gemarkung": "dolor do et",
          "grundschule": "aute est sed et cupidatat",
          "kaminkehrerbezirk": "exerci",
          "mittelschule": "id",
          "parklizenzgebietId": 94922847,
          "parklizenzgebietName": "dolor Ut",
          "polizeiinspektion": "ipsum Ut",
          "postleitzahl": "occaecat magna",
          "verwaltungszuteilung": {
            "gemeinde": "deserunt proident qu",
            "landesschluessel": "dolor dolore aliqua sint",
            "kreis": "non dolore",
            "ortsteil": "et",
            "regierungsbezirk": "sed incididunt culpa laboris",
            "stadtbezirk": "ex Lorem ut",
            "stadtbezirksteil": "occaecat tempor exercitation elit in",
            "stadtbezirksviertel": "dolor proident est ",
            "gemeindeschluessel": "est in aute"
          },
          "wahleinteilungen": {
            "stimmbezirk": 74324945,
            "stimmkreis": -77251994,
            "wahlkreis": 5286704,
            "wahlbezirk": 99862331
          }
        }
      },
      "adresseVorgaenger": {
        "adressId": "Excepteur eiusmod minim",
        "adresse": "tempor Excepteur",
        "hausnummer": 26301953,
        "ortsname": "sint dolor do",
        "position": {
          "utm": {
            "north": 95724673.61278659,
            "east": -61300742.3780092
          },
          "wgs": {
            "lat": -49914817.939602464,
            "lon": -89986743.1099866,
            "geohash": "minim enim dolor quis",
            "fragment": true
          }
        },
        "strasseId": "qui consequat mollit nostrud",
        "strassenname": "pariatur sed enim nisi tempor",
        "strassennameAbgekuerzt": "Dui",
        "strassennameKurz": "labore amet commodo",
        "wirkung": {
          "status": "HISTORISCH",
          "vorgang": "EINZIEHUNG",
          "wirkungsdatum": "1964-10-17"
        },
        "buchstabe": "Ut in",
        "ehemaligeAdresse": "fugiat in sint ea",
        "geozuordnungen": {
          "baublock": "s",
          "erhaltungssatzung": "cillum sunt ut et",
          "gemarkung": "aliqua sunt aute ipsum",
          "grundschule": "incididunt esse eu voluptate",
          "kaminkehrerbezirk": "nisi qui",
          "mittelschule": "est amet non",
          "parklizenzgebietId": -35659030,
          "parklizenzgebietName": "ea",
          "polizeiinspektion": "voluptate Lorem ullamco sed",
          "postleitzahl": "dolor",
          "verwaltungszuteilung": {
            "gemeinde": "amet sint voluptate",
            "landesschluessel": "amet repre",
            "kreis": "dolor ut",
            "ortsteil": "eu",
            "regierungsbezirk": "ea nulla",
            "stadtbezirk": "labore",
            "stadtbezirksteil": "nulla ea qui in",
            "stadtbezirksviertel": "aliqua",
            "gemeindeschluessel": "enim quis pariatur do"
          },
          "wahleinteilungen": {
            "stimmbezirk": -28609417,
            "stimmkreis": -88358648,
            "wahlkreis": 97007008,
            "wahlbezirk": 986440
          }
        }
      }
    },
    {
      "adresse": {
        "adressId": "tempor labore",
        "adresse": "aliqua",
        "hausnummer": 75107588,
        "ortsname": "consectetur anim",
        "position": {
          "utm": {
            "north": -41030171.90305036,
            "east": -35381680.71528238
          },
          "wgs": {
            "lat": -1213137.8927651644,
            "lon": 64896157.11713731,
            "geohash": "Ut incididunt",
            "fragment": false
          }
        },
        "strasseId": "ea ut",
        "strassenname": "non irure ut nostrud Excepteur",
        "strassennameAbgekuerzt": "aute consequat occaecat enim",
        "strassennameKurz": "ullamco eu id deserunt voluptate",
        "wirkung": {
          "status": "HISTORISCH",
          "vorgang": "UMNUMMERIERUNG",
          "wirkungsdatum": "2011-07-17"
        },
        "buchstabe": "voluptate do",
        "ehemaligeAdresse": "sit proident laboris",
        "geozuordnungen": {
          "baublock": "nulla ea fugiat et sit",
          "erhaltungssatzung": "officia velit ex",
          "gemarkung": "laborum Duis",
          "grundschule": "dolor",
          "kaminkehrerbezirk": "amet aliquip laborum",
          "mittelschule": "eu qui esse laborum",
          "parklizenzgebietId": 79219283,
          "parklizenzgebietName": "in adipisicing non",
          "polizeiinspektion": "in culpa ullamco dolore",
          "postleitzahl": "laborum in et nisi",
          "verwaltungszuteilung": {
            "gemeinde": "dolore ",
            "landesschluessel": "incididunt veniam Ut",
            "kreis": "laboris pariatur ad",
            "ortsteil": "magna",
            "regierungsbezirk": "ex Ut magna qui sint",
            "stadtbezirk": "aute",
            "stadtbezirksteil": "tempor sed laboris",
            "stadtbezirksviertel": "proident aute sunt",
            "gemeindeschluessel": "cupidatat reprehenderit nulla"
          },
          "wahleinteilungen": {
            "stimmbezirk": 26176655,
            "stimmkreis": 79997219,
            "wahlkreis": 64862394,
            "wahlbezirk": 17307295
          }
        }
      },
      "adresseVorgaenger": {
        "adressId": "occ",
        "adresse": "consequat pariatur laborum",
        "hausnummer": -1168069,
        "ortsname": "Lorem irure eiusmod laboris ullamco",
        "position": {
          "utm": {
            "north": -81977282.97920452,
            "east": 54251674.58158988
          },
          "wgs": {
            "lat": -38345793.233037904,
            "lon": 89185540.31637692,
            "geohash": "non sit",
            "fragment": true
          }
        },
        "strasseId": "mollit amet ex enim ut",
        "strassenname": "pariatur ut",
        "strassennameAbgekuerzt": "aliquip Lorem",
        "strassennameKurz": "exercitation eu cillum",
        "wirkung": {
          "status": "HISTORISCH",
          "vorgang": "NEUERTEILUNG",
          "wirkungsdatum": "1954-12-23"
        },
        "buchstabe": "ea anim velit occaecat officia",
        "ehemaligeAdresse": "eiusmod dolor anim laboru",
        "geozuordnungen": {
          "baublock": "consectetur in enim ut",
          "erhaltungssatzung": "sit minim",
          "gemarkung": "anim Lorem eu sunt pariatur",
          "grundschule": "pariatur mollit",
          "kaminkehrerbezirk": "pariatur voluptate",
          "mittelschule": "occaecat Duis",
          "parklizenzgebietId": -35001853,
          "parklizenzgebietName": "ullamco labore laboris deserunt",
          "polizeiinspektion": "ullamco dolore",
          "postleitzahl": "cillum non fugiat ",
          "verwaltungszuteilung": {
            "gemeinde": "non",
            "landesschluessel": "adipisicing consectetur commodo qui",
            "kreis": "deserunt labore Lorem magna",
            "ortsteil": "enim consectetur",
            "regierungsbezirk": "sunt laboris irure sint",
            "stadtbezirk": "incididunt qui fugiat",
            "stadtbezirksteil": "dolor officia non",
            "stadtbezirksviertel": "sint sit est commodo",
            "gemeindeschluessel": "sed eiusmod officia"
          },
          "wahleinteilungen": {
            "stimmbezirk": 80318956,
            "stimmkreis": 52457018,
            "wahlkreis": -74481438,
            "wahlbezirk": 5716885
          }
        }
      }
    }
  ]
}
```

#### searchAdressenMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "searchAdressenMuenchen",
  "query": "culpa incididunt occaecat ut",
  "plzfilter": "amet Lorem fugiat",
  "hausnummerfilter": 71564634,
  "buchstabefilter": "mollit eu",
  "searchtype": "AKTIV",
  "sort": "culpa incididunt occaecat ut",
  "sortdir": "culpa incididunt occaecat ut",
  "page": 0,
  "pagesize": 20
}
```

Die Antwort lautet wie folgt:

```json
{
  "page": {
    "size": 20,
    "number": 78213137,
    "totalElements": 95507712,
    "totalPages": 68298283,
    "numberOfElements": 24301954
  },
  "content": [
    {
      "score": -21536539.679125115,
      "adresse": {
        "adressId": "in ea sit nulla cupidatat",
        "adresse": "officia mollit",
        "hausnummer": 67224312,
        "ortsname": "incididunt Duis",
        "position": {
          "utm": {
            "north": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "east": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wgs": {
            "lat": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "lon": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "geohash": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "fragment": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        },
        "strasseId": "elit ex tempor",
        "strassenname": "nostrud id amet",
        "strassennameAbgekuerzt": "sed sit cillum",
        "strassennameKurz": "amet nostrud",
        "wirkung": {
          "status": "AKTIV",
          "vorgang": "WIEDERERTEILUNG",
          "wirkungsdatum": "1992-09-27"
        },
        "buchstabe": "nostrud pariatur sunt ut",
        "ehemaligeAdresse": "non in",
        "geozuordnungen": {
          "baublock": "id esse reprehenderit nostrud",
          "erhaltungssatzung": "deserunt ad",
          "gemarkung": "incididunt culpa reprehenderit",
          "grundschule": "officia reprehenderit ut",
          "kaminkehrerbezirk": "Duis amet",
          "mittelschule": "sunt aliquip dolore",
          "parklizenzgebietId": 83270015,
          "parklizenzgebietName": "ut labore commodo",
          "polizeiinspektion": "laborum aute",
          "postleitzahl": "Duis aute sint dol",
          "verwaltungszuteilung": {
            "gemeinde": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "landesschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "kreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "ortsteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "regierungsbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksviertel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "gemeindeschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wahleinteilungen": {
            "stimmbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stimmkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        }
      }
    },
    {
      "score": 94422007.0175604,
      "adresse": {
        "adressId": "qui aliquip commodo",
        "adresse": "eu",
        "hausnummer": 379016,
        "ortsname": "velit ullamco laboris",
        "position": {
          "utm": {
            "north": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "east": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wgs": {
            "lat": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "lon": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "geohash": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "fragment": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        },
        "strasseId": "elit ut in",
        "strassenname": "id d",
        "strassennameAbgekuerzt": "aliq",
        "strassennameKurz": "irure ullamco tempor",
        "wirkung": {
          "status": "AKTIV",
          "vorgang": "WIEDERERTEILUNG",
          "wirkungsdatum": "1999-02-22"
        },
        "buchstabe": "dolor elit",
        "ehemaligeAdresse": "esse laboris",
        "geozuordnungen": {
          "baublock": "tempo",
          "erhaltungssatzung": "esse amet anim ut",
          "gemarkung": "anim nisi",
          "grundschule": "nulla eiusmod",
          "kaminkehrerbezirk": "eu eiusmod",
          "mittelschule": "Ut sint Duis proident quis",
          "parklizenzgebietId": 63351358,
          "parklizenzgebietName": "sit Lorem",
          "polizeiinspektion": "enim ex culpa velit adipisicing",
          "postleitzahl": "voluptate ipsum est",
          "verwaltungszuteilung": {
            "gemeinde": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "landesschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "kreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "ortsteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "regierungsbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksteil": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stadtbezirksviertel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "gemeindeschluessel": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          },
          "wahleinteilungen": {
            "stimmbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "stimmkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlkreis": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            },
            "wahlbezirk": {
              "value": "<Error: Too many levels of nesting to fake this schema>"
            }
          }
        }
      }
    }
  ]
}
```

##### searchAdressenGeoMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "searchAdressenGeoMuenchen",
  "geometrie": "culpa incididunt occaecat ut",
  "lat": 81856032.3170662,
  "lng": 81856032.3170662,
  "distanz": 81856032.3170662,
  "topleftlat": 81856032.3170662,
  "topleftlng": 81856032.3170662,
  "bottomrightlat": 81856032.3170662,
  "bottomrightlng": 81856032.3170662,
  "format": "culpa incididunt occaecat ut"
}
```

Die Antwort lautet wie folgt:

```json
{
  "adresseDistances": [
    {
      "adresse": {
        "adressId": "minim exercitation",
        "adresse": "sed laboris veniam proident",
        "hausnummer": 17712660,
        "ortsname": "velit laboris",
        "position": {
          "utm": {
            "north": 5161499.421731815,
            "east": -2168418.07622163
          },
          "wgs": {
            "lat": -47561326.93011712,
            "lon": -73615277.2662409,
            "geohash": "velit ad et",
            "fragment": true
          }
        },
        "strasseId": "incididunt officia sed",
        "strassenname": "officia",
        "strassennameAbgekuerzt": "ut laborum aut",
        "strassennameKurz": "ipsum eu",
        "wirkung": {
          "status": "AKTIV",
          "vorgang": "UMNUMMERIERUNG",
          "wirkungsdatum": "1956-10-06"
        },
        "buchstabe": "enim cillum laborum pariatur",
        "ehemaligeAdresse": "quis",
        "geozuordnungen": {
          "baublock": "sint sit",
          "erhaltungssatzung": "Duis Lorem",
          "gemarkung": "sed minim veniam occaecat",
          "grundschule": "sint occaecat sunt non laboris",
          "kaminkehrerbezirk": "ut nostrud",
          "mittelschule": "sunt deserunt",
          "parklizenzgebietId": -72706230,
          "parklizenzgebietName": "labore Ut tempor ut",
          "polizeiinspektion": "eu veniam cupidatat mollit",
          "postleitzahl": "ad proident laborum",
          "verwaltungszuteilung": {
            "gemeinde": "pariatur cons",
            "landesschluessel": "nostrud occaecat amet ",
            "kreis": "quis ut laboris",
            "ortsteil": "id sint pariatur",
            "regierungsbezirk": "veniam id ut quis",
            "stadtbezirk": "irure Duis Excepteur dolor",
            "stadtbezirksteil": "non",
            "stadtbezirksviertel": "Duis magna Ut",
            "gemeindeschluessel": "adipisicing non enim"
          },
          "wahleinteilungen": {
            "stimmbezirk": 34084370,
            "stimmkreis": 78982700,
            "wahlkreis": 23936825,
            "wahlbezirk": -43056325
          }
        }
      },
      "distanz": -51118197.34915293
    },
    {
      "adresse": {
        "adressId": "vo",
        "adresse": "laborum ad do",
        "hausnummer": 44372535,
        "ortsname": "nisi Duis laboris",
        "position": {
          "utm": {
            "north": 47204252.96079421,
            "east": -46831621.22245297
          },
          "wgs": {
            "lat": -4107070.8893123716,
            "lon": 84062168.22143531,
            "geohash": "non cillum reprehenderit in ea",
            "fragment": false
          }
        },
        "strasseId": "nostrud incididunt aute et",
        "strassenname": "do sed",
        "strassennameAbgekuerzt": "qui elit nulla sint",
        "strassennameKurz": "sed pariatur",
        "wirkung": {
          "status": "AKTIV",
          "vorgang": "NEUERTEILUNG",
          "wirkungsdatum": "1955-08-05"
        },
        "buchstabe": "commodo amet in laborum",
        "ehemaligeAdresse": "sed dolore",
        "geozuordnungen": {
          "baublock": "sed",
          "erhaltungssatzung": "non adipisicing",
          "gemarkung": "sint",
          "grundschule": "aute Excepteur exercitation",
          "kaminkehrerbezirk": "irure esse ullamco ut",
          "mittelschule": "fugiat",
          "parklizenzgebietId": -52672708,
          "parklizenzgebietName": "veniam enim et aute magna",
          "polizeiinspektion": "proident minim dolor aliquip",
          "postleitzahl": "in Lorem consectetur laboris o",
          "verwaltungszuteilung": {
            "gemeinde": "dolore",
            "landesschluessel": "voluptate elit",
            "kreis": "laboris ullamco",
            "ortsteil": "culpa irure est nost",
            "regierungsbezirk": "dolore officia in cupidatat",
            "stadtbezirk": "dolor sunt",
            "stadtbezirksteil": "ut eiusmod",
            "stadtbezirksviertel": "proident culpa",
            "gemeindeschluessel": "enim irure"
          },
          "wahleinteilungen": {
            "stimmbezirk": 85912569,
            "stimmkreis": 91004474,
            "wahlkreis": -68025382,
            "wahlbezirk": -61788842
          }
        }
      },
      "distanz": -17973894.71322362
    }
  ]
}
```

### Straßen München

#### findStrasseByIdMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "findStrasseByIdMuenchen",
  "strasseId": 1
}
```

Die Antwort lautet wie folgt:

```json
{
  "strasseId": 1,
  "strassenname": "non ut",
  "strassennameAbgekuerzt": "sed Ut fugiat",
  "strassennameKurz": "ea exercitation",
  "geozuordnungen": {
    "verwaltungszuteilung": {
      "stadtbezirke": [
        {
          "name": "elit minim non sit officia",
          "nummer": -20411444
        },
        {
          "name": "dolor eiusmod minim nulla",
          "nummer": -20766720
        }
      ]
    }
  }
}
```

#### listStrassenMuenchen

Das folgende JSON-Objekt zeigt das Beispiel-Datenpaket, das im Anfragefeld des Element-Teamplates gesetzt ist:

```json
{
  "eventType": "listStrassenMuenchen",
  "stadtbezirksnamen": "eiusmod eu",
  "stadtbezirksnummern": -78812834,
  "strassenname": "culpa incididunt occaecat ut",
  "sortdir": "culpa incididunt occaecat ut",
  "page": 0,
  "pagesize": 20
}
```

Die Antwort lautet wie folgt:

```json
{
  "page": {
    "size": 20,
    "number": -75626395,
    "totalElements": 92409383,
    "totalPages": -63195915,
    "numberOfElements": -79868306
  },
  "content": [
    {
      "score": 35150532.776935995,
      "strasse": {
        "strasseId": 26477179,
        "strassenname": "ea",
        "strassennameAbgekuerzt": "ex voluptate adipisicing",
        "strassennameKurz": "amet irure ullamco",
        "geozuordnungen": {
          "verwaltungszuteilung": {
            "stadtbezirke": [
              {
                "name": "ea magna dolore consectetur",
                "nummer": -36233911
              },
              {
                "name": "est fugiat qui",
                "nummer": -81100275
              }
            ]
          }
        }
      }
    },
    {
      "score": 23941144.519478336,
      "strasse": {
        "strasseId": 4150312,
        "strassenname": "qui",
        "strassennameAbgekuerzt": "esse",
        "strassennameKurz": "quis mol",
        "geozuordnungen": {
          "verwaltungszuteilung": {
            "stadtbezirke": [
              {
                "name": "nostrud in",
                "nummer": -11217522
              },
              {
                "name": "aute Duis incididunt est nulla",
                "nummer": 55593962
              }
            ]
          }
        }
      }
    }
  ]
}
```

### Fehlerbehandlung

| Error Code         | Error Message                                                            | Beschreibung                                                                            | Handlungsempfehlung                                                             | 
|--------------------|--------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `ADDRESS_SERVICE_CLIENT_ERROR` | Die Fehlermeldung des Stadt München Address Services wird weitergegeben. | Beim Aufrufen des Address Services ist ein Client Fehler aufgetreten (HTTP Status 4xx). | Analysieren sie die Fehlermeldung und versuchen den Request nochmals zu senden. | 

## DigiWF Address Integration anpassen

Die Integration wurde in einer hexagonalen Architektur implementiert, um Anpassbarkeit und Erweiterbarkeit zu gewährleisten.
Um die Funktionen der Integration zu erweitern bzw. zu ersetzen, müssen lediglich die Port Interfaces überschrieben und als `@bean`
bereitgestellt werden. Dadurch wird unsere Standard-Implementierung durch die eigene Implementierung ersetzt.

Die Port Definitionen finden Sie unter dem Pfad: [digiwf-address-integration-core/src/main/java/de/muenchen/oss/digiwf/address/integration/application/port](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-address-integration/digiwf-address-integration-core/src/main/java/de/muenchen/oss/digiwf/address/integration/application/port)

### Address-Client anpassen

Der Address-Client ist für die Kommunikation mit dem Address-Service der Stadt München zuständig.
Der Client wurde in einem eigenen Maven Modul als Bibliothek implementiert, um die Abhängigkeiten zu kapseln.
Die Implementierung des Clients kann ebenfalls erweitert bzw. zu ersetzt werden durch die Implementierung der API Interfaces `AddressGermanyApi`, `AddressMunichApi` und `StreetsMunichApi`.
Die Implementierung dieser Interfaces muss ebenfalls als `@bean` bereitgestellt werden.

Die API Interfaces finden Sie unter dem Pfad: [digiwf-address-integration-client/src/main/java/de/muenchen/oss/digiwf/address/integration/client/api](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-address-integration/digiwf-address-integration-client/src/main/java/de/muenchen/oss/digiwf/address/integration/client/api)

## Konfigurationen

Zusätzlich zu den allgemeinen Konfigurationen für DigiWF Integrationen, die unter
[Eigene Integration erstellen](/integrations/guides/custom-integration-service.html#anwendung-konfigurieren) beschrieben
sind, können Sie die folgenden Konfigurationen für die DigiWF Address Integration verwenden:

| Eigenschaft                                  | Bedeutung                |
|----------------------------------------------|--------------------------|
| `de.muenchen.oss.digiwf.address.service.url` | URL des Address Services |
