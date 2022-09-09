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
            <dwf-form-renderer :options="{}" :schema="schema"></dwf-form-renderer>
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

    const schema = ref({
      "type": "object", "x-display": "", "allOf": [{
        "title": "Abschnitt",
        "description": "",
        "type": "object",
        "x-options": {"sectionsTitlesClasses": ["d-none"]},
        "allOf": [{
          "containerType": "group",
          "title": "Group",
          "description": "",
          "x-options": {"childrenClass": "pl-0"},
          "properties": {
            "391da0c5-9606-4358-9e35-2dc9b877ace8": {
              "fieldType": "textarea",
              "title": "Textarea",
              "x-display": "textarea",
              "type": "string",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12}},
              "x-props": {"outlined": true, "dense": true}
            },
            "5dab13fb-dfbc-4901-bf03-9e635df99892": {
              "key": "5dab13fb-dfbc-4901-bf03-9e635df99892",
              "fieldType": "object",
              "title": "Dynamisches Objekt",
              "type": "object",
              "x-options": {"fieldColProps": {"cols": 12, "sm": 12}},
              "x-props": {"outlined": true, "dense": true},
              "properties": {
                "97a0c7d2-e8e9-409b-8ae9-5712f3faa704": {
                  "fieldType": "text",
                  "title": "Textfeld",
                  "type": "string",
                  "x-options": {"fieldColProps": {"cols": 12, "sm": 12}},
                  "x-props": {"outlined": true, "dense": true}
                }
              }
            }
          },
          "key": "f02216df-ee8e-4dde-ab50-86340844baa2"
        }],
        "key": "6cd889c3-c0cd-497c-a02f-a5ef74721a24"
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
      form,
      schema,
      settings
    }
  }
})

</script>
