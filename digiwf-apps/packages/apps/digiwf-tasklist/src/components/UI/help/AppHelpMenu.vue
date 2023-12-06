<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        aria-label="Help Icon Button"
        text
        fab
        v-bind="attrs"
        v-on="on"
      >
        <v-icon
          aria-label="Help Icon"
          class="white--text">
          mdi-help-circle
        </v-icon>
      </v-btn>
    </template>
    <v-list>
      <v-list-item @click.stop="changeMode">
        <v-list-item-title>
          <v-icon>mdi-contrast-box</v-icon>
          Hoher Kontrast
        </v-list-item-title>
        <v-switch
          class="ml-2"
          dense
          :aria-label="isHighContrastModeEnabled() ? 'Hohen Kontrast deaktivieren' : 'Hohen Kontrast aktivieren'"
          :value="isHighContrastModeEnabled()"
        >
        </v-switch>
      </v-list-item>
      <v-list-item
        aria-label="Tastaturbedienungsanleitung öffnen"
        @click.stop="$emit('openKeyBindingsDialoge')"
      >
        <v-list-item-title>
          <v-icon>mdi-keyboard</v-icon>
          Anleitung öffnen
        </v-list-item-title>
      </v-list-item>
      <v-list-item
        aria-label="Tastaturbedienungsanleitung öffnen"
        @click.stop="$emit('openKeyBindingsDialoge')"
      >
        <v-list-item-title>
          <v-icon>mdi-file-document</v-icon>
          Barrierefreiheitserklärung
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>


<script lang="ts">
import {defineComponent} from "vue";
import {useTheme} from "../../../plugins/vuetify";
import {useAccessibility} from "../../../store/modules/accessibility";
import HighContrastIcon from "../icons/HighContrastIcon.vue";

export default defineComponent({
  components: {HighContrastIcon},
  emits: ['openKeyBindingsDialoge', 'closeKeyBindingsDialoge'],
  setup: (components, {emit}) => {
    const theme = useTheme();
    const {isHighContrastModeEnabled, setHighContrastModeEnabled} = useAccessibility();

    emit('openKeyBindingsDialoge');
    emit('closeKeyBindingsDialoge');

    const changeMode = () => {
      const isEnabled = isHighContrastModeEnabled();
      if (isEnabled) {
        theme.deactivateContrastMode();
      } else {
        theme.activateContrastMode();
      }
      setHighContrastModeEnabled(!isEnabled);
    };

    return {
      changeMode,
      isHighContrastModeEnabled
    };
  }
});
</script>
<style scoped>

</style>
