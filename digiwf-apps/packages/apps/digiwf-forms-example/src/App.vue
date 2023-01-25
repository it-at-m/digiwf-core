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
        <dwf-form-builder :value="schema" @input="changed" :builderSettings="settings"></dwf-form-builder>
      </v-tab-item>
      <v-tab-item>
        <div style="padding: 30px">
          <v-form ref="form">
            <dwf-form-renderer :options="{locale : 'de', readOnly: false, markdownit: { breaks: true } }"
                               :schema="schema" :key="componentKey"
                               @input="valueChanged" :value="value">
              <template #custom-multi-file-input="context">
                <dwf-multi-file-input
                  v-bind="context"
                  :readonly="false"
                />
              </template>
              <template #custom-lm-test="context">
                <p v-bind="context">
                  test
                </p>
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
          <pre class="codeblock">{{JSON.stringify(value, undefined, 4)}}</pre>
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
import { SettingsEN } from "@muenchen/digiwf-form-builder-settings";
import { DwfMultiFileInput } from "@muenchen/digiwf-multi-file-input";
import { defineComponent, provide, ref } from "vue";

export default defineComponent({
  components: {DwfFormRenderer, DwfFormBuilder, DwfMultiFileInput},
  setup() {
    const componentKey = ref(0);

    const form = ref(null);

    const value = ref({});

    const schema = ref({});
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
                "title": "Group",
                "description": "",
                "x-options": {
                  "childrenClass": "pl-0"
                },
                "properties": {
                  "aaf3bc4d-1e46-4399-b8e4-67678f6101ec": {
                    "fieldType": "boolean",
                    "title": "Checkbox",
                    "type": "boolean",
                    "key": "aaf3bc4d-1e46-4399-b8e4-67678f6101ec",
                    "default": false,
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "93b7eb24-54bc-42a5-bf10-e33f2108a4c4": {
                    "fieldType": "text",
                    "title": "Textfield",
                    "type": "string",
                    "key": "93b7eb24-54bc-42a5-bf10-e33f2108a4c4",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "bf47a93a-152e-4cf9-ad3d-e7a0c2636309": {
                    "fieldType": "user-input",
                    "title": "Benutzerauswahl",
                    "x-display": "custom-user-input",
                    "type": "string",
                    "key": "bf47a93a-152e-4cf9-ad3d-e7a0c2636309",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "7910a83b-fb4d-4da4-b482-a8fce586b3ab": {
                    "fieldType": "file",
                    "title": "Files",
                    "x-display": "custom-multi-file-input",
                    "type": "object",
                    "properties": {
                      "key": {
                        "type": "string"
                      },
                      "amount": {
                        "type": "integer"
                      }
                    },
                    "key": "7910a83b-fb4d-4da4-b482-a8fce586b3ab",
                    "uuidEnabled": false,
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "88ea16b6-82b3-40f0-b6b9-37d0f273d256": {
                    "fieldType": "file",
                    "title": "Files",
                    "x-display": "custom-multi-file-input",
                    "type": "object",
                    "properties": {
                      "key": {
                        "type": "string"
                      },
                      "amount": {
                        "type": "integer"
                      }
                    },
                    "key": "88ea16b6-82b3-40f0-b6b9-37d0f273d256",
                    "uuidEnabled": false,
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "3ab9f847-b614-43ce-9411-072d5f4eddd8": {
                    "fieldType": "text",
                    "title": "Textfield",
                    "type": "string",
                    "key": "3ab9f847-b614-43ce-9411-072d5f4eddd8",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "5a8dc839-ecb7-4f29-8210-8318cc51b05d": {
                    "fieldType": "text",
                    "title": "Textfield",
                    "type": "string",
                    "key": "5a8dc839-ecb7-4f29-8210-8318cc51b05d",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "lm-test-file": {
                    "fieldType": "file",
                    "title": "asdf",
                    "x-display": "custom-lm-test",
                    "type": "object",
                    "properties": {
                      "key": {
                        "type": "string"
                      },
                      "amount": {
                        "type": "integer"
                      }
                    },
                    "key": "88ea16b6-82b3-40f0-b6b9-37d0f273d256",
                    "uuidEnabled": false,
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "lm-test": {
                    "fieldType": "file",
                    "title": "lm-test",
                    "type": "object",
                    "properties": { },
                    "x-display": "custom-lm-test",
                    "key": "5a8dc839-ecb7-4f29-8210-8318cc51b05d",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
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
                  "lm-test-works": {
                    "fieldType": "file",
                    "title": "lm-test",
                    "type": "object",
                    "x-display": "custom-lm-test",
                    "key": "5a8dc839-ecb7-4f29-8210-8318cc51b05d",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 6,
                        "messages": {}
                      }
                    },
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    },
                    "x-rules": []
                  }
                },
                "key": "28656bcf-8add-4f52-a0b1-4d3b68696f3a"
              }
            ]
          }
        ]
      };
      value.value = {};
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
