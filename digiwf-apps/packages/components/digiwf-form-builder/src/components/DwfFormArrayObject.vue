<template>
  <v-list-item
      class="container-box"
  >
    <v-list-group
        no-action
        sub-group
        prepend-icon=""
        class="container-header"
    >
      <template #activator>
        <v-flex class="container-content">
          <v-icon
              size="30"
              class="mr-5 handle"
          >
            {{ icon(value.fieldType) }}
          </v-icon>
          <span class="font-weight-regular">{{ value.title }}</span>
          <v-spacer/>
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
                  :schemas="settings.formFieldSchemas"
                  :generic-schema="settings.defaultFormFieldSchema"
                  :field-key="fieldKey"
                  @saved="onObjectChanged"
              />
              <v-list-item
                  link
                  @click="removed"
              > Remove </v-list-item>
            </v-list>
          </v-menu>
        </v-flex>
      </template>
      <dwf-properties-container @input="propertiesChanged" :dragOptions="dragOptions" :properties="properties"/>
    </v-list-group>
  </v-list-item>
</template>

<script lang="ts">
import {FormBuilderSettings} from "@muenchen/digiwf-form-builder-settings";
import {computed, defineComponent, inject} from "vue";

export default defineComponent({
  props: ['fieldKey', 'value'],
  emits: ['input', 'remove'],
  setup(props, {emit}) {
    const dragOptions = {
      animation: 200,
      group: "field",
      disabled: false,
      ghostClass: "ghost"
    };

    const settings = inject<FormBuilderSettings>("builderSettings")

    const input = (value: any) => {
      emit('input', {
        key: props.fieldKey,
        newKey: value.key,
        value: value
      });
    }

    const removed = () => {
      emit('remove', props.fieldKey);
    }

    const properties = computed(() => Object.entries(props.value.items.properties!));

    const icon = () => {
      return settings!.iconSettings.iconMap[props.value.fieldType] ?? settings!.iconSettings.defaultIcon;
    }

    const onObjectChanged = (container: any) => {
      input(
          {
            key: props.fieldKey,
            ...container,
            items: {
              type: "object",
              properties: props.value.items.properties
            }
          }
      );
    }

    const propertiesChanged = (properties: any) => {
      input(
          {
            key: props.fieldKey,
            ...props.value,
            items: {
              type: "object",
              properties: properties
            }
          }
      );
    }

    return {
      settings,
      properties,
      dragOptions,
      input,
      removed,
      icon,
      onObjectChanged,
      propertiesChanged
    }
  }
})

</script>
