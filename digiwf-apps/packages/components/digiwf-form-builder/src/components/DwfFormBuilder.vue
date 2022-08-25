<template>
  <div
      class="d-flex h-100">
    <dwf-form-builder-elements/>
    <v-card
        elevation="1"
        class="contentWrapper modeler-area">
      <v-card-title class="pt-1">{{ name }}</v-card-title>
      <v-card-subtitle>{{ description }}</v-card-subtitle>

      <v-list class="h-100">
        <draggable
            :list="currentValue.allOf"
            class="list-group"
            style="margin-bottom: 50px"
            handle=".handle"
            v-bind="dragOptions"
            @change="input"
            @start="drag = true"
            @end="drag = false"
        >
          <template v-for="section in currentValue.allOf">
            <dwf-form-section
                :key="uuid(section)"
                class="w-100"
                :value="section"
                @remove="onSectionRemoved"
                @input="onSectionChanged"
            />
          </template>

        </draggable>
      </v-list>
    </v-card>
  </div>
</template>

<script lang="ts">
import {defineComponent, provide, reactive, set} from "@vue/composition-api";
import {generateUUID} from "@/utils/UUIDGenerator";
import {Form, Section} from "@dh-nx-test/digiwf-form-renderer";

export default defineComponent({
  props: ['value', 'name', 'description', 'schema', 'builderSettings'],
  emits: ['input'],
  setup(props, {emit}) {
    const currentValue = reactive<Form>(props.value);
    const dragOptions = {
      animation: 200,
      group: "section",
      disabled: false,
      ghostClass: "ghost"
    }
    provide("builderSettings", props.builderSettings);

    const input = () => {
      emit("input", currentValue)
    };

    const uuid = (section: Section): string => {
      if (section.key) {
        return section.key;
      }
      set(section, "key", generateUUID());
      input();
      return section.key;
    }

    const onSectionChanged = (section: Section) => {
      for (let i = 0; i < currentValue.allOf.length; i++) {
        if (currentValue.allOf[i].key === section.key) {
          set(currentValue.allOf, i, section);
          input();
          return;
        }
      }
    }

    const onSectionRemoved = (key: string) => {
      currentValue.allOf = currentValue.allOf.filter((el: Section) => el.key != key);
      input();
    }

    return {
      currentValue,
      dragOptions,
      onSectionChanged,
      onSectionRemoved,
      input,
      uuid,
    }

  }
})

</script>

<style>

.container-header > div:first-child {
  padding: 0 !important;
  margin: 0;
}

.contentWrapper {
  padding: 20px;
  border-radius: 10px !important;
  background-color: white;
}

.container-content {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 20px;
}

.container-box {
  padding-right: 0;
}

.container-header {
  flex-grow: 1;
}

.container-header > div:first-child {
  padding: 0 !important;
  margin: 0;
}

.handle {
  cursor: grab;
}

.field-placeholder {
  min-height: 60px;
  margin-left: 40px;
  align-items: center;
  display: flex;
}

</style>

<style scoped>

.modeler-area {
  max-width: 1000px;
  min-height: 908px;
  margin-bottom: 8px;
  margin-left: 40px;
  flex-grow: 1;
  height: 100%;
}

</style>
