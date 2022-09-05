<template>
  <v-dialog
      :value="dialog"
      persistent
      max-width="800px"
  >
    <template #activator="{ on, attrs }">
      <v-btn
          link
          v-bind="attrs"
          v-on="on"
      >
        {{ title }}
      </v-btn>
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h5">{{ title }}</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <slot/>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer/>
        <v-btn
            text
            min-width="150"
            outlined
            @click="onCancel"
        >
          {{ cancelButtonText }}
        </v-btn>
        <v-btn
            min-width="150"
            color="primary"
            @click="onSave"
        >
          {{ saveButtonText }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">


import {defineComponent} from "vue";

export default defineComponent({
  props: ['dialog', 'title', 'cancelButtonText', 'saveButtonText'],
  emits: ["cancel", "save"],
  setup(props, {emit,}) {

    const onCancel = () => {
      emit("cancel");
    }

    const onSave = () => {
      emit("save");
    }

    return {
      onSave,
      onCancel
    }
  }

})

</script>

