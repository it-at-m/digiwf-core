<template>
  <v-text-field
    :id="schema.key"
    v-model="dateValue"
    :dense="dense"
    :disabled="readOnly"
    :label="label"
    :outlined="outlined"
    :rules="[validationResult, ...rules]"
    type="date"
    @change="onChange(event)"
    @input="onInput(event)"
  >
    <template #append-outer>
      <v-tooltip v-if="description" :open-on-hover="false" left>
        <template v-slot:activator="{ on }">
          <v-btn icon retain-focus-on-click @blur="on.blur" @click="on.click">
            <v-icon> mdi-information</v-icon>
          </v-btn>
        </template>
        <div class="tooltip">{{ description }}</div>
      </v-tooltip>
    </template>
  </v-text-field>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import {validateDate} from "@/validation/dateValidation";

export default defineComponent({
  props: [
    'value',
    'schema',
    'on'
  ],
  setup({value, schema, on}) {
    const {title: label, readOnly, description} = schema;
    const {dense, outlined} = schema['x-props'];
    const nativeElement = ref<HTMLInputElement>();
    let rules: any[] = [];

    if (!!schema['x-rules']?.includes('required')) {
      rules.push((v: string) => !!v || 'Dieses Feld ist ein Pflichtfeld');
    }

    const dateValue = ref(value);
    const validationResult = ref<string | boolean>(true);

    const clearValueIfEmpty = () => {
      if (dateValue.value === "") {
        dateValue.value = null;
      }
    }

    const onChange = () => {
      clearValueIfEmpty()
      if (!!on?.input) {
        on.input(dateValue.value);
      }
      validationResult.value = validateDate(dateValue.value, nativeElement.value?.validity?.valid);
    }

    const onInput = () => {
      clearValueIfEmpty();
      validationResult.value = validateDate(dateValue.value, nativeElement.value?.validity?.valid);
    }

    clearValueIfEmpty();
    on.input(dateValue.value);

    return {
      label,
      description,
      dense,
      outlined,
      readOnly,
      dateValue,
      validationResult,
      rules,
      onChange,
      onInput,
      nativeElement
    }
  },
  mounted() {
    this.nativeElement = document.getElementById(this.schema.key) as HTMLInputElement;

  }
})
</script>

<style scoped>

</style>
