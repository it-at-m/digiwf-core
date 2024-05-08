<template>
  <v-form ref="form">
    <dwf-form-renderer
      :options="{
        locale: 'de',
        readOnly: readonly || false,
        markdownit: { breaks: true },
      }"
      :schema="schema"
      :value="value"
      @input="onInput"
    >
      <template #custom-date-input="context">
        <dwf-date-input v-bind="context"/>
      </template>
      <template #custom-dms-input="context">
        <dwf-dms-input v-bind="context"/>
      </template>
      <template #custom-time-input="context">
        <dwf-time-input v-bind="context"/>
      </template>
      <template #custom-user-input="context">
        <v-user-input v-bind="context"/>
      </template>
      <template #custom-multi-user-input="context">
        <v-multi-user-input v-bind="context"/>
      </template>
      <template #custom-multi-file-input="context">
        <dwf-multi-file-input
          :readonly="readonly"
          v-bind="context"
        />
      </template>
    </dwf-form-renderer>
    <v-flex
      class="d-flex"
      style="width: 100%"
    >
      <v-spacer/>
      <v-btn
        :disabled="isCompleting || readonly"
        :loading="isCompleting"
        class="mt-5 form-submit-button"
        color="primary"
        @click="complete"
      >
        {{ buttonText }}
      </v-btn>
    </v-flex>
  </v-form>
</template>

<script lang="ts">
import {defineComponent, ref, watch} from "vue";
import {useRouter} from "vue-router/composables";

import {filterInputsWithValue} from "../../utils/dataTransformations";

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
    safeValidation: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  emits: [
    "input", // (value: any) => void;
    "complete-form", // (value: any) => void
    "completion-failed", // (value: string) => void
    "reset-safe-validation", // () => void
  ],
  setup: (
    props: {
      value: any;
      schema: any;
      readonly: boolean;
      isCompleting: boolean;
      buttonText: string;
      safeValidation: boolean;
    },
    ctx
  ) => {
    const router = useRouter();

    const currentValue = ref<any>(props.value);

    const form = ref(null);

    const complete = () => {
      if ((form.value as HTMLFormElement).validate()) {
        ctx.emit("complete-form", currentValue.value);
      } else {
        ctx.emit("completion-failed", "Validierung Ihrer Eingaben fehlgeschlagen. Bitte überprüfen Sie diese.");
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

      router.replace({query: newQuery});

      ctx.emit("input", value);
    };

    watch(() => props.safeValidation, safeValidation => {
      if (safeValidation) {
        if (!(form.value as HTMLFormElement).validate()) {
          ctx.emit("completion-failed", "Validierung Ihrer Eingaben fehlgeschlagen. Bitte überprüfen Sie diese.");
        }
        ctx.emit("reset-safe-validation");
      }
    });

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
