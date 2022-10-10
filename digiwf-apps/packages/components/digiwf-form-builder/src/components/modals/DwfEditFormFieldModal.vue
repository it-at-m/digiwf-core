<template>
  <dwf-dialog-list-item
    cancel-button-text="Cancel"
    save-button-text="Save"
    title="Edit"
    :dialog="dialog"
    @cancel="onCancelForm"
    @save="onSaveForm"
  >
    <template #default>
      <v-form
        ref="form"
        v-model="valid"
      >
        <dwf-form-renderer
          v-if="!dialog"
          style="min-height: 400px;"
          :value="currentValue"
          :schema="schema"
          :options="{}"
          @input="onFormUpdate"
        />
      </v-form>
    </template>
  </dwf-dialog-list-item>
</template>

<script lang="ts">
import {DwfFormRenderer, FormField} from "@muenchen/digiwf-form-renderer";
import {computed, defineComponent, nextTick, ref} from "vue";

export default defineComponent({
  components: {DwfFormRenderer},
  props: ['schemas', 'genericSchema', 'value', 'fieldKey'],
  emits: ['saved'],
  setup(props, {emit}) {
    const valid = ref(false);
    const dialog = ref(false);
    const form = ref(null);
    let currentValue = ref<FormField>({...props.value, key: props.fieldKey});

    const saved = () => {
      emit("saved", currentValue.value);
    }

    const onSaveForm = () => {
      (form.value as HTMLFormElement).validate();
      if (valid.value) {
        dialog.value = true;
        nextTick(() => {
          dialog.value = false;
        });
        saved();
      }
    }

    const onCancelForm = () => {
      dialog.value = true;
      currentValue.value = {...props.value, key: props.fieldKey};
      nextTick(() => {
        dialog.value = false;
      });
    }

    const onFormUpdate = (value: FormField) => {
      currentValue.value = value;
    }

    const schema = computed(() => props.schemas[props.value.fieldType.toString()] ?? props.genericSchema);

    return {
      currentValue,
      dialog,
      valid,
      schema,
      form,
      saved,
      onSaveForm,
      onCancelForm,
      onFormUpdate
    }
  }
})

</script>
