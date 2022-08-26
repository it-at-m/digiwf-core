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
            :value="value"
            :schema="schema"
            :options="{}"
            @input="onFormUpdate"
        />
      </v-form>
    </template>
  </dwf-dialog-list-item>
</template>

<script lang="ts">
import {defineComponent, reactive, ref} from "@vue/composition-api";
import {DwfFormRenderer, FormFieldContainer} from "@muenchen/digiwf-form-renderer";

export default defineComponent({
  components: {DwfFormRenderer},
  props: ['schema', 'value'],
  emits: ['saved'],
  setup(props, {emit, refs, root}) {
    const valid = ref(false);
    const dialog = ref(false);
    let currentValue = reactive<FormFieldContainer>(props.value);

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

    const onFormUpdate = (value: FormFieldContainer) => {
      currentValue = value;
    }

    return {
      currentValue,
      dialog,
      valid,
      saved,
      onSaveForm,
      onCancelForm,
      onFormUpdate
    }
  }

})

</script>
