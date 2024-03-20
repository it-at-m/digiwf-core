<template>
  <v-dialog
    :key="open"
    :value="open"
    persistent
    width="800"
  >
    <v-card>
      <v-card-title> Ungespeicherte Änderungen </v-card-title>
      <v-card-text>
        Es sind ungespeicherte Änderungen vorhanden. Wollen Sie die Seite
        verlassen?
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          aria-label="Auf Seite verbleiben"
          class="e2e-leave-dialog-btn-no"
          text
          @click="$emit('cancel')"
        >
          Nein
        </v-btn>
        <v-btn
          aria-label="Seite verlassen"
          class="e2e-leave-dialog-btn-yes"
          color="primary"
          @click="$emit('submit')"
        >
          Ja
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { PropType } from "vue";

export default {
  props: {
    open: {
      type: Boolean,
      required: true,
    },
  },
  emits: {
    cancel: Function as PropType<() => void>,
    submit: Function as PropType<() => void>,
  },
};
</script>
