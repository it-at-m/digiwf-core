<template>
  <v-list-item class="container-box" style="width: 100%">
    <v-flex class="container-content form-field">
      <v-icon
          size="30"
          class="mr-5 handle"
      >
        {{ icon }}
      </v-icon>
      <template v-if="value.fieldType === 'const'">
        <span class="flex-grow-1">{{ fieldKey }}</span>
        <span class="flex-grow-0 text-right mr-10">{{ value.fieldType }}</span>
      </template>
      <template v-else>
        <span class="flex-grow-1">{{ value.title }}</span>
        <span class="flex-grow-0 text-right mr-10">{{ value.fieldType }}</span>
      </template>

      <v-menu
          top
          offset-x
      >
        <template #activator="{ on, attrs }">
          <v-btn
              icon
              v-bind="attrs"
              v-on.prevent="on"
          >
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-list>
          <dwf-edit-form-field-modal
              :value="value"
              :field-key="fieldKey"
              :schemas="settings.formFieldSchemas"
              :generic-schema="settings.defaultFormFieldSchema"
              @saved="onFieldChanged"
          />

          <v-list-item
              v-if="value.fieldType !== 'const'"
              link
              @click="removed"
          > Remove </v-list-item>
        </v-list>
      </v-menu>
    </v-flex>
  </v-list-item>
</template>

<script lang="ts">
import {computed, defineComponent, inject} from "vue";
import {FormBuilderSettings} from "@muenchen/digiwf-form-builder-settings";

export default defineComponent({
  props: ['value', 'fieldKey'],
  emits: ['input', 'remove'],
  setup(props, {emit}) {
    const settings = inject<FormBuilderSettings>("builderSettings");

    const icon = computed(() => settings!.iconSettings.iconMap[props.value.fieldType] ?? settings!.iconSettings.defaultIcon)

    const input = (field: any) => {
      emit("input", {
        key: props.fieldKey,
        newKey: field.key,
        value: field
      });
    }

    const removed = () => {
      emit('remove', props.fieldKey);
    }

    const onFieldChanged = (field: any) => {
      input(field);
    }

    return {
      settings,
      removed,
      input,
      onFieldChanged,
      icon

    }
  }

});

</script>

<style>

.form-field:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

</style>
