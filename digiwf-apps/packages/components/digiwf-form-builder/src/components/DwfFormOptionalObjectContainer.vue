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
            mdi-border-none-variant
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
              <dwf-edit-form-field-modal
                  :value="value"
                  :field-key="fieldKey"
                  :schemas="{'optionalContainer' : settings.conditionalObjectContainerSchema} "
                  :generic-schema="settings.conditionalContainerSchema"
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

      <draggable
          :list="value.oneOf"
          class="list-group"
          handle=".handle"
          v-bind="dragOptions"
          @change="onListChanged"
          @start="drag = true"
          @end="drag = false"
      >
        <dwf-form-optional-item
            v-for="optItem in value.oneOf"
            :key="uuid(optItem)"
            :value="optItem"
            :default="value.default"
            @defaultChanged="defaultChanged"
            @defaultDeleted="defaultDeleted"
            @input="onItemChanged"
            @remove="onItemRemoved"
        />
        <div
            v-if="value.oneOf < 1"
            slot="header"
            role="group"
            class="field-placeholder"
        >
          insert Item
        </div>
      </draggable>
    </v-list-group>
  </v-list-item>
</template>

<script lang="ts">
import {generateUUID} from "@/utils/UUIDGenerator";
import {defineComponent, inject, set} from "vue";

export default defineComponent({
  props: ['fieldKey', 'value'],
  emits: ['input', 'remove'],
  setup(props, {emit}) {
    const dragOptions = {
      animation: 200,
      group: "optionalItem",
      disabled: false
    };

    const settings = inject("builderSettings");

    const input = (value: any) => {
      emit('input', {
        key: props.fieldKey,
        newKey: value.key,
        value: value
      })
    }

    const removed = () => {
      emit('remove', props.fieldKey)
    }

    const onListChanged = () => {
      input(props.value);
    }

    const uuid = (container: any): string => {
      if (container.key) {
        return container.key;
      }
      const key = generateUUID();
      set(container, "key", key);
      input(props.value);
      return container.key;
    }

    const defaultChanged = (value: any) => {
      const defaultValue: any = {}
      defaultValue[value[0]] = value[1].const;
      const newContainer = {
        key: props.fieldKey,
        ...props.value,
        "default": defaultValue
      };
      input(newContainer);
    }

    const defaultDeleted = () => {
      props.value.default = undefined;
      input(props.value);
    }

    const onItemChanged = (container: any) => {
      for (let i = 0; i < props.value.oneOf.length; i++) {
        if (props.value.oneOf[i].key === container.key) {
          set(props.value.oneOf, i, container);
          input({
            key: props.fieldKey,
            ...props.value
          });
          return;
        }
      }
    }

    const onContainerChanged = (section: any) => {
      const newContainer = {
        key: props.fieldKey,
        ...section,
        oneOf: props.value.oneOf
      };
      input(newContainer);
    }

    const onItemRemoved = (key: string) => {
      props.value.oneOf = props.value.oneOf.filter((el: any) => el.key != key);
      input({
        key: props.fieldKey,
        ...props.value
      });
    }

    return {
      settings,
      dragOptions,
      input,
      removed,
      onListChanged,
      uuid,
      defaultChanged,
      defaultDeleted,
      onItemChanged,
      onContainerChanged,
      onItemRemoved
    }
  }
});

</script>


