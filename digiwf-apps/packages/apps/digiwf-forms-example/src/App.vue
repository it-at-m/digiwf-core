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
import {DwfFormRenderer} from "@muenchen/digiwf-form-renderer";
import {DwfFormBuilder} from "@muenchen/digiwf-form-builder";
import {DwfDateInput, DwfTimeInput} from "@muenchen/digiwf-date-input";
import {SettingsEN} from "@muenchen/digiwf-form-builder-settings";
import {defineComponent, provide, ref} from "vue";

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
                "title": "Group",
                "description": "",
                "x-options": {
                  "childrenClass": "pl-0"
                },
                "properties": {
                  "dateval": {
                    "fieldType": "date",
                    "title": "Date",
                    "x-display": "custom-date-input",
                    "type": "string",
                    "format": "date",
                    "key": "dateval",
                    "x-options": {
                      "fieldColProps": {
                        "cols": 12,
                        "sm": 12
                      },
                      "messages": {}
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
