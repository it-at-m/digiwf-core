<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        aria-label="Barrierefreiheit und Hilfe Menü mit Entertaste öffnen und mit den Pfeiltasten darin navigieren"
        text
        fab
        v-bind="attrs"
        v-on="on"
      >
        <HelpIcon/>
      </v-btn>
    </template>
    <v-list>
      <v-list-item
        class="max-v-list-item-height"
        @click.stop="changeMode"
      >
        <HighContrastIcon class="mr-2"/>
        Hoher Kontrast
        <v-switch
          class="ml-2"
          dense
          :aria-label="isHighContrastModeEnabled() ? 'Hohen Kontrast deaktivieren' : 'Hohen Kontrast aktivieren'"
          :value="isHighContrastModeEnabled()"
        >
        </v-switch>
      </v-list-item>
      <v-list-item
        aria-label="Tastaturbedienungsanleitung öffnen und mit Tabulatortaste navigieren"
        class="max-v-list-item-height"
        @click="$emit('openKeyBindingsDialoge')"
      >

        <KeyboardAccessibilityIcon class="mr-2"/>
        Anleitung öffnen
      </v-list-item>
      <v-list-item
        aria-label="Barrierefreiheitserklärung öffnen"
        class="max-v-list-item-height"
        to="/accessibilitystatement"
      >
        <StatementIcon class="mr-2"/>
        Barrierefreiheitserklärung

      </v-list-item>
    </v-list>
  </v-menu>
</template>


<script lang="ts">
import {defineComponent} from "vue";
import {useTheme} from "../../../plugins/vuetify";
import {useAccessibility} from "../../../store/modules/accessibility";
import HighContrastIcon from "../icons/HighContrastIcon.vue";
import HelpIcon from "@/components/UI/icons/HelpIcon.vue";
import KeyboardAccessibilityIcon from "@/components/UI/icons/KeyboardAccessibilityIcon.vue";
import StatementIcon from "@/components/UI/icons/StatementIcon.vue";

export default defineComponent({
  components: {StatementIcon, KeyboardAccessibilityIcon, HelpIcon, HighContrastIcon},
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
.max-v-list-item-height {
  max-height: 10px !important;
}
</style>
