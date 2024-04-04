<template>
  <v-dialog
    :key="value"
    :value="value"
    width="500"
    @click:outside="$emit('cancel')"
  >
    <v-card>
      <v-card-title class="headline grey lighten-2">
        Wiedervorlage bearbeiten
      </v-card-title>
      <div class="ma-8">
        <p style="margin-bottom: 1rem">
          Die Aufgabe wird Ihnen zum gewählten Zeitpunkt wieder in "Meine
          Aufgaben" angezeigt.
        </p>
        <v-menu
          v-model="dateSelectionOpen"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="auto"
        >
          <template #activator="{ on, attrs }">
            <v-text-field
              style="
                pointer-events: auto !important;
                background-color: white !important;
              "
              outlined
              clearable
              hide-details
              readonly
              :value="computedDateSelection()"
              label="Wiedervorlage am"
              prepend-icon="mdi-calendar"
              v-bind="attrs"
              @input="updateDateSelection"
              v-on="on"
            />
          </template>
          <v-date-picker
            role="menuitem"
            v-model="dateSelection"
            locale="de-de"
            @input="dateSelectionOpen = false"
          />
        </v-menu>
      </div>
      <v-divider />

      <v-card-actions>
        <v-spacer />
        <v-btn
          text
          @click="$emit('cancel')"
        >
          Abbrechen
        </v-btn>
        <v-btn
          color="primary"
          @click="$emit('submit', dateSelection)"
        >
          Speichern
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { DateTime } from "luxon";
import { defineComponent, ref } from "vue";

export default defineComponent({
  props: {
    value: {
      type: Boolean,
      required: true,
    },
    followUpDate: {
      type: String,
      required: false,
      default: "",
    },
  },
  emits: ["cancel", "submit"],
  setup: (props) => {
    const dateSelection = ref<string>(props.followUpDate);
    const dateSelectionOpen = ref<boolean>(false);

    return {
      dateSelection,
      dateSelectionOpen,
      computedDateSelection: () =>
        dateSelection.value
          ? DateTime.fromFormat(dateSelection.value, "yy-MM-dd").toLocaleString(
              DateTime.DATE_SHORT
            )
          : "",
      updateDateSelection: (v: string) => (dateSelection.value = v),
    };
  },
});
</script>
