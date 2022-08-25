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
            mdi-border-none
          </v-icon>
          <span class="font-weight-bold">{{ value.title }}</span>
          <v-spacer/>
          <span v-if=" isDefault" class="font-weight-bold mr-10">default</span>
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
              >
                <v-list-item-title>Remove</v-list-item-title>
              </v-list-item>
              <v-list-item
                  link
                  @click="defaultChanged"
              >
                <v-list-item-title>Default</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-flex>
      </template>

      <dwf-properties-container @input="propertiesChanged" :dragOptions="dragOptions" :properties="properties"/>

    </v-list-group>
  </v-list-item>
</template>

<script lang="ts">
import {computed, defineComponent, inject} from "@vue/composition-api";

export default defineComponent({
  props: ['value', 'default'],
  emits: ['input', 'defaultChanged', 'remove'],
  setup(props, {emit}) {
    const dragOptions = {
      animation: 200,
      group: "field",
      disabled: false,
      ghostClass: "ghost"
    };

    const settings = inject('builderSettings');

    const input = (value: any) => {
      emit('input', value)
    }
    const defaultChanged = (): any => {
      return extractKey();
    }

    const removed = () => {
      emit('remove', props.value.key)
    }

    const properties = computed(() => Object.entries(props.value.properties));

    const isDefault = computed(() => {
      if (!props.default) {
        return false;
      }
      return props.default && Object.entries(props.default)[0][1] === this.extractKey()[1].const;
    })

    const extractKey = (): any => {
      return Object.entries(this.value.properties).filter((obj: any) => obj[1]['const'])[0];
    }

    const onContainerChanged = (container: any) => {
      input(
          {
            ...container,
            properties: props.value.properties
          }
      );
    }

    const propertiesChanged = (properties: any) => {
      input(
          {
            ...props.value,
            properties: properties
          }
      );
    }

    return {
      dragOptions,
      settings,
      isDefault,
      properties,
      defaultChanged,
      removed,
      input,
      onContainerChanged,
      propertiesChanged
    }
  }
})

</script>
