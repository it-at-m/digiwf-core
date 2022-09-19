<template>
  <v-app>
    <menu></menu>
    <v-tabs>
      <v-tab>
        builder
      </v-tab>
      <v-tab>
        renderer
      </v-tab>
      <v-tab-item>
        <dwf-form-builder :value="schema" @input="changed" :builderSettings="settings"></dwf-form-builder>
      </v-tab-item>
      <v-tab-item>
        <div style="padding: 30px">
          <v-form ref="form">
            <dwf-form-renderer :options="{}" :value="value" :schema="schema"></dwf-form-renderer>
          </v-form>
          <v-btn @click="validate">Validate</v-btn>
        </div>
      </v-tab-item>
    </v-tabs>
  </v-app>
</template>

<style>
html, body {
  height: 100%;
}
</style>

<script lang="ts">
import {DwfFormRenderer} from "@muenchen/digiwf-form-renderer";
import {DwfFormBuilder} from "@muenchen/digiwf-form-builder";
import {SettingsEN} from "@muenchen/digiwf-form-builder-settings";
import {defineComponent, provide, ref} from "vue";

export default defineComponent({
  components: {DwfFormRenderer, DwfFormBuilder},
  setup() {
    const form = ref(null);

    const value = ref({});

    const schema = ref({
      "type": "object", "x-display": "tabs", "allOf": [{
        "key": "sectionKey1",
        "title": "Allgemeine Angaben",
        "type": "object",
        "x-options": {"sectionsTitlesClasses": []},
        "allOf": [{
          "containerType": "group",
          "title": " ",
          "key": "b4750075-1ae2-468a-a54b-0fea4795aa02",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "Adressangaben": {
              "key": "Adressangaben",
              "fieldType": "object",
              "title": "Adressangaben",
              "type": "object",
              "additionalProperties": false,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "properties": {
                "FormField_Strasse": {
                  "fieldType": "text",
                  "title": "Straße",
                  "type": "string",
                  "key": "FormField_Strasse",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Hausnummer": {
                  "fieldType": "text",
                  "title": "Hausnummer",
                  "type": "string",
                  "key": "FormField_Hausnummer",
                  "x-options": {"fieldColProps": {"cols": 6, "sm": 3, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Hausnummernzusatz": {
                  "fieldType": "text",
                  "title": "Hausnummernzusatz",
                  "type": "string",
                  "key": "FormField_Hausnummernzusatz",
                  "x-options": {"fieldColProps": {"cols": 6, "sm": 3, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_Postleitzahl": {
                  "fieldType": "integer",
                  "type": "integer",
                  "key": "FormField_Postleitzahl",
                  "title": "Postleitzahl",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Ort": {
                  "fieldType": "text",
                  "title": "Ort",
                  "type": "string",
                  "key": "FormField_Ort",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 8, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                }
              }
            }, "Liegenschaft": {
              "key": "Liegenschaft",
              "fieldType": "object",
              "title": "Liegenschaft",
              "type": "object",
              "additionalProperties": false,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "properties": {
                "FormField_Stadtbezirk": {
                  "fieldType": "select",
                  "title": "Stadtbezirk (Lage)",
                  "type": "string",
                  "key": "FormField_Stadtbezirk",
                  "anyOf": [{
                    "title": "01 Altstadt-Lehel",
                    "const": "Value_01_AltstadtLehel"
                  }, {
                    "title": "02 Ludwigsvorstadt-Isarvorstadt",
                    "const": "Value_02_LudwigsvorstadtIsarvorstadt"
                  }, {"title": "03 Maxvorstadt", "const": "Value_03_Maxvorstadt"}, {
                    "title": "04 Schwabing-West",
                    "const": "Value_04_SchwabingWest"
                  }, {"title": "05 Au-Haidhausen", "const": "Value_05_AuHaidhausen"}, {
                    "title": "06 Sendling",
                    "const": "Value_06_Sendling"
                  }, {
                    "title": "07 Sendling-Westpark",
                    "const": "Value_07_SendlingWestpark"
                  }, {
                    "title": "08 Schwanthaler Höhe",
                    "const": "Value_08_SchwanthalerHoehe"
                  }, {
                    "title": "09 Neuhausen-Nymphenburg",
                    "const": "Value_09_NeuhausenNymphenburg"
                  }, {"title": "10 Moosach", "const": "Value_10_Moosach"}, {
                    "title": "11 Milbertshofen-Am Hart",
                    "const": "Value_11_MilbertshofenAmHart"
                  }, {
                    "title": "12 Schwabing-Freimann",
                    "const": "Value_12_SchwabingFreimann"
                  }, {"title": "13 Bogenhausen", "const": "Value_13_Bogenhausen"}, {
                    "title": "14 Berg am Laim",
                    "const": "Value_14_BergAmLaim"
                  }, {
                    "title": "15 Trudering-Riem",
                    "const": "Value_15_TruderingRiem"
                  }, {
                    "title": "16 Ramersdorf-Perlach",
                    "const": "Value_16_RamersdorfPerlach"
                  }, {
                    "title": "17 Obergiesing-Fasangarten",
                    "const": "Value_17_ObergiesingFasangarten"
                  }, {
                    "title": "18 Untergiesing-Harlaching",
                    "const": "Value_18_UntergiesingHarlaching"
                  }, {
                    "title": "19 Thalkirchen-Obersendling-Forst",
                    "const": "Value_19_ThalkirschenObersendlingForst"
                  }, {"title": "20 Hadern", "const": "Value_20_Hadern"}, {
                    "title": "21 Pasing-Obermenzing",
                    "const": "Value_21_PasingObermenzing"
                  }, {
                    "title": "22 Aubing-Lochhausen-Langwied",
                    "const": "Value_22_AubingLochhausenLangwied"
                  }, {
                    "title": "23 Allach-Untermenzing",
                    "const": "Value_23_AllachUntermenzing"
                  }, {
                    "title": "24 Feldmoching-Hasenbergerl",
                    "const": "Value_24_FeldmochingHasenbergerl"
                  }, {"title": "25 Laim", "const": "Value_25_Laim"}, {
                    "title": "99 außerhalb Münchens",
                    "const": "Value_99_AusserhalbMuenchens"
                  }],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Eigentumsverhaeltnis": {
                  "fieldType": "select",
                  "title": "Eigentumsverhältnis",
                  "type": "string",
                  "key": "FormField_Eigentumsverhaeltnis",
                  "anyOf": [{
                    "title": "10 stadteigen",
                    "const": "Value_10_stadteigen"
                  }, {
                    "title": "20 nicht im Eigentum der Stadt",
                    "const": "Value_20_nichtImEigentumDerStadt"
                  }, {"title": "30 Teileigentum", "const": "Value_30_Teileigentum"}, {
                    "title": "40 unbekannt",
                    "const": "Value_40_unbekannt"
                  }],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Liegenschaftsart": {
                  "fieldType": "select",
                  "title": "Liegenschaftsart",
                  "type": "string",
                  "key": "FormField_Liegenschaftsart",
                  "anyOf": [{"title": "1000 Verwaltung", "const": "Value_1000_Verwaltung"}, {
                    "title": "3000 Gesundheit",
                    "const": "Value_3000_Gesundheit"
                  }, {"title": "4000 Bildung", "const": "Value_4000_Bildung"}, {
                    "title": "4500 Kultur",
                    "const": "Value_4500_Kultur"
                  }, {
                    "title": "5000 Sport",
                    "const": "Value_5000_Sport"
                  }, {
                    "title": "6000 Wohnen, Beherbergen, Betreuen, Verpflegen",
                    "const": "Value_6000_WohnenBeherbergenBetreuenVerpflegen"
                  }, {
                    "title": "7000 Lagerung, Wartung, Verkauf",
                    "const": "Value_7000_LagerungWartungVerkauf"
                  }, {"title": "8000 Technik", "const": "Value_8000_Technik"}, {
                    "title": "9000 Sonstiges",
                    "const": "Value_9000_Sonstiges"
                  }, {"title": "9500 unbebaute Grundstücke", "const": "Value_9500_unbebauteGrundstuecke"}],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_BezeichnungLiegenschaft": {
                  "fieldType": "text",
                  "title": "Bezeichnung der Liegenschaft",
                  "type": "string",
                  "key": "FormField_BezeichnungLiegenschaft",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": [],
                  "maxLength": 40
                },
                "FormField_GueltigkeitszeitraumAb": {
                  "fieldType": "date",
                  "title": "Gültigkeitszeitraum ab",
                  "type": "string",
                  "format": "date",
                  "key": "FormField_GueltigkeitszeitraumAb",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_GueltigkeitszeitraumBis": {
                  "fieldType": "date",
                  "title": "Gültigkeitszeitraum bis",
                  "type": "string",
                  "format": "date",
                  "key": "FormField_GueltigkeitszeitraumBis",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_Zusatztext": {
                  "fieldType": "textarea",
                  "title": "Zusatztext",
                  "x-display": "textarea",
                  "type": "string",
                  "key": "FormField_Zusatztext",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": [],
                  "maxLength": 2000
                }
              }
            }, "Wirtschaftseinheit": {
              "key": "Wirtschaftseinheit",
              "fieldType": "object",
              "title": "Wirtschaftseinheit",
              "type": "object",
              "additionalProperties": false,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "properties": {
                "6ca320f8-9a7d-41cc-b987-3fa797f90916": {
                  "key": "6ca320f8-9a7d-41cc-b987-3fa797f90916",
                  "fieldType": "optionalContainer",
                  "title": "Buchungskreis",
                  "type": "object",
                  "x-options": {"sectionsTitlesClasses": []},
                  "x-props": {"outlined": true, "dense": true},
                  "oneOf": [{
                    "title": "0099 Allg Finanz-&Personalwirt",
                    "key": "71c36a8a-7975-4fbd-a7c6-ccec91bebafb",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "Buchungskreis": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "0099",
                        "key": "Buchungskreis"
                      },
                      "FormField_BuchungskreiseUnterabschnitt_0099": {
                        "fieldType": "select",
                        "title": "Unterabschnitt",
                        "type": "string",
                        "key": "FormField_BuchungskreiseUnterabschnitt_0099",
                        "anyOf": [{
                          "title": "0221 Beteiligung an Personalausgaben",
                          "const": "Value_0221"
                        }, {
                          "title": "0400 Umlage \"Zentrale Steuerungsfunktionen\"",
                          "const": "Value_0400"
                        }, {"title": "0800 Zentrale Personalbetreuung", "const": "Value_0800"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      }
                    }
                  }, {
                    "title": "0100 Direktorium",
                    "key": "8ecdd4cb-b51b-4fa8-a9f9-d411dd132c26",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "Buchungskreis": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "0100",
                        "key": "Buchungskreis"
                      },
                      "FormField_BuchungskreiseUnterabschnitt_0100": {
                        "fieldType": "select",
                        "title": "Unterabschnitt",
                        "type": "string",
                        "key": "FormField_BuchungskreiseUnterabschnitt_0100",
                        "anyOf": [{
                          "const": "Value_0000",
                          "title": "0000 Stadtvertretung"
                        }, {
                          "title": "0010 Stadtvertretung - Stadtrat",
                          "const": "Value_0010"
                        }, {"title": "0020 Stadtvertretung - Bezirksausschüsse", "const": "Value_0020"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      }
                    }
                  }]
                },
                "FormField_ProfitCenter": {
                  "fieldType": "text",
                  "title": "Profit-Center",
                  "type": "string",
                  "key": "FormField_ProfitCenter",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Optionssatzmethode": {
                  "fieldType": "select",
                  "title": "Optionssatzmethode",
                  "type": "string",
                  "key": "FormField_Optionssatzmethode",
                  "anyOf": [{
                    "title": "Flächenverhältnis (automatisch)",
                    "const": "Value_Flaechenverhaeltnis"
                  }, {
                    "title": "Optiert nicht (0%)",
                    "const": "Value_OptiertNicht"
                  }, {
                    "title": "Sonstiges Verfahren (manuell)",
                    "const": "Value_SonstigesVerfahren"
                  }, {"title": "Optiert (100%)", "const": "Value_Optiert"}],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "undefined": {
                  "key": "undefined",
                  "fieldType": "optionalContainer",
                  "title": "Angabe der Versicherungspflicht",
                  "type": "object",
                  "x-options": {"sectionsTitlesClasses": []},
                  "x-props": {"outlined": true, "dense": true},
                  "oneOf": [{
                    "title": "Versicherungspflicht angeben - Kommunalreferat",
                    "key": "4f581cc3-f35b-4b46-a8c9-5dd6cd41c65f",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "OV_Referat": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "KR",
                        "key": "OV_Referat"
                      },
                      "FormField_Versicherungspflicht": {
                        "fieldType": "select",
                        "title": "Versicherungspflicht",
                        "type": "string",
                        "key": "FormField_Versicherungspflicht",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      }
                    }
                  }, {
                    "title": "Versicherungspflicht nicht angeben - anderes Referat",
                    "key": "8eb501a0-6a86-4c36-9713-143bdc8f15f3",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "OV_Referat": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "Anderes",
                        "key": "OV_Referat"
                      }
                    }
                  }]
                }
              }
            }
          }
        }]
      }, {
        "title": "Gebäude",
        "type": "object",
        "key": "91d7e4f0-518d-4bfa-a662-279e90fec9a1",
        "x-options": {"sectionsTitlesClasses": []},
        "allOf": [{
          "containerType": "group",
          "title": " ",
          "key": "b9dcbcbc-b75e-49dd-9720-1000aa1f01a6",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "GebaeudeListe": {
              "key": "GebaeudeListe",
              "fieldType": "arrayObject",
              "title": "Gebäude",
              "type": "array",
              "items": {
                "type": "object", "properties": {
                  "AllgemeineAngaben": {
                    "key": "AllgemeineAngaben",
                    "fieldType": "object",
                    "title": "Allgemeine Angaben",
                    "type": "object",
                    "additionalProperties": false,
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "properties": {
                      "FormField_Gebaeude_StrasseHNr": {
                        "fieldType": "text",
                        "title": "Straße, Hausnummer, Hausnummernzusatz",
                        "type": "string",
                        "key": "FormField_Gebaeude_StrasseHNr",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Gebaeude_WeitereAdresse": {
                        "fieldType": "text",
                        "title": "Weitere Adresse",
                        "type": "string",
                        "key": "FormField_Gebaeude_WeitereAdresse",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_Gebaeude_Postleitzahl": {
                        "fieldType": "text",
                        "title": "Postleitzahl",
                        "type": "string",
                        "key": "FormField_Gebaeude_Postleitzahl",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 2, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Gebaeude_Ort": {
                        "fieldType": "text",
                        "title": "Ort",
                        "type": "string",
                        "key": "FormField_Gebaeude_Ort",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Gebaeude_Lage": {
                        "fieldType": "select",
                        "title": "Lage (innerhalb / außerhalb Altstadt)",
                        "type": "string",
                        "key": "FormField_Gebaeude_Lage",
                        "anyOf": [{
                          "title": "innerhalb des Altstadtrings",
                          "const": "Value_Innerhalb"
                        }, {"title": "außerhalb des Altstadtrings", "const": "Value_Ausserhalb"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Gebaeude_Eigentumsverhaeltnis": {
                        "fieldType": "select",
                        "title": "Eigentumsverhältnis",
                        "type": "string",
                        "key": "FormField_Gebaeude_Eigentumsverhaeltnis",
                        "anyOf": [{
                          "title": "10 stadteigen",
                          "const": "Value_10"
                        }, {
                          "title": "20 nicht im Eigentum der Stadt",
                          "const": "Value_20"
                        }, {"title": "30 Teileigentum", "const": "Value_30"}, {
                          "title": "40 unbekannt",
                          "const": "Value_40"
                        }],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Gebaeude_Gebaeudeart": {
                        "fieldType": "select",
                        "title": "Gebäudeart",
                        "type": "string",
                        "key": "FormField_Gebaeude_Gebaeudeart",
                        "anyOf": [{
                          "title": "1300 Verwaltungsgebäude",
                          "const": "Value_1300"
                        }, {"title": "3200 Krankenhäuser", "const": "Value_3200"}, {
                          "title": "3600 Pflegeheime",
                          "const": "Value_3600"
                        }, {"title": "4100 Schulgebäude", "const": "Value_4100"}, {
                          "title": "4310 Fortbildungsstätten",
                          "const": "Value_4310"
                        }],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "undefined": {
                        "key": "undefined",
                        "fieldType": "optionalContainer",
                        "title": "Baujahr bekannt",
                        "type": "object",
                        "x-options": {"sectionsTitlesClasses": []},
                        "x-props": {"outlined": true, "dense": true},
                        "oneOf": [{
                          "title": "Ja",
                          "key": "dc6a78a3-2791-4347-a939-97df72e92a81",
                          "x-options": {"childrenClass": "pl-0"},
                          "properties": {
                            "FormField_Gebaeude_BaujahrBekannt": {
                              "fieldType": "const",
                              "type": "string",
                              "const": "Value_Ja",
                              "key": "FormField_Gebaeude_BaujahrBekannt"
                            },
                            "FormField_Gebaeude_Baujahr": {
                              "fieldType": "integer",
                              "title": "Baujahr",
                              "type": "integer",
                              "key": "FormField_Gebaeude_Baujahr",
                              "x-options": {"fieldColProps": {"cols": 12, "sm": 2, "messages": {}}},
                              "x-props": {"outlined": true, "dense": true},
                              "x-rules": ["required"]
                            }
                          }
                        }, {
                          "title": "Nein",
                          "key": "9132dd91-d175-453b-9b55-7511adb05db6",
                          "x-options": {"childrenClass": "pl-0"},
                          "properties": {
                            "FormField_Gebaeude_BaujahrBekannt": {
                              "fieldType": "const",
                              "type": "string",
                              "const": "Value_Nein",
                              "key": "FormField_Gebaeude_BaujahrBekannt"
                            }
                          }
                        }]
                      },
                      "FormField_Gebaeude_Denkmalschutz": {
                        "fieldType": "select",
                        "title": "Denkmalschutz",
                        "type": "string",
                        "key": "FormField_Gebaeude_Denkmalschutz",
                        "anyOf": [{"title": "10 unbekannt", "const": "Value_10"}, {
                          "title": "20 nein",
                          "const": "Value_20"
                        }, {"title": "30 Gebäude", "const": "Value_30"}, {"title": "40 Ensemble", "const": "Value_40"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Gebaeude_Bezeichnung": {
                        "fieldType": "text",
                        "title": "Bezeichnung",
                        "type": "string",
                        "key": "FormField_Gebaeude_Bezeichnung",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": [],
                        "maxLength": 40
                      }
                    }
                  }, "Geschaeftspartner1": {
                    "key": "Geschaeftspartner1",
                    "fieldType": "object",
                    "title": "Geschäftspartner 1",
                    "type": "object",
                    "additionalProperties": false,
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "properties": {
                      "FormField_SachgebietsleiterObjektverantwortung": {
                        "fieldType": "user-input",
                        "title": "Sachgebietsleiter*in Objektverantwortung",
                        "x-display": "custom-user-input",
                        "type": "string",
                        "key": "FormField_SachgebietsleiterObjektverantwortung",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_Objektverantwortlicher": {
                        "fieldType": "user-input",
                        "x-display": "custom-user-input",
                        "type": "string",
                        "key": "FormField_Objektverantwortlicher",
                        "title": "Objektverantwortliche*r",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_ObjektverantwortlicheOrganisationseinheit": {
                        "fieldType": "select",
                        "title": "Objektverantwortliche Organisationseinheit",
                        "type": "string",
                        "key": "FormField_ObjektverantwortlicheOrganisationseinheit",
                        "anyOf": [{"title": "RBS-ZIM-N-1-N", "const": "Value_RBS_ZIM_N_1_N"}, {
                          "title": "RBS-ZIM-N-1-O",
                          "const": "Value_RBS_ZIM_N_1_O"
                        }, {"title": "RBS-ZIM-N-1-S", "const": "Value_RBS_ZIM_N_1_S"}, {
                          "title": "RBS-ZIM-N-2-W",
                          "const": "Value_RBS_ZIM_N_2_W"
                        }],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_TechnischeHausverwaltung": {
                        "fieldType": "select",
                        "title": "Technische Hausverwaltung",
                        "type": "string",
                        "key": "FormField_TechnischeHausverwaltung",
                        "anyOf": [{
                          "title": "KR-ID-THV-HR",
                          "const": "Value_KR_ID_THV_HR"
                        }, {"const": "Value_KR_ID_THV_HH", "title": "KR-ID-THV-HH"}, {
                          "const": "Value_KR_ID_THV_HH_W",
                          "title": "KR-ID-THV-HH-W"
                        }, {"const": "Value_KR_ID_TH_AW", "title": "KR-ID-TH-AW"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_TechnischerDienstleisterArchitektur": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Architektur",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterArchitektur",
                        "anyOf": [{"title": "H 11", "const": "Value_H11"}, {
                          "title": "H 12",
                          "const": "Value_H12"
                        }, {"title": "H 13", "const": "Value_H13"}, {
                          "title": "H14",
                          "const": "Value_H14"
                        }, {"title": "H 21", "const": "Value_H21"}, {"title": "H 22", "const": "Value_H22"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_TechnischerDienstleisterElektrotechnik": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Elektrotechnik",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterElektrotechnik",
                        "anyOf": [{"title": "H 81", "const": "Value_H81"}, {
                          "title": "H 82",
                          "const": "Value_H82"
                        }, {"title": "H 83", "const": "Value_H83"}, {
                          "title": "H 84",
                          "const": "Value_H84"
                        }, {"title": "H 85", "const": "Value_H85"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_TechnischerDienstleisterVersorgungstechnik": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Versorgungstechnik",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterVersorgungstechnik",
                        "anyOf": [{"title": "H 71", "const": "Value_H71"}, {
                          "title": "H 72",
                          "const": "Value_H72"
                        }, {"title": "H 73", "const": "Value_H73"}, {
                          "title": "H 74",
                          "const": "Value_H74"
                        }, {"title": "H 75", "const": "Value_H75"}, {
                          "title": "H 76",
                          "const": "Value_H76"
                        }, {"title": "KR-IM-TKN", "const": "Value_KR_IM_TKN"}, {
                          "title": "KR-IM-TKO",
                          "const": "Value_KR_IM_TKO"
                        }],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_TechnischerDienstleisterBetrieb": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Betrieb",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterBetrieb",
                        "anyOf": [{"title": "Servicedesk H9", "const": "Value_Servicedesk_H9"}, {
                          "title": "Vermieter",
                          "const": "Value_Vermieter"
                        }, {"title": "Externer Dienstleister", "const": "Value_ExternerDienstleister"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      },
                      "FormField_TechnischerDienstleisterBetriebTelefonnummer": {
                        "fieldType": "text",
                        "title": "Technischer Dienstleister Betrieb Telefonnummer",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterBetriebTelefonnummer",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      }
                    }
                  }, "Geschaeftspartner2": {
                    "key": "Geschaeftspartner2",
                    "fieldType": "object",
                    "title": "Geschäftspartner 2",
                    "type": "object",
                    "additionalProperties": false,
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "properties": {
                      "FormField_TechnischeHausverwaltung": {
                        "fieldType": "select",
                        "title": "Technische Hausverwaltung",
                        "type": "string",
                        "key": "FormField_TechnischeHausverwaltung",
                        "anyOf": [{
                          "title": "KR-ID-THV-HR",
                          "const": "Value_KR_ID_THV_HR"
                        }, {"const": "Value_KR_ID_THV_HH", "title": "KR-ID-THV-HH"}, {
                          "const": "Value_KR_ID_THV_HH_W",
                          "title": "KR-ID-THV-HH-W"
                        }, {"const": "Value_KR_ID_THV_AQ", "title": "KR-ID-THV-AW"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_TechnischerDienstleisterArchitektur": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Architektur",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterArchitektur",
                        "anyOf": [{"title": "H 11", "const": "Value_H11"}, {
                          "title": "H 12",
                          "const": "Value_H12"
                        }, {"title": "H 13", "const": "Value_H13"}, {
                          "title": "H 14",
                          "const": "Value_H14"
                        }, {"title": "H 21", "const": "Value_H21"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_TechnischerDienstleisterElektrotechnik": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Elektrotechnik",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterElektrotechnik",
                        "anyOf": [{"title": "H 81", "const": "Value_H81"}, {
                          "title": "H 82",
                          "const": "Value_H82"
                        }, {"title": "H 83", "const": "Value_H83"}, {
                          "title": "H 84",
                          "const": "Value_H84"
                        }, {"title": "H 85", "const": "Value_H85"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_TechnischerDienstleisterVersorgungstechnik": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Versorgungstechnik",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterVersorgungstechnik",
                        "anyOf": [{"title": "H 71", "const": "Value_H71"}, {
                          "title": "H 72",
                          "const": "Value_H72"
                        }, {"title": "H 73", "const": "Value_H73"}, {
                          "title": "H 74",
                          "const": "Value_H74"
                        }, {"title": "H 75", "const": "Value_H75"}, {"title": "H 76", "const": "Value_H76"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_TechnischerDienstleisterBetrieb": {
                        "fieldType": "select",
                        "title": "Technischer Dienstleister Betrieb",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterBetrieb",
                        "anyOf": [{"title": "Servicedesk H9", "const": "Value_Servicedesk_H9"}, {
                          "title": "Vermieter",
                          "const": "Value_Vermieter"
                        }, {"title": "Externer Dienstleister", "const": "Value_ExternerDienstleister"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_TechnischerDienstleisterBetriebTelefonnummer": {
                        "fieldType": "text",
                        "title": "Technischer Dienstleister Betrieb Telefonnummer",
                        "type": "string",
                        "key": "FormField_TechnischerDienstleisterBetriebTelefonnummer",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      }
                    }
                  }, "Flaechenangaben": {
                    "key": "Flaechenangaben",
                    "fieldType": "object",
                    "title": "Flächenangaben",
                    "type": "object",
                    "additionalProperties": false,
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "properties": {
                      "FormField_InitialeFlaecheR1BGF": {
                        "fieldType": "integer",
                        "title": "Initiale Fläche R1 BGF (m²)",
                        "type": "integer",
                        "key": "FormField_InitialeFlaecheR1BGF",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_InitialeFlaecheR1NRF": {
                        "fieldType": "integer",
                        "title": "Initiale Fläche R1 NRF (m²)",
                        "type": "integer",
                        "key": "FormField_InitialeFlaecheR1NRF",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_InitialeFlaecheR1NUF": {
                        "fieldType": "integer",
                        "title": "Initiale Fläche R1 NUF (m²)",
                        "type": "integer",
                        "key": "FormField_InitialeFlaecheR1NUF",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_InitialeFlaecheR1Mietflaeche": {
                        "fieldType": "integer",
                        "title": "Initiale Fläche R1 Mietfläche (m²)",
                        "type": "integer",
                        "key": "FormField_InitialeFlaecheR1Mietflaeche",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_NettoRaumflaecheBeheizt": {
                        "fieldType": "integer",
                        "title": "Netto-Raumfläche beheizt (NRF beheizt) (m²)",
                        "type": "integer",
                        "key": "FormField_NettoRaumflaecheBeheizt",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_BruttoRauminhalt": {
                        "fieldType": "integer",
                        "title": "Brutto-Rauminhalt BRI (m²)",
                        "type": "integer",
                        "key": "FormField_BruttoRauminhalt",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      }
                    }
                  }, "WeitereAngaben": {
                    "key": "WeitereAngaben",
                    "fieldType": "object",
                    "title": "Weitere Angaben",
                    "type": "object",
                    "additionalProperties": false,
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "properties": {
                      "FormField_Kurzbezeichnung": {
                        "fieldType": "text",
                        "title": "Kurzbezeichnung",
                        "type": "string",
                        "key": "FormField_Kurzbezeichnung",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 8, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_Gebaeudetyp": {
                        "fieldType": "select",
                        "title": "Gebäudetyp",
                        "type": "string",
                        "key": "FormField_Gebaeudetyp",
                        "anyOf": [{
                          "title": "10 Freistehendes Gebäude",
                          "const": "Value_10_Freistehendes_Gebaeude"
                        }, {
                          "title": "20 Nicht freistehendes Gebäude",
                          "const": "Value_20_Nicht_Freistehendes_Gebaeude"
                        }, {"title": "30 Anbau", "const": "Value_30_Anbau"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_BarrierefreierZugang": {
                        "fieldType": "select",
                        "title": "Barrierefreier Zugang",
                        "type": "string",
                        "key": "FormField_BarrierefreierZugang",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_BarrierefreierAufzug": {
                        "fieldType": "select",
                        "title": "Barrierefreier Aufzug",
                        "type": "string",
                        "key": "FormField_BarrierefreierAufzug",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_BarrierefreierInnenausbau": {
                        "fieldType": "select",
                        "title": "Barrierefreier Innenausbau",
                        "type": "string",
                        "key": "FormField_BarrierefreierInnenausbau",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_BlindengerechteAusstattung": {
                        "fieldType": "select",
                        "title": "Blindengerechte Ausstattung",
                        "type": "string",
                        "key": "FormField_BlindengerechteAusstattung",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_MassnahmeFuerHoergeschaedigte": {
                        "fieldType": "select",
                        "title": "Maßnahmen für Hörgeschädigte",
                        "type": "string",
                        "key": "FormField_MassnahmeFuerHoergeschaedigte",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_Personenaufzug": {
                        "fieldType": "select",
                        "title": "Personenaufzug",
                        "type": "string",
                        "key": "FormField_Personenaufzug",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_Lastenaufzug": {
                        "fieldType": "select",
                        "type": "string",
                        "key": "FormField_Lastenaufzug",
                        "title": "Lastenaufzug",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_BegruentesDach": {
                        "fieldType": "select",
                        "type": "string",
                        "key": "FormField_BegruentesDach",
                        "title": "Begrüntes Dach",
                        "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_GueltigkeitszeitraumAb": {
                        "fieldType": "date",
                        "title": "Gültigkeitszeitraum ab",
                        "type": "string",
                        "format": "date",
                        "key": "FormField_GueltigkeitszeitraumAb",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_GueltigkeitszeitraumBis": {
                        "fieldType": "date",
                        "title": "Gültigkeitszeitraum bis",
                        "type": "string",
                        "format": "date",
                        "key": "FormField_GueltigkeitszeitraumBis",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "FormField_Zusatztext": {
                        "fieldType": "textarea",
                        "title": "Zusatztext",
                        "x-display": "textarea",
                        "type": "string",
                        "key": "FormField_Zusatztext",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": [],
                        "maxLength": 1500
                      }
                    }
                  }, "c29f2ddf-b557-4b2a-9688-3ba50bc08e1e": {
                    "key": "c29f2ddf-b557-4b2a-9688-3ba50bc08e1e",
                    "fieldType": "optionalContainer",
                    "title": "Nutzer-/Mietobjekt",
                    "type": "object",
                    "x-options": {"sectionsTitlesClasses": []},
                    "x-props": {"outlined": true, "dense": true},
                    "oneOf": [{
                      "title": "Nutzer-/Mietobjekt(e) angeben - Referat Bildung und Sport",
                      "key": "f7e1917e-6315-4ba3-8524-a9f079905063",
                      "x-options": {"childrenClass": "pl-0"},
                      "properties": {
                        "OV_Referat": {"fieldType": "const", "type": "string", "const": "RBS", "key": "OV_Referat"},
                        "NutzerMietobjekte": {
                          "key": "NutzerMietobjekte",
                          "fieldType": "arrayObject",
                          "title": "Nutzer-/Mietobjekte",
                          "type": "array",
                          "items": {
                            "type": "object", "properties": {
                              "AllgemeineDaten": {
                                "key": "AllgemeineDaten",
                                "fieldType": "object",
                                "title": "Allgemeine Daten",
                                "type": "object",
                                "additionalProperties": false,
                                "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                                "x-props": {"outlined": true, "dense": true},
                                "properties": {
                                  "FormField_ArtDesMietobjekts": {
                                    "fieldType": "select",
                                    "title": "Art des Mietobjekts",
                                    "type": "string",
                                    "key": "FormField_ArtDesMietobjekts",
                                    "anyOf": [{
                                      "title": "Berufsfachschule",
                                      "const": "Value_Berufsfachschule"
                                    }, {
                                      "title": "Berufsoberschule (BOS)",
                                      "const": "Value_Berufsoberschule"
                                    }, {
                                      "title": "Berufsschule",
                                      "const": "Value_Berufsschule"
                                    }, {
                                      "title": "Dienstwohnung THV / Platzwart",
                                      "const": "Value_DienstwohnungTHVPlatzwart"
                                    }],
                                    "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                                    "x-props": {"outlined": true, "dense": true},
                                    "x-rules": ["required"]
                                  },
                                  "FormField_NutzerkostenstelleFachbereich": {
                                    "fieldType": "text",
                                    "title": "Nutzerkostenstelle des Fachbereichs",
                                    "type": "string",
                                    "key": "FormField_NutzerkostenstelleFachbereich",
                                    "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                                    "x-props": {"outlined": true, "dense": true},
                                    "x-rules": []
                                  },
                                  "FormField_Traegerschaft": {
                                    "fieldType": "select",
                                    "title": "Trägerschaft",
                                    "type": "string",
                                    "key": "FormField_Traegerschaft",
                                    "anyOf": [{
                                      "title": "Städtisch",
                                      "const": "Value_Staedtisch"
                                    }, {"title": "Staatlich", "const": "Value_Staatlich"}, {
                                      "title": "Betriebsträger",
                                      "const": "Value_Betriebstraeger"
                                    }],
                                    "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                                    "x-props": {"outlined": true, "dense": true},
                                    "x-rules": ["required"]
                                  },
                                  "AenderungAb": {
                                    "key": "AenderungAb",
                                    "fieldType": "object",
                                    "type": "object",
                                    "additionalProperties": false,
                                    "title": "Änderung ab",
                                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                                    "x-props": {"outlined": true, "dense": true},
                                    "properties": {
                                      "FormField_Tag": {
                                        "fieldType": "select",
                                        "title": "Tag",
                                        "type": "string",
                                        "key": "FormField_Tag",
                                        "readOnly": true,
                                        "default": "Value_01",
                                        "anyOf": [{"title": "01.", "const": "Value_01"}, {
                                          "title": "02.",
                                          "const": "Value_02"
                                        }],
                                        "x-options": {"fieldColProps": {"cols": 12, "sm": 2, "messages": {}}},
                                        "x-props": {"outlined": true, "dense": true},
                                        "x-rules": []
                                      },
                                      "FormField_Monat": {
                                        "fieldType": "select",
                                        "title": "Monat",
                                        "type": "string",
                                        "key": "FormField_Monat",
                                        "anyOf": [{"title": "Januar", "const": "Value_Januar"}, {
                                          "title": "Februar",
                                          "const": "Value_Februar"
                                        }, {"title": "März", "const": "Value_Maerz"}, {
                                          "title": "April",
                                          "const": "Value_April"
                                        }, {"title": "Mai", "const": "Value_Mai"}, {
                                          "title": "Juni",
                                          "const": "Value_Juni"
                                        }, {"title": "Juli", "const": "Value_Juli"}, {
                                          "title": "August",
                                          "const": "Value_August"
                                        }, {"title": "September", "const": "Value_September"}, {
                                          "title": "Oktober",
                                          "const": "Value_Oktober"
                                        }, {"title": "November", "const": "Value_November"}, {
                                          "title": "Dezember",
                                          "const": "Value_Dezember"
                                        }],
                                        "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                                        "x-props": {"outlined": true, "dense": true},
                                        "x-rules": []
                                      },
                                      "FormField_Jahr": {
                                        "fieldType": "select",
                                        "title": "Jahr",
                                        "type": "string",
                                        "key": "FormField_Jahr",
                                        "anyOf": [{"title": "2022", "const": "Value_2022"}, {
                                          "title": "2023",
                                          "const": "Value_2023"
                                        }, {"title": "2024", "const": "Value_2024"}, {
                                          "title": "2025",
                                          "const": "Value_2025"
                                        }, {"title": "2026", "const": "Value_2026"}, {
                                          "title": "2027",
                                          "const": "Value_2027"
                                        }, {"title": "2028", "const": "Value_2028"}, {
                                          "title": "2029",
                                          "const": "Value_2029"
                                        }, {"title": "2030", "const": "Value_2030"}, {
                                          "title": "2031",
                                          "const": "Value_2031"
                                        }, {"title": "2032", "const": "Value_2032"}],
                                        "x-options": {"fieldColProps": {"cols": 12, "sm": 2, "messages": {}}},
                                        "x-props": {"outlined": true, "dense": true},
                                        "x-rules": []
                                      }
                                    }
                                  }
                                }
                              },
                              "Flaechenangabe": {
                                "key": "Flaechenangabe",
                                "fieldType": "object",
                                "title": "Flächenangabe",
                                "type": "object",
                                "additionalProperties": false,
                                "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                                "x-props": {"outlined": true, "dense": true},
                                "properties": {
                                  "FormField_Mietflaeche": {
                                    "fieldType": "number",
                                    "title": "Mietfläche des Mietobjekts (Nettoraumfläche) in m²",
                                    "type": "number",
                                    "key": "FormField_Mietflaeche",
                                    "x-options": {"fieldColProps": {"cols": 12, "sm": 5, "messages": {}}},
                                    "x-props": {"outlined": true, "dense": true},
                                    "x-rules": ["required"]
                                  }
                                }
                              }
                            }
                          },
                          "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                          "x-props": {"outlined": true, "dense": false}
                        }
                      }
                    }, {
                      "title": "keine Angabe von Nutzer/Mietobjekt(en) - anderes Referat",
                      "key": "8d2565be-e911-433e-86ed-7114003319a7",
                      "x-options": {"childrenClass": "pl-0"},
                      "properties": {
                        "OV_Referat": {
                          "fieldType": "const",
                          "type": "string",
                          "const": "Value_Anderes",
                          "key": "OV_Referat"
                        }
                      }
                    }]
                  }
                }
              },
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": false}
            }
          }
        }]
      }, {
        "title": "Wirtschaftseinheit",
        "type": "object",
        "key": "5be066a2-f2e3-4f6e-8167-72c82fa0243b",
        "x-options": {"sectionsTitlesClasses": []},
        "allOf": [{
          "containerType": "group",
          "title": " ",
          "key": "4fcaed2a-bf6f-4eea-8422-2afd12ba3d03",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "WEGrundstueck": {
              "key": "WEGrundstueck",
              "fieldType": "object",
              "title": "WE-Grundstück",
              "type": "object",
              "additionalProperties": false,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "properties": {
                "ac7979f4-0d9c-412b-abca-ea148f26e96a": {
                  "key": "ac7979f4-0d9c-412b-abca-ea148f26e96a",
                  "fieldType": "optionalContainer",
                  "title": "Statistische Kostenstelle",
                  "type": "object",
                  "x-options": {"sectionsTitlesClasses": []},
                  "x-props": {"outlined": true, "dense": true},
                  "oneOf": [{
                    "title": "Kostellenstelle Kommunalreferat",
                    "key": "35035ce1-a613-40f9-a96c-fb3a413ebae0",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "OV_Referat": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "KR",
                        "key": "OV_Referat"
                      },
                      "FormField_StatistischeKostenstelle": {
                        "fieldType": "select",
                        "title": "Statistische Kostenstelle",
                        "type": "string",
                        "key": "FormField_StatistischeKostenstelle",
                        "anyOf": [{
                          "title": "14800000 Stadteigene Verwaltungsgebäude",
                          "const": "Value_14800000"
                        }, {
                          "title": "14800001 Angemietete Verwaltungsgebäude",
                          "const": "Value_14800001"
                        }, {"title": "14800010 Stadteigene Betriebsgebäude", "const": "Value_14800010"}],
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      }
                    }
                  }, {
                    "title": "Kostellenstelle Referat Bildung und Sport",
                    "key": "8819096c-0270-416f-a927-90244d0547d3",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "OV_Referat": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "RBS",
                        "key": "OV_Referat"
                      },
                      "FormField_StatistischeKostenstelleRBS": {
                        "fieldType": "text",
                        "title": "Statistische Kostenstelle",
                        "type": "string",
                        "key": "FormField_StatistischeKostenstelleRBS",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": ["required"]
                      }
                    }
                  }, {
                    "title": "keine Kostenstelle - anderes Referat",
                    "key": "4192f606-fec6-4c88-98c6-8bc4a077d161",
                    "x-options": {"childrenClass": "pl-0"},
                    "properties": {
                      "OV_Referat": {
                        "fieldType": "const",
                        "type": "string",
                        "const": "Anderes",
                        "key": "OV_Referat"
                      }
                    }
                  }],
                  "readOnly": false
                },
                "FormField_Optionssatzmethode": {
                  "fieldType": "select",
                  "title": "Optionssatzmethode",
                  "type": "string",
                  "key": "FormField_Optionssatzmethode",
                  "anyOf": [{
                    "title": "Flächenverhältnis (automatisch)",
                    "const": "Value_Flaechenverhaeltnis"
                  }, {
                    "title": "Optiert nicht (0%)",
                    "const": "Value_OptiertNicht"
                  }, {
                    "title": "Sonstiges Verfahren (manuell)",
                    "const": "Value_SonstigesVerfahren"
                  }, {"title": "Optiert (100%)", "const": "Value_Optiert"}],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                }
              }
            }, "WEGebaeude_Liste": {
              "key": "WEGebaeude_Liste",
              "fieldType": "arrayObject",
              "title": "WE-Gebäude",
              "type": "array",
              "items": {
                "type": "object", "properties": {
                  "FormField_WEGebaeude_Optionssatzmethode": {
                    "fieldType": "select",
                    "title": "Optionssatzmethode",
                    "type": "string",
                    "key": "FormField_WEGebaeude_Optionssatzmethode",
                    "anyOf": [{
                      "title": "Flächenverhältnis (autmatisch)",
                      "const": "Value_Flaechenverhaeltnis"
                    }, {
                      "title": "Optiert nicht (0%)",
                      "const": "Value_OptiertNicht"
                    }, {
                      "title": "Sonstiges Verfahren (manuell)",
                      "const": "Value_SonstigesVerfahren"
                    }, {"title": "Optiert", "const": "Value_Optiert"}],
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": ["required"]
                  },
                  "undefined": {
                    "key": "undefined",
                    "fieldType": "optionalContainer",
                    "title": "IM-KST Entlastung",
                    "type": "object",
                    "x-options": {"sectionsTitlesClasses": []},
                    "x-props": {"outlined": true, "dense": true},
                    "oneOf": [{
                      "title": "Entlastung angeben - Kommunalreferat",
                      "key": "c02e8f1a-ddab-4d47-8ec3-180c8e11a1b0",
                      "x-options": {"childrenClass": "pl-0"},
                      "properties": {
                        "OV_Referat": {"fieldType": "const", "type": "string", "const": "KR", "key": "OV_Referat"},
                        "FormField_WEGebaeude_ImKstEntlastung": {
                          "fieldType": "select",
                          "title": "IM-KST Entlastung",
                          "type": "string",
                          "key": "FormField_WEGebaeude_ImKstEntlastung",
                          "description": "\"Nicht Verrechnungsrelevant\" ist immer auszuwählen, wenn die Immobilie nicht dem Buchungskreis 0175 zugeordnet ist. Ansonsten ist immer die Kostenstelle des Objektverantwortlichen auszuwählen.",
                          "anyOf": [{
                            "title": "14150200-NK (Entlastung IM-KS-KST)",
                            "const": "Value_14150200"
                          }, {
                            "title": "14150310-NK (Entlastung IM-VGB-KST)",
                            "const": "Value_14150310"
                          }, {
                            "title": "14150320-NK (Entlastung IM-VB-BFV-KST)",
                            "const": "Value_14150320"
                          }, {
                            "title": "14150400-NK (Entlastung IM-FS-KST)",
                            "const": "Value_14150400"
                          }, {
                            "title": "14150500-NK (Entlastung IM-GW-KST)",
                            "const": "Value_14150500"
                          }, {"title": "Nicht verrechnungsrelevant", "const": "Value_NichtVerrechnungsrelevant"}],
                          "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                          "x-props": {"outlined": true, "dense": true},
                          "x-rules": ["required"]
                        }
                      }
                    }, {
                      "title": "Keine Entlastung angeben - anderes Referat",
                      "key": "cd03b007-5472-4bea-897c-011e8421c726",
                      "x-options": {"childrenClass": "pl-0"},
                      "properties": {
                        "OV_Referat": {
                          "fieldType": "const",
                          "type": "string",
                          "const": "Anderes",
                          "key": "OV_Referat"
                        }
                      }
                    }]
                  },
                  "FormField_WEGebaeude_Fahrradkeller": {
                    "fieldType": "select",
                    "title": "Fahrradkeller",
                    "type": "string",
                    "key": "FormField_WEGebaeude_Fahrradkeller",
                    "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_WEGebaeude_Fahrstuhl": {
                    "fieldType": "select",
                    "title": "Fahrstuhl",
                    "type": "string",
                    "key": "FormField_WEGebaeude_Fahrstuhl",
                    "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_WEGebaeude_Muellraum": {
                    "fieldType": "select",
                    "title": "Müllraum",
                    "type": "string",
                    "key": "FormField_WEGebaeude_Muellraum",
                    "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_WEGebaeude_Trockenraum": {
                    "fieldType": "select",
                    "title": "Trockenraum",
                    "type": "string",
                    "key": "FormField_WEGebaeude_Trockenraum",
                    "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_WEGebaeude_Waschkeuche": {
                    "fieldType": "select",
                    "title": "Waschküche",
                    "type": "string",
                    "key": "FormField_WEGebaeude_Waschkeuche",
                    "anyOf": [{"title": "Ja", "const": "Value_Ja"}, {"title": "Nein", "const": "Value_Nein"}],
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  }
                }
              },
              "x-options": {"editMode": "inline", "fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": false}
            }
          }
        }]
      }, {
        "title": "Grundstück",
        "type": "object",
        "key": "f2ceeca2-2e93-4988-a6de-fb329ec53a92",
        "x-options": {"sectionsTitlesClasses": []},
        "allOf": [{
          "containerType": "group",
          "key": "34927519-98ad-4f0e-9cbf-7bee3141d641",
          "title": " ",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "Grundstueck": {
              "key": "Grundstueck",
              "fieldType": "object",
              "title": " ",
              "type": "object",
              "additionalProperties": false,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "properties": {
                "FormField_Eigentumsverhaeltnis": {
                  "fieldType": "select",
                  "title": "Eigentumsverhältnis",
                  "type": "string",
                  "key": "FormField_Eigentumsverhaeltnis",
                  "anyOf": [{"title": "10 stadteigen", "const": "Value_10"}, {
                    "title": "20 nicht im Eigentum der Stadt",
                    "const": "Value_20"
                  }, {"title": "30 Teileigentum", "const": "Value_30"}, {"title": "40 unbekannt", "const": "Value_40"}],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "Geschaeftspartner1": {
                  "key": "Geschaeftspartner1",
                  "fieldType": "object",
                  "title": "Geschäftspartner 1",
                  "type": "object",
                  "additionalProperties": false,
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "properties": {
                    "FormField_SachgebietsleiterObjektverantwortung": {
                      "fieldType": "user-input",
                      "title": "Sachgebietsleiter*in Objektverantwortung",
                      "x-display": "custom-user-input",
                      "type": "string",
                      "key": "FormField_SachgebietsleiterObjektverantwortung",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_Objektverantwortlicher": {
                      "fieldType": "user-input",
                      "title": "Objektverantwortliche*r",
                      "x-display": "custom-user-input",
                      "type": "string",
                      "key": "FormField_Objektverantwortlicher",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_ObjektverantwortlicheOrganisationseinheit": {
                      "fieldType": "select",
                      "title": "Objektverantwortliche Organisationseinheit",
                      "type": "string",
                      "key": "FormField_ObjektverantwortlicheOrganisationseinheit",
                      "anyOf": [{"title": "RBS-ZIM-N-1-N", "const": "Value_RBS_ZIM_N_1_N"}, {
                        "title": "RBS-ZIM-N-1-O",
                        "const": "Value_RBS_ZIM_N_1_O"
                      }, {"title": "RBS-ZIM-N-1-S", "const": "Value_RBS_ZIM_N_1_S"}, {
                        "title": "RBS-ZIM-N-2-W",
                        "const": "Value_RBS_ZIM_N_2_W"
                      }, {"title": "RBS-ZIM-N-2-EE", "const": "Value_ZIM_N_2_EE"}, {
                        "title": "RBS-ZIM-ImmoV-2-W",
                        "const": "RBS_ZIM_ImmoV_2_W"
                      }],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_TechnischerDienstleisterElektrotechnik": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Elektrotechnik",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterElektrotechnik",
                      "anyOf": [{"title": "H 81", "const": "Value_H81"}, {
                        "title": "H 82",
                        "const": "Value_H82"
                      }, {"title": "H 83", "const": "Value_H83"}, {
                        "title": "H 84",
                        "const": "Value_H84"
                      }, {"title": "H 85", "const": "Value_H85"}, {
                        "title": "H 86",
                        "const": "Value_H86"
                      }, {"title": "H 1T", "const": "Value_H1T"}, {
                        "title": "KR-IM-TKN",
                        "const": "Value_KR_IM_TKN"
                      }, {"title": "KR-IM-TKO", "const": "Value_KR_IM_TKO"}, {
                        "title": "KR-IM-TKS",
                        "const": "Value_KR_IM_TKS"
                      }, {"title": "GEWOFAG", "const": "Value_GEWOFAG"}, {
                        "title": "GWG",
                        "const": "Value_GWG"
                      }, {"title": "SWM GmbH", "const": "Value_SWM_GMBH"}, {
                        "title": "Mieter",
                        "const": "Value_Mieter"
                      }, {"title": "Vermieter", "const": "Value_Vermieter"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_TechnischerDienstleisterVersorgungstechnik": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Versorgungstechnik",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterVersorgungstechnik",
                      "anyOf": [{"title": "H 71", "const": "Value_H71"}, {
                        "title": "H 72",
                        "const": "Value_H72"
                      }, {"title": "H 73", "const": "Value_H73"}, {
                        "title": "H 74",
                        "const": "Value_H74"
                      }, {"title": "H 75", "const": "Value_H75"}, {
                        "title": "H 76",
                        "const": "Value_H76"
                      }, {"title": "H 1T", "const": "Value_H1T"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_TechnischerDienstleisterBetrieb": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Betrieb",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterBetrieb",
                      "anyOf": [{"title": "Servicedesk H9", "const": "Value_Servicedesk_H9"}, {
                        "title": "Vermieter",
                        "const": "Value_Vermieter"
                      }, {"title": "Externer Dienstleister", "const": "Value_ExternerDienstleister"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_TechnischerDienstleisterBetriebTelefonnummer": {
                      "fieldType": "text",
                      "title": "Technischer Dienstleister Betrieb Telefonnummer",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterBetriebTelefonnummer",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    },
                    "FormField_TechnischerDienstleisterGartenbau": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Gartenbau",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterGartenbau",
                      "anyOf": [{"title": "G 11", "const": "Value_G11"}, {
                        "title": "G 12",
                        "const": "Value_G12"
                      }, {"title": "G 13", "const": "Value_G13"}, {
                        "title": "G 14",
                        "const": "Value_G14"
                      }, {"title": "G 15", "const": "Value_G15"}, {
                        "title": "G 21",
                        "const": "Value_G21"
                      }, {"title": "G 22", "const": "Value_G22"}, {
                        "title": "G 23",
                        "const": "Value_G23"
                      }, {"title": "G 31", "const": "Value_G31"}, {
                        "title": "G 32",
                        "const": "Value_G32"
                      }, {"title": "G 33", "const": "Value_G33"}, {
                        "title": "Vermieter",
                        "const": "Value_Vermieter"
                      }, {"title": "Mieter", "const": "Value_Mieter"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": ["required"]
                    }
                  }
                },
                "Geschaeftspartner2": {
                  "key": "Geschaeftspartner2",
                  "fieldType": "object",
                  "title": "Geschäftspartner 2",
                  "type": "object",
                  "additionalProperties": false,
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "properties": {
                    "FormField_TechnischerDienstleisterElektrotechnik": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Elektrotechnik",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterElektrotechnik",
                      "anyOf": [{"title": "H 81", "const": "Value_H81"}, {
                        "title": "H 82",
                        "const": "Value_H82"
                      }, {"title": "H 83", "const": "Value_H83"}, {
                        "title": "H 84",
                        "const": "Value_H84"
                      }, {"title": "H 85", "const": "Value_H85"}, {
                        "title": "H 86",
                        "const": "Value_H86"
                      }, {"title": "H 1T", "const": "Value_H1T"}, {
                        "title": "KR-IM-TKN",
                        "const": "Value_KR_IM_TKN"
                      }, {"title": "KR-IM-TKO", "const": "Value_KR_IM_TKO"}, {
                        "title": "KR-IM-TKS",
                        "const": "Value_KR_IM_TKS"
                      }, {"title": "GEWFAG", "const": "Value_GEWOFAG"}, {
                        "title": "GWG",
                        "const": "Value_GWG"
                      }, {"title": "SWM GmbH", "const": "Value_SWM"}, {
                        "title": "Mieter",
                        "const": "Value_Mieter"
                      }, {"title": "Vermieter", "const": "Value_Vermieter"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_TechnischerDienstleisterVersorgungstechnik": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Versorgungstechnik",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterVersorgungstechnik",
                      "anyOf": [{"title": "H 71", "const": "Value_H71"}, {
                        "title": "H 72",
                        "const": "Value_H72"
                      }, {"title": "H 73", "const": "Value_H73"}, {"title": "H 74", "const": "Value_H74"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_TechnischerDienstleisterBetrieb": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Betrieb",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterBetrieb",
                      "anyOf": [{"title": "Servicedesk H9", "const": "Value_Servicedesk_H9"}, {
                        "title": "Vermieter",
                        "const": "Value_Vermieter"
                      }, {"title": "Externer Dienstleister", "const": "Value_ExternerDienstleister"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_TechnischerDienstleisterBetriebTelefonnummer": {
                      "fieldType": "text",
                      "title": "Technischer Dienstleister Betrieb Telefonnummer",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterBetriebTelefonnummer",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_TechnischerDienstleisterGartenbau": {
                      "fieldType": "select",
                      "title": "Technischer Dienstleister Gartenbau",
                      "type": "string",
                      "key": "FormField_TechnischerDienstleisterGartenbau",
                      "anyOf": [{"title": "G 11", "const": "Value_G11"}, {
                        "title": "G 12",
                        "const": "Value_G12"
                      }, {"title": "G 13", "const": "Value_G13"}, {
                        "title": "G 14",
                        "const": "Value_G14"
                      }, {"title": "G 15", "const": "Value_G15"}, {
                        "title": "G21",
                        "const": "Value_G21"
                      }, {"title": "G 22", "const": "Value_G22"}, {
                        "title": "G 23",
                        "const": "Value_G23"
                      }, {"title": "G 31", "const": "Value_G31"}, {
                        "title": "G 32",
                        "const": "Value_G32"
                      }, {"title": "G 33", "const": "Value_G33"}, {
                        "title": "Vermieter",
                        "const": "Value_Vermieter"
                      }, {"title": "Mieter", "const": "Value_Mieter"}],
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 4, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    }
                  }
                },
                "WeitereAngaben": {
                  "key": "WeitereAngaben",
                  "fieldType": "object",
                  "title": "Weitere Angaben",
                  "type": "object",
                  "additionalProperties": false,
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "properties": {
                    "FormField_Grundstuecksflaeche": {
                      "fieldType": "integer",
                      "title": "Grundstücksfläche (in m²)",
                      "type": "integer",
                      "key": "FormField_Grundstuecksflaeche",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_UnbebauteGrundstuecksflaeche": {
                      "fieldType": "integer",
                      "title": "Unbebaute Grundstücksfläche (in m²)",
                      "type": "integer",
                      "key": "FormField_UnbebauteGrundstuecksflaeche",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_GueltigkeitszeitraumAb": {
                      "fieldType": "date",
                      "title": "Gültigkeitszeitraum ab",
                      "type": "string",
                      "format": "date",
                      "key": "FormField_GueltigkeitszeitraumAb",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_GueltigkeitszeitraumBis": {
                      "fieldType": "date",
                      "title": "Gültigkeitszeitraum bis",
                      "type": "string",
                      "format": "date",
                      "key": "FormField_GueltigkeitszeitraumBis",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": []
                    },
                    "FormField_Zusatztext": {
                      "fieldType": "textarea",
                      "title": "Zusatztext",
                      "x-display": "textarea",
                      "type": "string",
                      "key": "FormField_Zusatztext",
                      "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                      "x-props": {"outlined": true, "dense": true},
                      "x-rules": [],
                      "maxLength": 1500
                    }
                  }
                },
                "FlurstueckeListe": {
                  "key": "FlurstueckeListe",
                  "fieldType": "arrayObject",
                  "title": "Flurstücke",
                  "type": "array",
                  "items": {
                    "type": "object",
                    "properties": {
                      "Gemarkung": {
                        "fieldType": "text",
                        "title": "Gemarkung",
                        "type": "string",
                        "key": "Gemarkung",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      },
                      "Flurstuecksnummer": {
                        "fieldType": "text",
                        "title": "Flurstücksnummer",
                        "type": "string",
                        "key": "Flurstuecksnummer",
                        "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                        "x-props": {"outlined": true, "dense": true},
                        "x-rules": []
                      }
                    }
                  },
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": false}
                }
              }
            }
          }
        }]
      }, {
        "title": "Geschosse",
        "type": "object",
        "key": "349792b9-47ad-4701-8a2f-81856b88debd",
        "x-options": {"sectionsTitlesClasses": ["d-none"]},
        "allOf": [{
          "containerType": "group",
          "title": " ",
          "key": "2a88501a-dcd5-4299-85b3-655fcafe2fac",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "GeschosseListe": {
              "key": "GeschosseListe",
              "fieldType": "arrayObject",
              "title": "Geschosse",
              "type": "array",
              "items": {
                "type": "object", "properties": {
                  "FormField_Geschossbezeichnung": {
                    "fieldType": "text",
                    "title": "Geschossbezeichnung",
                    "type": "string",
                    "key": "FormField_Geschossbezeichnung",
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_Bruttorgundfläche": {
                    "fieldType": "integer",
                    "title": "Bruttogrundfläche",
                    "type": "integer",
                    "key": "FormField_Bruttorgundfläche",
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_AnzahlStellplaetze": {
                    "fieldType": "integer",
                    "title": "Anzahl Stellplätze",
                    "type": "integer",
                    "key": "FormField_AnzahlStellplaetze",
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_GueltigkeitszeitraumAb": {
                    "fieldType": "date",
                    "title": "Gültigkeitszeitraum ab",
                    "type": "string",
                    "format": "date",
                    "key": "FormField_GueltigkeitszeitraumAb",
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_GueltigkeitszeitraumBis": {
                    "fieldType": "date",
                    "title": "Gültigkeitszeitraum bis",
                    "type": "string",
                    "format": "date",
                    "key": "FormField_GueltigkeitszeitraumBis",
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": []
                  },
                  "FormField_Zusatztext": {
                    "fieldType": "textarea",
                    "title": "Zusatztext",
                    "x-display": "textarea",
                    "type": "string",
                    "key": "FormField_Zusatztext",
                    "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                    "x-props": {"outlined": true, "dense": true},
                    "x-rules": [],
                    "maxLength": 1500
                  }
                }
              },
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true}
            }
          }
        }]
      }]
    });
    const changed = (newSchema: any) => {
      schema.value = newSchema;
    };

    provide('apiEndpoint', import.meta.env.BASE_URL + 'api/digitalwf-backend-service');
    provide('formContext', {
      id: 'Task01',
      type: 'task'
    })

    const settings = SettingsEN;

    const validate = () => {
      (form.value as HTMLFormElement).validate();
    }

    return {
      changed,
      validate,
      value,
      form,
      schema,
      settings
    }
  }
})

</script>
