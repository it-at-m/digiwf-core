<template>
  <v-text-field
    v-model="dateValue"
    type="date"
    :label="label"
    :dense="dense"
    :outlined="outlined"
    :disabled="readOnly"
    :rules="[validationResult, ...rules]"
    @change="onChange(event)"
    @input="onInput(event)"
    :id="schema.key"
  >
    <template #append-outer>
      <v-tooltip v-if="description" left :open-on-hover="false">
        <template v-slot:activator="{ on }">
          <v-btn icon @click="on.click" @blur="on.blur" retain-focus-on-click>
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

    const onChange = () => {
      if (!!on?.input) {
        on.input(dateValue.value);
      }
      validationResult.value = validateDate(dateValue.value, nativeElement.value?.validity?.valid);
    }

    const onInput = () => {
      validationResult.value = validateDate(dateValue.value, nativeElement.value?.validity?.valid);
    }

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
