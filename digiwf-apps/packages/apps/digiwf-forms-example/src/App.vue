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
              <template #custom-date-input="context">
                <dwf-date-input v-bind="context"/>
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
import { DwfDateInput } from "@muenchen/digiwf-date-input";
import { SettingsEN } from "@muenchen/digiwf-form-builder-settings";
import { defineComponent, provide, ref } from "vue";

export default defineComponent({
  components: {DwfFormRenderer, DwfFormBuilder, DwfDateInput},
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
