<template>
  <v-card
      elevation="3"
      class="palette contentWrapper"
  >
    <h4 class="pl-5 pt-5">Sections</h4>
    <div class="field-divider"></div>
    <draggable
        :list="settings.modelerPalette.sections"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="secitonDragOptions"
    >
      <dwf-draggable-list-item
          v-for="section in settings.modelerPalette.sections"
          :key="section.title"
          class="pl-5"
          icon="mdi-tab"
          :name="section.title"
          :new-name="section.title"
          :new-description="section.description"
      />
    </draggable>

    <draggable
        :list="settings.modelerPalette.containers"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="containerDragOptions"
    >
      <dwf-draggable-list-item
          v-for="container in settings.modelerPalette.containers"
          :key="container.title"
          :icon="containerIcons(container.containerType)"
          class="pl-5"
          :name="container.title"
          :new-name="container.title"
          :new-description="container.description"
      />
    </draggable>

    <div style="margin-bottom: 20px"></div>
    <h4 class="pl-5">Objects</h4>
    <div class="field-divider"></div>
    <draggable
        v-for="field in settings.modelerPalette.objects"
        :key="field[1].title"
        :list="[field]"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="formObjectDragOtions"
    >
      <dwf-draggable-list-item
          :key="field[1].title"
          class="pl-5"
          :icon="icon(field[1].fieldType)"
          :name="field[1].title"
          :new-name="field[1].title"
          new-description=""
      />
    </draggable>

    <div style="margin-bottom: 20px"></div>
    <h4 class="pl-5">Optional Content</h4>
    <div class="field-divider"></div>
    <draggable
        v-for="optContent in settings.modelerPalette.optionalObject"
        :key="optContent.title"
        :list="[optContent]"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="conditionalContentContainerDragOptions"
    >
      <dwf-draggable-list-item
          :key="optContent.title"
          class="pl-5"
          :icon="icon(optContent.fieldType)"
          :name="optContent.title"
          :new-name="optContent.title"
          new-description=""
      />
    </draggable>
    <draggable
        v-for="field in settings.modelerPalette.optionalProperties"
        :key="field[1].title"
        :list="[field]"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="formObjectDragOtions"
    >
      <dwf-draggable-list-item
          :key="field[1].title"
          class="pl-5"
          :icon="icon(field[1].fieldType)"
          :name="field[1].title"
          :new-name="field[1].title"
          new-description=""
      />
    </draggable>
    <draggable
        v-for="optItem in settings.modelerPalette.optionalItem"
        :key="optItem.title"
        :list="[optItem]"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="conditionalContentContainerItemDragOptions"
    >
      <dwf-draggable-list-item
          :key="optItem.title"
          class="pl-5"
          :icon="icon(optItem.fieldType)"
          :name="optItem.title"
          :new-name="optItem.title"
          new-description=""
      />
    </draggable>

    <div style="margin-bottom: 20px"></div>
    <h4 class="pl-5">Form Fields</h4>
    <div class="field-divider"></div>
    <draggable
        v-for="field in settings.modelerPalette.formFields"
        :key="field[1].title"
        :list="[field]"
        class="list-group"
        handle=".handle"
        :clone="handleClone"
        v-bind="formFieldDragOtions"
    >
      <dwf-draggable-list-item
          :key="field[1].title"
          class="pl-5"
          :icon="icon(field[1].fieldType)"
          :name="field[1].title"
          :new-name="field[1].title"
          new-description=""
      />
    </draggable>
    <div class="pb-5"></div>
  </v-card>
</template>

<script lang="ts">
import {defineComponent, inject} from "vue";
import {FormBuilderSettings} from "@muenchen/digiwf-form-builder-settings";

export default defineComponent({
  setup() {
    const settings = inject<FormBuilderSettings>("builderSettings");

    const secitonDragOptions = {
      animation: 200,
      group: {name: 'section', pull: 'clone', put: false},
      disabled: false,
      chosenClass: "draggable",
    };

    const containerDragOptions = {
      animation: 200,
      group: {name: 'container', pull: 'clone', put: false},
      disabled: false,
      chosenClass: "draggable",
    };

    const conditionalContentContainerDragOptions = {
      animation: 200,
      group: {name: 'optionalContainer', pull: 'clone', put: false},
      disabled: false,
      chosenClass: "draggable",
    };

    const conditionalContentContainerItemDragOptions = {
      animation: 200,
      group: {name: 'optionalItem', pull: 'clone', put: false},
      disabled: false,
      chosenClass: "draggable",
    };

    const formObjectDragOtions = {
      animation: 200,
      group: {name: 'field', pull: 'clone', put: false},
      disabled: false,
      chosenClass: "draggable",
    };

    const formFieldDragOtions = {
      animation: 200,
      group: {name: 'field', pull: 'clone', put: false},
      disabled: false,
      chosenClass: "draggable",
    };


    const icon = (type: string): string => {
      return settings!.iconSettings.iconMap[type] ?? settings!.iconSettings.defaultIcon;
    }

    const containerIcons = (type: string): string => {
      return settings!.iconSettings.containerIconMap[type] ?? settings!.iconSettings.defaultContainerIcon;
    }

    const handleClone = (item: any): any => {
      return JSON.parse(JSON.stringify(item));
    }

    return {
      secitonDragOptions,
      formFieldDragOtions,
      formObjectDragOtions,
      conditionalContentContainerItemDragOptions,
      conditionalContentContainerDragOptions,
      containerDragOptions,
      settings,
      icon,
      containerIcons,
      handleClone
    }

  }
})

</script>

<style scoped>

.field-divider {
  border-bottom: #ccc 2px solid;
  margin: 5px 0;
}

.palette {
  text-align: left;
  overflow-y: scroll;
  min-width: 120px;
}

</style>
