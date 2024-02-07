<template>
  <v-app>
    <menu></menu>
    <v-tabs grow>
      <v-tab>
        builder
      </v-tab>
      <v-tab>
        renderer
      </v-tab>
      <v-tab>
        json schema
      </v-tab>
      <v-tab>
        schema value
      </v-tab>
      <v-tab-item>
        <dwf-form-builder :builderSettings="settings" :value="schema" @input="changed"></dwf-form-builder>
      </v-tab-item>
      <v-tab-item>
        <div style="padding: 30px">
          <v-text-field label="Change"
                        @beforeinput="evt => (!evt.data || /[\d,+-]/.test(evt.data)) || evt.preventDefault()"></v-text-field>
          <v-text-field label="Keydown" type="number"
                        @keydown="obj => (obj.key.length > 1 || /[\d,+-]/.test(obj.key)) || obj.preventDefault()"></v-text-field>
          <v-form ref="form">
            <dwf-form-renderer :key="componentKey"
                               :options="{locale : 'de', readOnly: false, markdownit: { breaks: true } }"
                               :schema="schema"
                               :value="value" @input="valueChanged">
              <template #custom-date-input="context">
                <dwf-date-input v-bind="context"/>
              </template>
              <template #custom-time-input="context">
                <dwf-time-input v-bind="context"/>
              </template>
            </dwf-form-renderer>
          </v-form>
          <v-btn @click="validate">Validate</v-btn>
        </div>
      </v-tab-item>
      <v-tab-item>
        <div style="padding: 30px">
          <textarea
            :value="JSON.stringify(schema, undefined, 4)"
            class="codeblock">
          </textarea>
          <v-btn @click="initSchema">Reset</v-btn>
        </div>
      </v-tab-item>
      <v-tab-item>
        <div style="padding: 30px">
          <pre class="codeblock">{{ JSON.stringify(value, undefined, 4) }}</pre>
        </div>
      </v-tab-item>
    </v-tabs>
  </v-app>
</template>

<style>
html, body {
  height: 100%;
}

.codeblock {
  width: 100%;
  height: 700px;
}
</style>

<script lang="ts">
import { DwfFormRenderer } from "@muenchen/digiwf-form-renderer";
import { DwfFormBuilder } from "@muenchen/digiwf-form-builder";
import { DwfDateInput, DwfTimeInput } from "@muenchen/digiwf-date-input";
import { defineComponent, provide, ref } from "vue";
import { SettingsEN } from "@muenchen/digiwf-form-builder-settings";

export default defineComponent({
  components: {DwfFormRenderer, DwfFormBuilder, DwfDateInput, DwfTimeInput},
  setup() {
    const componentKey = ref(0);

    const form = ref(null);

    const value = ref({});

    const schema = ref({"dateval": ""});
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
      value.value = test;
    }

    const initSchema = () => {
      // change the schema below for debugging
      schema.value = {
        "type": "object",
        "x-display": "tabs",
        "allOf": [
          {
            "key": "sectionKey1",
            "title": "Allgemeine Angaben",
            "type": "object",
            "x-options": {
              "sectionsTitlesClasses": []
            },
            "allOf": [
              {
                "containerType": "group",
                "title": "Custom Errors: Simple Types",
                "description": "",
                "key": "28656bcf-8add-4f52-a0b1-4d3b68696f3a",
                "x-options": {
                  "childrenClass": "pl-0"
                },
                "properties": {
                  "96d29f1e-bcce-4aee-91e8-1e72d4f9e85a": {
                    "fieldType": "text",
                    "title": "Textfield",
                    "type": "string",
                    "key": "96d29f1e-bcce-4aee-91e8-1e72d4f9e85a",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minLength": "Custom error min",
                        "maxLength": "Custom error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": [],
                    "minLength": 1,
                    "maxLength": 2
                  },
                  "080662a6-c790-4594-bb25-7a0547e64214": {
                    "fieldType": "textarea",
                    "title": "Textarea",
                    "x-display": "textarea",
                    "type": "string",
                    "key": "080662a6-c790-4594-bb25-7a0547e64214",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minLength": "Custom error min",
                        "maxLength": "Custom error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": [],
                    "minLength": 1,
                    "maxLength": 2
                  },
                  "18426ea7-209c-449e-9cab-a3e99a5d4961": {
                    "fieldType": "integer",
                    "title": "Number",
                    "type": "integer",
                    "key": "18426ea7-209c-449e-9cab-a3e99a5d4961",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minimum": "Custom Error min",
                        "maximum": "Custom Error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": [],
                    "minimum": 1,
                    "maximum": 2
                  },
                  "53524c20-4c90-48a1-a994-2e9bd1a67977": {
                    "fieldType": "number",
                    "title": "Floating Number",
                    "type": "number",
                    "key": "53524c20-4c90-48a1-a994-2e9bd1a67977",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minimum": "Custom Error min",
                        "maximum": "Custom Error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": []
                  },
                  "725c5f4c-607f-4fea-980e-0412b6f196c9": {
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "fieldType": "select",
                    "title": "Select",
                    "type": "string",
                    "anyOf": []
                  }
                }
              },
              {
                "containerType": "group",
                "title": "Custom Errors: Array Types",
                "description": "",
                "key": "7871dbe6-def3-44b5-914d-ad80c8b71ec6",
                "x-options": {
                  "childrenClass": "pl-0"
                },
                "properties": {
                  "a89299a1-87b7-4225-a003-7db5a5f9df6d": {
                    "key": "a89299a1-87b7-4225-a003-7db5a5f9df6d",
                    "fieldType": "arrayObject",
                    "title": "Object List",
                    "type": "array",
                    "items": {
                      "type": "object",
                      "properties": {}
                    },
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minItems": "Custom error min",
                        "maxItems": "Custom error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": false
                    },
                    "minItems": 1,
                    "maxItems": 2
                  },
                  "64392302-7a38-4e91-8e83-50b1958712c4": {
                    "fieldType": "multi-user-input",
                    "title": "Mehrfache Benutzerauswahl",
                    "x-display": "custom-multi-user-input",
                    "type": "array",
                    "items": {
                      "type": "string"
                    },
                    "key": "64392302-7a38-4e91-8e83-50b1958712c4",
                    "default": [],
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minItems": "Custom error min",
                        "maxItems": "Custom error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": [],
                    "minItems": 1,
                    "maxItems": 2
                  },
                  "ac0573a4-5d09-46ce-96ca-4a84bded621a": {
                    "fieldType": "multiselect",
                    "title": "Multiselect",
                    "type": "array",
                    "key": "ac0573a4-5d09-46ce-96ca-4a84bded621a",
                    "default": [],
                    "items": {
                      "anyOf": [
                        {
                          "title": "1",
                          "const": "1"
                        },
                        {
                          "title": "2",
                          "const": "2"
                        },
                        {
                          "title": "3",
                          "const": "3"
                        }
                      ],
                      "type": "string"
                    },
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minItems": "Custom error min",
                        "maxItems": "Custom error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": [],
                    "minItems": 1,
                    "maxItems": 2
                  },
                  "ed17d6aa-4ab3-4d34-b851-2baaaecbb2ad": {
                    "fieldType": "array",
                    "title": "List",
                    "type": "array",
                    "items": {
                      "type": "string"
                    },
                    "key": "ed17d6aa-4ab3-4d34-b851-2baaaecbb2ad",
                    "default": [],
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {
                        "minItems": "Custom error min",
                        "maxItems": "Custom error max"
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": false
                    },
                    "x-rules": [],
                    "minItems": 1,
                    "maxItems": 2
                  }
                }
              }
            ]
          }
        ]
      };
      value.value = {"dateval": ""};
    }

    initSchema();

    return {
      initSchema,
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
