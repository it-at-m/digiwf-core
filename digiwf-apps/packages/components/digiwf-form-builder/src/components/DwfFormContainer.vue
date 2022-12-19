<template>
  <v-list-item
      class="container-box pb-4"
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
            mdi-crop-free
          </v-icon>
          <span class="font-weight-bold">{{ value.title }}</span>
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
              <dwf-edit-container-modal
                  :value="value"
                  :schema="settings.containerSchema"
                  @saved="onContainerChanged"
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
import {computed, defineComponent, inject} from "vue";
import {FormFieldContainer} from "@muenchen/digiwf-form-renderer";


export default defineComponent({
  props: ['value'],
  emits: ['input', 'remove'],
  setup(props, {emit}) {
    const dragOptions = {
      animation: 200,
      group: "field",
      disabled: false,
      ghostClass: "ghost"
    }

    const settings = inject("builderSettings");

    const input = (value: FormFieldContainer) => {
      emit('input', value);
    }

    const removed = () => {
      emit("remove", props.value.key)
    }

    const properties = computed(() => Object.entries(props.value.properties))

    const onContainerChanged = (container: any) => {
      input({
            ...container,
            properties: props.value.properties
          }
      );
    }

    const propertiesChanged = (properties: any) => {
      input({
            ...props.value,
            properties: properties
          }
      );
    }

    return {
      dragOptions,
      settings,
      input,
      removed,
      properties,
      onContainerChanged,
      propertiesChanged
    }
  }
})

</script>
