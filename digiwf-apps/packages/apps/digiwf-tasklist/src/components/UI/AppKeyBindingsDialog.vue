<template>
  <v-dialog
    :key="value"
    :value="value"
    @click:outside="close"
    @keydown.esc="close"
    max-width="600px"
  >
    <v-card>
      <v-card-title>
        <span class="text-h5">Tastaturbedienung</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Fokus vorwärts bewegen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Tabulatortaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Fokus rückwärts bewegen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Tabulatortaste + Umschalttaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Dialogfenster schließen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Escapetaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Tab auswählen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Entertaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Button drücken
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Entertaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Auswahlfelder öffnen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Entertaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Auswahl in Auswahlfeld bestätigen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Entertaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Mehrfachauswahlfeld verlassen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Escapetaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Checkbox aktivieren / deaktivieren
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Leertaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Switch aktivieren / deaktivieren
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Leertaste
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Zahleneingabe
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Tastatureingabe oder Pfeiltasten
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Datumseingabe
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Tastatureingabe oder Pfeiltasten
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Uhrzeiteingabe
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Tastatureingabe oder Pfeiltasten
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Dateieingabe Feld öffnen
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Enter
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              Dateieingabe Feld verlassen ohne Eingabe
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              Escapetaste
            </v-col>
          </v-row>
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

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";

@Component
export default class AppKeyBindingsDialog extends Vue {
  @Prop()
  value!: boolean;

  @Emit("close")
  close(): void {
    //
  }

}
</script>

<style scoped>

</style>
