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
            style="min-height: 400px;"
            :value="{...value, key: fieldKey}"
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
import {computed, defineComponent, reactive, ref} from "@vue/composition-api";

export default defineComponent({
  components: {DwfFormRenderer},
  props: ['schemas', 'genericSchema', 'value', 'fieldKey'],
  emits: ['saved'],
  setup(props, {emit, refs, root}) {
    const valid = ref(false);
    const dialog = ref(false);
    let currentValue = reactive<FormField>({...props.value, key: props.fieldKey});

    const saved = () => {
      emit("saved", currentValue);
    }

    const onSaveForm = () => {
      (refs.form as HTMLFormElement).validate();
      if (valid.value) {
        dialog.value = true;
        root.$nextTick(() => {
          dialog.value = false;
        });
        saved();
      }
    }

    const onCancelForm = () => {
      dialog.value = true;
      root.$nextTick(() => {
        dialog.value = false;
      });
    }

    const onFormUpdate = (value: FormField) => {
      currentValue = value;
    }

    const schema = computed(() => props.schemas[props.value.fieldType.toString()] ?? props.genericSchema);

    return {
      currentValue,
      dialog,
      valid,
      schema,
      saved,
      onSaveForm,
      onCancelForm,
      onFormUpdate
    }
  }
})

</script>
