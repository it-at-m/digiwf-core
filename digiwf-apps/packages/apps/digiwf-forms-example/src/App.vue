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
    {{ schema }}
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
          "type": "object",
          "properties": {
            "objectList": {
              "title": "Object-List",
              "type": "array",
              "x-options": {
                "editMode": "inline"
              },
              "items": {
                "type": "object",
                "properties": {}
              }
            }
          }
        }
    );
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
