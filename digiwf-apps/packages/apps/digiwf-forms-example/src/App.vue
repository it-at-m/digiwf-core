<template>
  <v-app>
    <menu></menu>
    <DwfMultiFileInput
        color="primary"
        :schema='{"description": "Dies ist eine Beschreibung.", "filePath": "/Documents/", "uuidEnabled": true}'
        fullKey="FormField_Datei"
        :rules="['Required']"
        label="File"
    ></DwfMultiFileInput>
    <dwf-form-builder :value="schema" :builderSettings="settings"></dwf-form-builder>
  </v-app>
</template>

<script lang="ts">
import {DwfFormRenderer} from "@muenchen/digiwf-form-renderer";
import {DwfFormBuilder} from "@muenchen/digiwf-form-builder";
import {SettingsEN} from "@muenchen/digiwf-form-builder-settings";
import {DwfMultiFileInput} from "@muenchen/digiwf-multi-file-input";
import {defineComponent, provide} from "vue";

export default defineComponent({
  components: {DwfFormRenderer, DwfFormBuilder, DwfMultiFileInput},
  setup() {
    const schema = {
      "type": "object",
      "x-display": "",
      "allOf": [
        {
          "key": "sectionKey1",
          "type": "object",
          "title": "Das ist meine Section",
          "x-options": {
            "sectionsTitlesClasses": [
              "font-weight-bold"
            ]
          },
          "allOf": [
            {
              "key": "feldgruppe1",
              "title": "Hinweise",
              "type": "object",
              "description": "Mit diesem Workflow können Sie Grüße versenden. Bitte beachten Sie, dass die Grüße nicht anonym versendet werden. Geben Sie in das entsprechende Feld einen Grußtext ein und wählen Sie die Art des Grußes aus.",
              "x-options": {
                "childrenClass": "pr-5 pl-0"
              },
              "properties": {
                "3d5b12cd-e6b8-4a16-9f55-a6fceba46312": {
                  "fieldType": "date",
                  "title": "Datum",
                  "type": "string",
                  "format": "date",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  }
                },
                "7c7f0547-7399-4501-9ac3-032d6f48bc62": {
                  "fieldType": "integer",
                  "title": "Zahl",
                  "type": "integer",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  }
                }
              }
            },
            {
              "title": "Daten zum Gruß",
              "type": "object",
              "key": "e267be11-e07b-435e-ab58-b3861d6b370a",
              "x-options": {
                "childrenClass": "pr-5 pl-0"
              },
              "properties": {
                "FormField_Empfaenger": {
                  "fieldType": "user-input",
                  "title": "Empfänger*in",
                  "type": "string",
                  "key": "FormField_Empfaenger",
                  "x-display": "custom-user-input",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12,
                      "messages": {}
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  },
                  "x-rules": [
                    "required"
                  ]
                },
                "FormField_ArtDesGrusses": {
                  "fieldType": "select",
                  "title": "Ich möchte einen Gruß versenden zum",
                  "type": "string",
                  "key": "FormField_ArtDesGrusses",
                  "anyOf": [
                    {
                      "title": "Geburtstag",
                      "const": "Geburtstag"
                    },
                    {
                      "title": "Namenstag",
                      "const": "Namenstag"
                    },
                    {
                      "title": "Dienstjubiläum",
                      "const": "Dienstjubilaeum"
                    }
                  ],
                  "x-options": {
                    "fieldColProps": {
                      "cols": 6,
                      "sm": 6,
                      "messages": {}
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  },
                  "x-rules": [
                    "required"
                  ]
                },
                "FormField_Zugehoerigkeit": {
                  "fieldType": "integer",
                  "title": "Zugehörigkeit zur LHM",
                  "type": "integer",
                  "key": "FormField_Zugehoerigkeit",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 6,
                      "sm": 6,
                      "messages": {}
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  },
                  "x-rules": []
                },
                "FormField_Grusstext": {
                  "fieldType": "textarea",
                  "title": "Grußtext (ohne Anrede)",
                  "x-display": "textarea",
                  "type": "string",
                  "key": "FormField_Grusstext",
                  "description": "Hier können Sie einen ausführlichen Grußtext verfassen!",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12,
                      "messages": {}
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  },
                  "x-rules": [
                    "required"
                  ]
                },
                "551d70d8-4a6c-48c4-9885-b14dc8bfc18e": {
                  "fieldType": "text",
                  "title": "Textfeld",
                  "type": "string",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  }
                },
                "a9838804-1fc7-405a-b84d-00b6c14ad005": {
                  "fieldType": "integer",
                  "title": "Zahl",
                  "type": "integer",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  }
                },
                "1f783198-a97b-4de5-86a1-d9c5a1ea2a89": {
                  "fieldType": "user-input",
                  "title": "Benutzerauswahl",
                  "x-display": "custom-user-input",
                  "type": "string",
                  "x-options": {
                    "fieldColProps": {
                      "cols": 12,
                      "sm": 12
                    }
                  },
                  "x-props": {
                    "outlined": true,
                    "dense": true
                  }
                }
              }
            }
          ]
        }
      ]
    }
    provide('apiEndpoint', import.meta.env.BASE_URL + 'api/digitalwf-backend-service');
    provide('formContext', {
      id: 'Task01',
      type: 'task'
    })

    const settings = SettingsEN;

    return {
      schema,
      settings
    }
  }
})

</script>
