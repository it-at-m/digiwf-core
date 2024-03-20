<template>
  <v-form ref="form">
    <dwf-form-renderer
      :options="{
        locale: 'de',
        readOnly: readonly || false,
        markdownit: { breaks: true },
      }"
      :value="value"
      :schema="schema"
      @input="onInput"
    >
      <template #custom-date-input="context">
        <dwf-date-input v-bind="context" />
      </template>
      <template #custom-dms-input="context">
        <dwf-dms-input v-bind="context" />
      </template>
      <template #custom-time-input="context">
        <dwf-time-input v-bind="context" />
      </template>
      <template #custom-user-input="context">
        <v-user-input v-bind="context" />
      </template>
      <template #custom-multi-user-input="context">
        <v-multi-user-input v-bind="context" />
      </template>
      <template #custom-multi-file-input="context">
        <dwf-multi-file-input
          v-bind="context"
          :readonly="readonly"
        />
      </template>
    </dwf-form-renderer>
    <v-flex
      class="d-flex"
      style="width: 100%"
    >
      <v-spacer />
      <v-btn
        class="mt-5 form-submit-button"
        color="primary"
        :disabled="isCompleting || readonly"
        @click="complete"
      >
        {{ buttonText }}
      </v-btn>
    </v-flex>
  </v-form>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router/composables";

import { filterInputsWithValue } from "../../utils/dataTransformations";

export default defineComponent({
  props: {
    value: {
      type: Object,
      required: true,
    },
    schema: {
      type: Object,
      required: true,
    },
    readonly: {
      type: Boolean,
      required: false,
      default: false,
    },
    isCompleting: {
      type: Boolean,
      required: false,
      default: false,
    },
    buttonText: {
      type: String,
      required: false,
      default: "Abschließen",
    },
  },
  emits: [
    "input", // (value: any) => void;
    "complete-form", // (value: any) => void
  ],
  setup: (
    props: {
      value: any;
      schema: any;
      readonly: boolean;
      isCompleting: boolean;
      buttonText: string;
    },
    ctx
  ) => {
    const router = useRouter();

    const currentValue = ref<any>(props.value);

    const form = ref(null);

    const complete = () => {
      if ((form.value as HTMLFormElement).validate()) {
        ctx.emit("complete-form", currentValue.value);
      }
    };

    const onInput = (value: any) => {
      currentValue.value = value;

      const filteredValues = filterInputsWithValue(value);
      const newInputsString = JSON.stringify(filteredValues);
      const newQuery = {
        ...router.currentRoute.query,
        inputs: newInputsString,
      };

      router.replace({ query: newQuery });

      ctx.emit("input", value);
    };

    return {
      complete,
      form,
      onInput,
    };
  },
});
</script>

<style scoped>
.form-submit-button:focus,
.form-submit-button:hover {
  opacity: 0.6; /* first fix for increasing contrast. User feedback is required */
}
</style>
