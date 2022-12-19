<template>
  <v-card class="mb-3">
    <v-list-group
        no-action
        class="section-header"
        append-icon=""
    >
      <template #activator>
        <v-flex class="section-content">
          <v-icon
              size="30"
              class="mr-5 handle"
          >
            mdi-tab
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
                  :schema="settings.sectionSchema"
                  @saved="onSectionChanged"
              />
              <v-list-item
                  link
                  @click="sectionRemoved"
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
        <template v-for="container in value.allOf">

          <dwf-form-optional-container
              v-if="container.containerType === 'optionalContainer'"
              :key="uuid(container)"
              :value="container"
              :isContainer="true"
              @input="onContainerChanged"
              @remove="onContainerRemoved"
          />
          <dwf-form-container
              v-else
              :key="uuid(container)"
              :value="container"
              @input="onContainerChanged"
              @remove="onContainerRemoved"
          />
        </template>
        <div
            v-if="value.allOf < 1"
            slot="header"
            role="group"
            class="d-flex align-center"
            style="min-height: 60px; margin-left: 30px"
        >
          insert container
        </div>
      </draggable>
    </v-list-group>
  </v-card>
</template>

<style>

.section-header > div:first-child {
  background-color: #f4f4f4;
}

</style>

<style scoped>

.section-content {
  width: 100%;
  display: flex;
  padding: 12px 5px 12px 12px;
  align-items: center;
  justify-content: center;
}

</style>

<script lang="ts">
import {generateUUID} from "@/utils/UUIDGenerator";
import {defineComponent, inject, set} from "vue";
import {FormBuilderSettings} from "@muenchen/digiwf-form-builder-settings";
import {Container, Section} from "@muenchen/digiwf-form-renderer";

export default defineComponent({
  props: ['value'],
  emits: ['input', 'remove'],
  setup(props, {emit}) {
    const dragOptions = {
      animation: 200,
      group: "container",
      disabled: false
    };
    const settings = inject<FormBuilderSettings>("builderSettings");

    const input = (value: Section) => {
      emit("input", value);
    }

    const sectionRemoved = () => {
      emit("remove", props.value.key);
    }

    const onListChanged = () => {
      input(props.value);
    }

    const uuid = (container: Container): string => {
      if (container.key) {
        return container.key;
      }
      set(container, "key", generateUUID());
      input(props.value);
      return container.key;
    }

    const onContainerChanged = (container: Container) => {
      for (let i = 0; i < props.value.allOf.length; i++) {
        if (props.value.allOf[i].key === container.key) {
          set(props.value.allOf, i, container);
          input(props.value);
          return;
        }
      }
    }

    const onSectionChanged = (section: Section) => {
      const newSection = {
        ...section,
        allOf: props.value.allOf
      };
      input(newSection);
    }

    const onContainerRemoved = (key: string): any => {
      props.value.allOf = props.value.allOf.filter((el: Container) => el.key != key);
      input(props.value);
    }

    return {
      dragOptions,
      settings,
      input,
      sectionRemoved,
      onListChanged,
      uuid,
      onContainerChanged,
      onSectionChanged,
      onContainerRemoved
    }
  }
})

</script>


