<template>
  <draggable
      :list="properties"
      class="list-group"
      handle=".handle"
      :empty-insert-threshold="500"
      v-bind="dragOptions"
      @change="onDragChanged"
      @start="drag = true"
      @end="drag = false"
  >
    <template v-for="property in properties">
      <dwf-form-field
          v-if="!isObjectType(property) && !isArrayObjectType(property) && !isOptionalContainer(property)"
          :key="property[0]"
          :field-key="property[0]"
          :value="property[1]"
          @input="onFormFieldChanged"
          @remove="onFieldRemoved"
      />
      <dwf-form-optional-object-container
          :key="property[0]"
          v-else-if="isOptionalContainer(property)"
          :field-key="property[0]"
          :value="property[1]"
          @input="onFormFieldChanged"
          @remove="onFieldRemoved">
      </dwf-form-optional-object-container>

      <dwf-form-object
          :key="property[0]"
          v-else-if="isObjectType(property)"
          :field-key="property[0]"
          :value="property[1]"
          @input="onFormFieldChanged"
          @remove="onFieldRemoved"
      />

      <dwf-form-array-object
          :key="property[0]"
          v-else-if="isArrayObjectType(property)"
          :field-key="property[0]"
          :value="property[1]"
          @input="onFormFieldChanged"
          @remove="onFieldRemoved"
      />
    </template>
    <div
        v-if="properties.length < 1"
        slot="header"
        role="group"
        class="field-placeholder"
    >
      insert field
    </div>
  </draggable>
</template>

<script lang="ts">
import {generateUUID} from "@/utils/UUIDGenerator";
import {defineComponent, ref} from "vue";

export default defineComponent({
  props: ['properties', 'dragOptions'],
  emit: ['input'],
  setup(props, {emit}) {

    const show = ref<boolean>(true);

    const input = (value: any) => {
      emit("input", value);
    }

    const isOptionalContainer = (container: any): boolean => {
      return container[1].fieldType === 'optionalContainer';
    }

    const isObjectType = (field: any): boolean => {
      return field[1].fieldType === 'object';
    }

    const isArrayObjectType = (field: any): boolean => {
      return field[1].fieldType === 'arrayObject' &&
          field[1].items && field[1].items.type === 'object';
    }

    const onDragChanged = (event: any): void => {
      if (event && event.added && !event.added.element[1].key) {
        event.added.element[0] = generateUUID();
      }
      const properties: any = {};
      props.properties.forEach((property: any) => properties[property[0]] = property[1]);

      input(properties);
    }

    const onFieldRemoved = (key: string): any => {
      const relevantFields = props.properties.filter((el: any) => el[0] != key);
      const properties: any = {};
      relevantFields.forEach((property: any) => properties[property[0]] = property[1]);
      input(properties);
    }

    const onFormFieldChanged = (update: any) => {
      const properties: any = {};
      for (let i = 0; i < props.properties.length; i++) {
        const property = props.properties[i];
        if (property[0] === update.key) {
          properties[update.newKey] = update.value;
          props.properties[i][1] = update.value;
        } else {
          properties[property[0]] = property[1];
        }
      }
      input(properties);
    }

    return {
      show,
      isObjectType,
      isOptionalContainer,
      isArrayObjectType,
      onFormFieldChanged,
      onFieldRemoved,
      onDragChanged
    }
  }
})

</script>
