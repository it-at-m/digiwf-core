<template>
  <v-dialog
    :key="value"
    :value="value"
    max-width="600px"
    @click:outside="$emit('close')"
    @keydown.esc="$emit('close')"
  >
    <v-card>
      <v-card-title>
        <span class="text-h5">Tastaturbedienung</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <key-binding-line
            key-binding="Tabulatortaste"
            action="Fokus vorwärts bewegen"
          />
          <key-binding-line
            key-binding="Tabulatortaste + Umschalttaste"
            action="Fokus rückwärts bewegen"
          />
          <key-binding-line
            key-binding="Esc-Taste"
            action="Dialogfenster schließen"
          />
          <key-binding-line
            key-binding="Entertaste"
            action="Tab auswählen"
          />
          <key-binding-line
            key-binding="Entertaste"
            action="Button drücken"
          />
          <key-binding-line
            key-binding="Entertaste"
            action="Auswahlfelder öffnen"
          />
          <key-binding-line
            key-binding="Entertaste"
            action="Auswahl in Auswahlfeld bestätigen"
          />
          <key-binding-line
            key-binding="Esc-Taste"
            action="Mehrfachauswahlfeld verlassen"
          />
          <key-binding-line
            key-binding="Leertaste"
            action="Checkbox aktivieren / deaktivieren"
          />
          <key-binding-line
            key-binding="Leertaste"
            action="Switch aktivieren / deaktivieren"
          />
          <key-binding-line
            key-binding="Tastatureingabe oder Pfeiltasten"
            action="Zahleneingabe"
          />
          <key-binding-line
            key-binding="Tastatureingabe oder Pfeiltasten"
            action="Datumseingabe"
          />
          <key-binding-line
            key-binding="Tastatureingabe oder Pfeiltasten"
            action="Uhrzeiteingabe"
          />
          <key-binding-line
            key-binding="Entertaste"
            action="Dateieingabefeld öffnen"
          />
          <key-binding-line
            key-binding="Esc-Taste"
            action="Dateieingabefeld verlassen ohne Eingabe"
          />
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          color="blue darken-1"
          text
          aria-label="Tastaturbedienungsanleitung schließen"
          @click="$emit('close')"
        >
          Schließen
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import KeyBindingLine from "./KeyBindingLine.vue";

defineProps({
  value: {
    type:Boolean,
    required:true
  }
});
defineEmits(["close"]);

</script>

<style scoped>

</style>
