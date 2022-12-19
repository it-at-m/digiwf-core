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
              <dwf-edit-section-modal
                  :value="value"
                  :schema="settings.conditionalContainerSchema"
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
          :list="value.allOf"
          class="list-group"
          handle=".handle"
          v-bind="dragOptions"
          @change="onListChanged"
          @start="drag = true"
          @end="drag = false"
      >
        <dwf-form-optional-fields-container
            v-for="optItem in value.allOf"
            :key="uuid(optItem)"
            :value="optItem"
            @input="onItemChanged"
            @remove="onItemRemoved"
        />
        <div
            v-if="value.allOf < 1"
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
import {Container} from "@muenchen/digiwf-form-renderer";
import {FormBuilderSettings} from "@muenchen/digiwf-form-builder-settings";

export default defineComponent({
  props: ['value'],
  emits: ['input', 'remove'],
  setup(props, {emit}) {
    const settings = inject<FormBuilderSettings>("builderSettings");
    const dragOptions = {
      animation: 200,
      group: "optionalContainer",
      disabled: false
    };

    const input = (value: Container) => {
      emit("input", value);
    }

    const removed = () => {
      emit("remove", props.value.key);
    }

    const onListChanged = () => {
      input(props.value);
    }

    const uuid = (container: Container): string => {
      if (container.key) return container.key;
      set(container, "key", generateUUID());
      input(props.value);
      return container.key;
    }

    const onContainerChanged = (container: Container) => {
      const newContainer = {
        ...container,
        allOf: props.value.allOf
      };
      input(newContainer);
    }

    const onItemRemoved = (key: string) => {
      props.value.allOf = props.value.allOf.filter((el: any) => el.key != key);
      input(props.value);
    }

    const onItemChanged = (container: any) => {
      for (let i = 0; i < props.value.allOf.length; i++) {
        if (props.value.allOf[i].key === container.key) {
          set(props.value.allOf, i, container);
          props.value.allOf[i] = container;
          input(props.value);
          return;
        }
      }
    }

    return {
      settings,
      dragOptions,
      input,
      removed,
      onListChanged,
      uuid,
      onContainerChanged,
      onItemRemoved,
      onItemChanged
    }
  }

})

</script>


