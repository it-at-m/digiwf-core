<template>
  <v-dialog
    :key="open"
    :value="open"
    persistent
    width="800"
  >
    <v-card>
      <v-card-title> Aufgabenzuweisung </v-card-title>
      <v-card-text>
        <div>
          Die Aufgabe ist aktuell folgender Person zugewiesen:
          <h3>{{ assigneeFormatted }}</h3>
          <br />
          Wollen Sie die Aufgabe übernehmen?
        </div>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          aria-label="Aufgabe nicht zuweisen"
          class="e2e-assign-task-to-yourself-btn-no"
          text
          @click="$emit('cancel')"
        >
          Nein
        </v-btn>
        <v-btn
          aria-label="Seite verlassen"
          class="e2e-assign-task-to-yourself-btn-yes"
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
    assigneeFormatted: {
      type: String,
      required: true,
    },
  },
  emits: {
    cancel: Function as PropType<() => void>,
    submit: Function as PropType<() => void>,
  },
};
</script>
