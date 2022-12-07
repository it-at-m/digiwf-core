<template>
  <v-app>
    <menu></menu>
    <button @click="undo">undo</button>
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
            <dwf-form-renderer :options="{}" @input="valueChanged" :value="value"
                               :schema="schema" :key="componentKey"></dwf-form-renderer>
          </v-form>
          <v-btn @click="validate">Validate</v-btn>
        </div>
      </v-tab-item>
    </v-tabs>
    <!--    <label>Schema</label><textarea :value="JSON.stringify(schema, undefined, 4)" style="height: 800px;"></textarea>-->
    <label>Value</label><textarea :value="JSON.stringify(value, undefined, 4)" style="height: 800px;"></textarea>
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
    const componentKey = ref(0);

    const form = ref(null);

    const value = ref({});

    const schema = ref({
      "type": "object", "x-display": "stepper", "allOf": [{
        "key": "sectionKey1",
        "title": "Antrag",
        "type": "object",
        "x-options": {"sectionsTitlesClasses": ["d-none"]},
        "allOf": [{
          "containerType": "group",
          "title": " ",
          "key": "fec184c0-a5ba-402b-b00c-d63a48547645",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "FormField_HinweisAntrag": {
              "fieldType": "markdown",
              "title": " ",
              "type": "string",
              "x-display": "markdown",
              "key": "FormField_HinweisAntrag",
              "readOnly": true,
              "default": "**Hinweis: Der Fahrkostenzuschuss wird frühstens vom Ersten des Monats an gezahlt, in dem der Antrag gestellt wurde.**",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": []
            }
          }
        }, {
          "key": "feldgruppe1",
          "title": "Antrag von",
          "description": "Wer stellt den Antrag?",
          "x-options": {"childrenClass": "pr-5 pl-0"},
          "properties": {
            "FormField_Personalnummer": {
              "fieldType": "text",
              "title": "Ihre Personalnummer",
              "type": "string",
              "key": "FormField_Personalnummer",
              "description": "Ihre Personalnummer finden Sie auf Ihrem Entgeltnachweis.",
              "readOnly": false,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            }
          }
        }, {
          "containerType": "optionalContainer",
          "title": "Nachwuchskraft",
          "type": "object",
          "x-options": {"sectionsTitlesClasses": ["d-none"]},
          "x-props": {"outlined": true, "dense": true},
          "allOf": [{
            "fieldType": "optionalContainer",
            "title": "Sind Sie eine Nachwuchskraft?",
            "type": "object",
            "x-options": {"sectionsTitlesClasses": []},
            "x-props": {"outlined": true, "dense": true},
            "oneOf": [{
              "title": "Nein",
              "key": "ecf14d23-e315-4be8-a5fb-3f238253408e",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Nachwuchskraft": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "Nein",
                  "key": "FormField_Nachwuchskraft"
                }
              }
            }, {
              "title": "Ja, ich bin Auszubildende*r",
              "key": "b863c262-324f-42e4-9fa0-aa0c507bb7e3",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Nachwuchskraft": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "Auszubildende",
                  "key": "FormField_Nachwuchskraft"
                },
                "FormField_Fachrichtung": {
                  "fieldType": "select",
                  "title": "Fachrichtung",
                  "type": "string",
                  "key": "FormField_Fachrichtung",
                  "anyOf": [{
                    "title": "Fachinformatiker*in Anwendungsentwicklung (FIAE)",
                    "const": "FIAE"
                  }, {
                    "title": "Fachinformatiker*in Systemintegration (FISI)",
                    "const": "FISI"
                  }, {
                    "title": "IT-Systemelektroniker*in (ITSE)",
                    "const": "ITSE"
                  }, {
                    "title": "Kaufleute für Büromanagement (KfB)",
                    "const": "KfB"
                  }, {
                    "title": "Verwaltungsfachangestellte*r Kommunal (VfAK)",
                    "const": "VfAK"
                  }, {
                    "title": "Verwaltungswirt*in (Beamtenanwärter*in, zweite Qualifikationsebene) (QE 2)",
                    "const": "QE2"
                  }, {"title": "Sonstige", "const": "sonstigeAusbildung"}],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_JahrgangUndKurs": {
                  "fieldType": "text",
                  "title": "Jahrgang und Kurs",
                  "type": "string",
                  "key": "FormField_JahrgangUndKurs",
                  "description": "Zum Beispiel: 20/23 A.",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                }
              }
            }, {
              "title": "Ja, ich bin Studierende*r",
              "key": "f990ba94-4805-49d2-b96b-4db6acfa9b62",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Nachwuchskraft": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "Studierende",
                  "key": "FormField_Nachwuchskraft"
                },
                "FormField_FachrichtungStudierende": {
                  "fieldType": "select",
                  "title": "Fachrichtung",
                  "type": "string",
                  "key": "FormField_FachrichtungStudierende",
                  "anyOf": [{
                    "title": "Bachelor of Laws (LLB)",
                    "const": "LLB"
                  }, {
                    "title": "Bachelor of Science Informatic (B.Sc.)",
                    "const": "BSc"
                  }, {
                    "title": "Bachelor Soziale Arbeit Kommunal (BSAK)",
                    "const": "BSAK"
                  }, {
                    "title": "Bachelor Wirtschaftsinformatik - Kommunal (BWI)",
                    "const": "BWI"
                  }, {
                    "title": "Diplom-Verwaltungsinformatiker*in (Beamtenanwärter*in, dritte Qualifikationsebene) (QE 3-IT)",
                    "const": "QE3IT"
                  }, {
                    "title": "Diplomverwaltungswirt*in (Beamtenanwärter*in, dritte Qualifikationsebene) (QE 3)",
                    "const": "QE3"
                  }, {"title": "Public Management (PuMa)", "const": "PuMa"}, {
                    "title": "Sonstige",
                    "const": "sonstigeStudium"
                  }],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_JahrgangKurs": {
                  "fieldType": "text",
                  "title": "Jahrgang und Kurs",
                  "type": "string",
                  "key": "FormField_JahrgangKurs",
                  "description": "Zum Beispiel: 20/23 A.",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                }
              }
            }],
            "key": "61d99719-94c3-457f-aa2c-a1e7ef2e61ff"
          }],
          "key": "1d32d2e6-edca-4b44-9ddb-55fe7d8c98db"
        }, {
          "containerType": "group",
          "title": "Adresse Dienststelle",
          "key": "2972a200-f061-440f-b34b-e86a443627e2",
          "description": "Die aktuelle Adresse Ihrer Dienststelle",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "FormField_HausnummerDienststelle": {
              "fieldType": "text",
              "title": "Straßenname und Hausnummer",
              "type": "string",
              "key": "FormField_HausnummerDienststelle",
              "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 6}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            },
            "FormField_PLZDienststelle": {
              "fieldType": "text",
              "title": "Postleitzahl",
              "type": "string",
              "key": "FormField_PLZDienststelle",
              "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 3}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            },
            "FormField_StadtDienststelle": {
              "fieldType": "text",
              "title": "Stadt",
              "type": "string",
              "key": "FormField_StadtDienststelle",
              "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 3}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            }
          }
        }, {
          "containerType": "group",
          "title": "Adresse Wohnort",
          "key": "769aaf4c-2037-47a5-9806-7bf49ea8ab42",
          "description": "Die aktuelle Adresse Ihres Wohnorts",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "FormField_HausnummerWohnort": {
              "fieldType": "text",
              "title": "Straßenname und Hausnummer",
              "type": "string",
              "key": "FormField_HausnummerWohnort",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            },
            "FormField_PLZWohnort": {
              "fieldType": "text",
              "title": "Postleitzahl",
              "type": "string",
              "key": "FormField_PLZWohnort",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            },
            "FormField_StadtWohnort": {
              "fieldType": "text",
              "title": "Stadt",
              "type": "string",
              "key": "FormField_StadtWohnort",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 3, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            }
          }
        }, {
          "containerType": "group",
          "title": "Fahrkostenzuschuss verlängern/ ändern",
          "key": "e0d5b2bb-8492-4a8a-98a3-2a5fbacc31e0",
          "description": "Fahrkostenzuschuss verlängern/ ändern",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {}
        }, {
          "containerType": "optionalContainer",
          "title": "Was möchten Sie tun?",
          "type": "object",
          "x-options": {"sectionsTitlesClasses": ["d-none"]},
          "x-props": {"outlined": true, "dense": true},
          "allOf": [{
            "fieldType": "optionalContainer",
            "title": "Was möchten Sie tun?",
            "type": "object",
            "x-options": {"sectionsTitlesClasses": []},
            "x-props": {"outlined": true, "dense": true},
            "oneOf": [{
              "title": "Fahrkostenzuschuss verlängern (ohne Änderung)",
              "key": "fee082c9-f8eb-44bc-b80d-57a366129acb",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Auswahl": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "FKZVerlaengernOhneAenderung",
                  "key": "FormField_Auswahl"
                }
              }
            }, {
              "title": "Fahrkostenzuschuss verlängern (mit Änderung)",
              "key": "3363b502-b4b0-4771-9b25-94ade069670b",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Auswahl": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "FKZVerlaengernMitAenderung",
                  "key": "FormField_Auswahl"
                },
                "FormField_AenderungGeltungsbereich": {
                  "fieldType": "boolean",
                  "title": "Änderung betrifft nicht den Geltungsbereich",
                  "type": "boolean",
                  "key": "FormField_AenderungGeltungsbereich",
                  "readOnly": false,
                  "description": "Eine Änderung des Geltungsbereichs liegt vor, wenn Sie durch veränderte Umstände (Berufswechsel in Fokusgruppe, Zonen-Wechsel et cetera) berechtigt sind zukünftig mehr oder weniger Geld oder einen Fahrkostenzuschuss für ein anderes Verkehrsmittel (ÖPNV oder KFZ) zu erhalten. In diesem Fall müssen Sie einen Erstantrag auf Fahrkostenzuschuss stellen.",
                  "default": false,
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 5}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Markdowndown": {
                  "fieldType": "markdown",
                  "type": "string",
                  "x-display": "markdown",
                  "key": "FormField_Markdowndown",
                  "default": "[LINK](https://wilma.muenchen.de/pages/jobticket-und-fahrkostenzuschuss/apps/content/formulare) zum Erstantrag. ",
                  "readOnly": true,
                  "title": " ",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_AenderungAnschrift": {
                  "fieldType": "boolean",
                  "type": "boolean",
                  "key": "FormField_AenderungAnschrift",
                  "title": "Änderung Ihrer Anschrift (Dienststelle oder Wohnort), wie zuvor bereits angegeben",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_Fahrpreisaenderung": {
                  "fieldType": "boolean",
                  "title": "Änderung des Fahrpreises",
                  "type": "boolean",
                  "key": "FormField_Fahrpreisaenderung",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 12}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_AenderungFahrpreisAngaben": {
                  "fieldType": "number",
                  "title": "Tragen Sie hier bitte gegebenenfalls den neuen Fahrpreis ein (in Euro)",
                  "type": "number",
                  "key": "FormField_AenderungFahrpreisAngaben",
                  "description": "Wenn Sie einen Fahrkostenzuschuss für Ihr KFZ beantragen, geben Sie bitte hier den Ticketpreis an, den Sie als Nutzer*in von öffentlichen Verkehrsmitteln bezahlen würden.",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 7}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_AngabeZahlungsrythmus": {
                  "fieldType": "select",
                  "title": "Zahlungsrhythmus",
                  "type": "string",
                  "key": "FormField_AngabeZahlungsrythmus",
                  "description": "Geben Sie hier bitte an, ob Sie für Ihr Ticket monatlich oder jährlich zahlen.",
                  "anyOf": [{"title": "monatlich", "const": "monatlich"}, {"title": "jährlich", "const": "jaehrlich"}],
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 5}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_SonstigeAenderung": {
                  "fieldType": "boolean",
                  "title": "Sonstige Änderungen",
                  "type": "boolean",
                  "key": "FormField_SonstigeAenderung",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 12}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_BeispielSonstigeAenderungen": {
                  "fieldType": "text",
                  "title": "Tragen Sie hier bitte gegebenenfalls Ihre sonstigen Änderungen ein",
                  "type": "string",
                  "key": "FormField_BeispielSonstigeAenderungen",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 12}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                }
              }
            }, {
              "title": "Fahrkostenzuschuss ändern",
              "key": "e219728f-89aa-4ca9-8578-ca8b5c7f8b6e",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Auswahl": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "FKZAendern",
                  "key": "FormField_Auswahl"
                },
                "FormField_Geltungsbereich": {
                  "fieldType": "boolean",
                  "title": "Änderung betrifft nicht den Geltungsbereich",
                  "type": "boolean",
                  "key": "FormField_Geltungsbereich",
                  "description": "Eine Änderung des Geltungsbereichs liegt vor, wenn Sie durch veränderte Umstände (Berufswechsel in Fokusgruppe, Zonen-Wechsel et cetera) berechtigt sind zukünftig mehr oder weniger Geld oder einen Fahrkostenzuschuss für ein anderes Verkehrsmittel (ÖPNV oder KFZ) zu erhalten. In diesem Fall müssen Sie einen Erstantrag auf Fahrkostenzuschuss stellen.",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 5}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["required"]
                },
                "FormField_Markdown": {
                  "fieldType": "markdown",
                  "type": "string",
                  "x-display": "markdown",
                  "key": "FormField_Markdown",
                  "title": " ",
                  "default": "[LINK](https://wilma.muenchen.de/pages/jobticket-und-fahrkostenzuschuss/apps/content/formulare) zum Erstantrag. ",
                  "readOnly": true,
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 6, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_AenderungenAnschrift": {
                  "fieldType": "boolean",
                  "title": "Änderung Ihrer Anschrift (Dienststelle oder Wohnort), wie zuvor bereits angegeben",
                  "type": "boolean",
                  "key": "FormField_AenderungenAnschrift",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_FahrpreisaenderungAllgemein": {
                  "fieldType": "boolean",
                  "title": "Änderung des Fahrpreises",
                  "type": "boolean",
                  "key": "FormField_FahrpreisaenderungAllgemein",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_AenderungFahrpreisAngaben2": {
                  "fieldType": "number",
                  "title": "Tragen Sie hier bitte gegebenenfalls den neuen Fahrpreis ein (in Euro)",
                  "type": "number",
                  "key": "FormField_AenderungFahrpreisAngaben2",
                  "description": "Wenn Sie einen Fahrkostenzuschuss für Ihr KFZ beantragen, geben Sie bitte hier den Ticketpreis an, den Sie als Nutzer*in von öffentlichen Verkehrsmitteln bezahlen würden.",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 7}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_AngabeZahlungsrythmus2": {
                  "fieldType": "select",
                  "title": "Zahlungsrhythmus",
                  "type": "string",
                  "key": "FormField_AngabeZahlungsrythmus2",
                  "description": "Geben Sie hier bitte an, ob Sie für Ihr Ticket monatlich oder jährlich zahlen.",
                  "anyOf": [{"title": "monatlich", "const": "monatlich"}, {"title": "jährlich", "const": "jaehrlich"}],
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 5, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_Sonst": {
                  "fieldType": "boolean",
                  "title": "Sonstige Änderungen",
                  "type": "boolean",
                  "key": "FormField_Sonst",
                  "x-options": {"fieldColProps": {"cols": 12, "messages": {}, "sm": 12}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                },
                "FormField_BeispielSonstige": {
                  "fieldType": "text",
                  "title": "Tragen Sie hier bitte gegebenenfalls Ihre sonstigen Änderungen ein",
                  "type": "string",
                  "key": "FormField_BeispielSonstige",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                }
              }
            }],
            "key": "ce81fd41-b76a-4e6d-8fe0-c8017624592d"
          }],
          "key": "75435413-5d5a-4ce9-b5b2-d8614ae424f8"
        }, {
          "containerType": "optionalContainer",
          "title": " Ihr aktuelles Verkehrsmittel",
          "type": "object",
          "x-options": {"sectionsTitlesClasses": ["d-none"]},
          "x-props": {"outlined": true, "dense": true},
          "allOf": [{
            "fieldType": "optionalContainer",
            "title": "Ihr aktuelles Verkehrsmittel",
            "type": "object",
            "x-options": {"sectionsTitlesClasses": []},
            "x-props": {"outlined": true, "dense": true},
            "oneOf": [{
              "title": "Öffentliche(s) Verkehrsmittel",
              "key": "988b8ef6-0161-4424-b7fe-aa6f26e4784f",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Verkehrsmittel": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "oepnv",
                  "key": "FormField_Verkehrsmittel"
                },
                "FormField_Tickets": {
                  "fieldType": "file",
                  "title": "Kopie Ihres neuen Tickets (PDF oder JPEG)",
                  "x-display": "custom-multi-file-input",
                  "type": "object",
                  "properties": {"key": {"type": "string"}, "amount": {"type": "integer"}},
                  "key": "FormField_Tickets",
                  "uuidEnabled": false,
                  "description": "Wenn Sie kein neues Ticket besitzen, da Sie zum Beispiel nur eine veränderte Anschrift angeben möchten, können Sie die folgenden Felder überspringen.",
                  "filePath": "dokumente/ticket",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["requiredObject"]
                },
                "FormField_Zahlungsnachweis": {
                  "fieldType": "file",
                  "title": "Kopie Ihres neuen Zahlungsnachweises (PDF oder JPEG)",
                  "x-display": "custom-multi-file-input",
                  "type": "object",
                  "properties": {"key": {"type": "string"}, "amount": {"type": "integer"}},
                  "key": "FormField_Zahlungsnachweis",
                  "uuidEnabled": false,
                  "description": "Wenn Sie aktuell über keinen Zahlungsnachweis verfügen, können Sie hier auch das Schreiben Ihres Fahrkartenanbieters hochladen und den Zahlungsnachweis zu einem späteren Zeitpunkt nachreichen.",
                  "filePath": "dokumente/zahlungsnachweis",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                }
              }
            }, {
              "title": "KFZ",
              "key": "4e683ec6-93d8-4df6-8a6c-6088ade23a53",
              "x-options": {"childrenClass": "pl-0"},
              "properties": {
                "FormField_Verkehrsmittel": {
                  "fieldType": "const",
                  "type": "string",
                  "const": "kfz",
                  "key": "FormField_Verkehrsmittel"
                },
                "FormField_Nachweise": {
                  "fieldType": "file",
                  "title": "Nachweise (PDF oder JPEG)",
                  "x-display": "custom-multi-file-input",
                  "type": "object",
                  "properties": {"key": {"type": "string"}, "amount": {"type": "integer"}},
                  "key": "FormField_Nachweise",
                  "uuidEnabled": false,
                  "description": "Falls Sie berechtigt sind einen Fahrkostenzuschuss für Ihr KFZ zu erhalten, laden Sie hier bitte die entsprechenden Nachweise hoch (persönliche oder dienstliche Gründe).",
                  "filePath": "dokumente/nachweise",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": ["requiredObject"]
                },
                "FormField_Rechercheunterlagen": {
                  "fieldType": "file",
                  "title": "Gegebenenfalls Rechercheunterlagen (PDF oder JPEG)",
                  "x-display": "custom-multi-file-input",
                  "type": "object",
                  "properties": {"key": {"type": "string"}, "amount": {"type": "integer"}},
                  "key": "FormField_Rechercheunterlagen",
                  "uuidEnabled": false,
                  "description": "Wenn Sie einen Fahrkostenzuschuss für Ihr KFZ über Zone M hinaus erhalten und sich Änderungen am Fahrpreis ergeben haben, können Sie hier Ihre neuen Rechercheunterlagen hochladen. ",
                  "filePath": "dokumente/rechercheunterlagen",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
                  "x-props": {"outlined": true, "dense": true},
                  "x-rules": []
                }
              }
            }],
            "key": "630d023f-55a7-4bd1-b45a-8cb228fd08b0"
          }],
          "key": "9fbd3299-c2e8-42f0-828a-0ae9141051e4"
        }]
      }, {
        "title": "Rechtliche Hinweise",
        "type": "object",
        "key": "b6bacd6c-32a2-4c8c-90a7-fc1f78d2422f",
        "x-options": {"sectionsTitlesClasses": ["d-none"]},
        "allOf": [{
          "title": "Gruppe",
          "description": "",
          "type": "object",
          "x-options": {"childrenClass": "pr-5 pl-0"},
          "properties": {
            "FormField_RichtigkeitVollständigkeit": {
              "fieldType": "markdown",
              "title": "Richtigkeit und Vollständigkeit",
              "type": "string",
              "x-display": "markdown",
              "key": "FormField_RichtigkeitVollständigkeit",
              "readOnly": true,
              "default": "Ich versichere nach besten Wissen die Richtigkeit und Vollständigkeit meiner Angaben. Ich habe davon Kenntnis genommen, dass die Gewährung des Fahrkostenzuschusses, außer in den Fällen anerkannter KFZ-Nutzung, davon abhängig ist, dass ich für regelmäßige Fahrten zwischen meiner Wohnung und meiner Dienststelle ein Verkehrsmittel des ÖPNV im Sinne des der FKZ-RiLi2020 nutze. Ich verpflichte mich ferner, jede Änderung der für die Gewährung des Fahrkostenzuschusses maßgebenden Verhältnisse unaufgefordert und unverzüglich schriftlich der zuständigen Personalstelle vor Ort mitzuteilen. Dies gilt insbesondere in folgenden Fällen:\n* Wohnungswechsel, Dienststellenwechsel, Fahrpreisänderung\n* Erkrankung von mehr als einem Kalendermonat\n* Mutterschutz, Elternzeit\n* Dienstabwesenheiten unter Fortzahlung der Bezüge/ des Entgelts, die länger als einen vollen Kalendermonat dauern (zum Beispiel Dienstreisen, Fortbildungslehrgänge, Kuren, Wehrübungen, Altersteilzeit-Freistellungsphase)\n* sonstige Dienstabwesenheiten unter Wegfall der Bezüge/ des Entgelts (zum Beispiel unbezahlte Beurlaubung, unerlaubtes Fernbleiben vom Dienst), unabhängig von der Dauer der Abwesenheit\n* Frühzeitige Beendigung der Ausbildung",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": []
            },
            "FormField_DSGVO": {
              "fieldType": "markdown",
              "title": "Datenschutzhinweise nach Artikel 13 und 14 Datenschutzgrundverordnung (DSGVO)",
              "type": "string",
              "x-display": "markdown",
              "key": "FormField_DSGVO",
              "default": "Verantwortlich für die Verarbeitung Ihrer personenbezogenen Daten ist die Landeshauptstadt München, 80313 München (E-Mail: [personal@muenchen.de](mailto:personal@muenchen.de)). Weitere Informationen über die Verarbeitung Ihrer personenbezogenen Daten und Ihre  diesbezüglichen Rechte finden Sie im Internet unter [https://www.muenchen.de/mitarbeiterservice]( https://www.muenchen.de/mitarbeiterservice). Alternativ erhalten Sie diese Informationen auch unter den obigen Kontaktdaten. Unsere behördliche Datenschutzbeauftragte können Sie unter Marienplatz 8, 80331 München (E Mail: [datenschutz@muenchen.de](mailto:datenschutz@muenchen.de)) kontaktieren.",
              "readOnly": true,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": []
            },
            "FormField_Hinweis": {
              "fieldType": "markdown",
              "title": "Hinweis",
              "type": "string",
              "x-display": "markdown",
              "key": "FormField_Hinweis",
              "default": "Zur Durchführung des Datenabgleiches bezüglich des Fortbestandes der Beschäftigung tauscht die Landeshauptstadt München gegebenenfalls die erforderlichen Daten mit dem Anbieter Ihres Tickets aus. Näheres entnehmen Sie bitte dem jeweiligen Bestellformular.",
              "readOnly": true,
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": []
            },
            "FormField_hinweiseAkzeptiert": {
              "fieldType": "boolean",
              "title": "Ich stimme den oben genannten rechtlichen Hinweisen zu",
              "type": "boolean",
              "key": "FormField_hinweiseAkzeptiert",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12, "messages": {}}},
              "x-props": {"outlined": true, "dense": true},
              "x-rules": ["required"]
            }
          },
          "key": "ed132003-366a-44aa-806f-d9ce38bfa49f"
        }]
      }]
    });
    const changed = (newSchema: any) => {
      componentKey.value += 1;
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

    const valueChanged = (test: any) => {
      console.log("value changed " + test)
      value.value = test;
    }

    const undo = () => {
      schema.value = {
        "type": "object",
        "x-display": "tabs",
        "allOf": [{
          "key": "sectionKey1",
          "title": "Allgemeine Angaben",
          "type": "object",
          "x-options": {"sectionsTitlesClasses": []},
          "allOf": [{
            "containerType": "group",
            "title": "Group",
            "description": "",
            "x-options": {"childrenClass": "pl-0"},
            "properties": {
              "aaf3bc4d-1e46-4399-b8e4-67678f6101ec": {
                "fieldType": "boolean",
                "title": "Checkbox",
                "type": "boolean",
                "x-options": {"fieldColProps": {"cols": 12, "sm": 12}},
                "x-props": {"outlined": true, "dense": true}
              }
            },
            "key": "28656bcf-8add-4f52-a0b1-4d3b68696f3a"
          }]
        }]
      }
    }

    return {
      undo,
      componentKey,
      changed,
      validate,
      value,
      form,
      schema,
      settings,
      valueChanged
    }
  }
})

</script>
