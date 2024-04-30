<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        aria-label="Barrierefreiheit und Hilfe Menü mit Entertaste öffnen und mit den Pfeiltasten darin navigieren"
        fab
        text
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
          :aria-label="
            isHighContrastModeEnabled()
              ? 'Hohen Kontrast deaktivieren'
              : 'Hohen Kontrast aktivieren'
          "
          :input-value="isHighContrastModeEnabled()"
          class="ml-2"
          dense
        >
        </v-switch>
      </v-list-item>
      <v-list-item
        class="max-v-list-item-height"
        @click.stop="changeA11YNotificationMode"
      >
        <HighContrastIcon class="mr-2"/>
        barrierefreie Mitteilungen
        <v-switch
          :aria-label="
            a11YNotificationEnabled()
              ? 'Mitteilungen werden barrierefrei angezeigt'
              : 'Mitteilungen werden als Popup angezeigt'
          "
          :input-value="a11YNotificationEnabled()"
          class="ml-2"
          dense
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

import HelpIcon from "@/components/UI/icons/HelpIcon.vue";
import KeyboardAccessibilityIcon from "@/components/UI/icons/KeyboardAccessibilityIcon.vue";
import StatementIcon from "@/components/UI/icons/StatementIcon.vue";
import {useTheme} from "../../../plugins/vuetify";
import {useAccessibility} from "../../../store/modules/accessibility";
import HighContrastIcon from "../icons/HighContrastIcon.vue";

export default defineComponent({
  components: {
    StatementIcon,
    KeyboardAccessibilityIcon,
    HelpIcon,
    HighContrastIcon,
  },
  emits: ["openKeyBindingsDialoge", "closeKeyBindingsDialoge"],
  setup: (components, {emit}) => {
    const theme = useTheme();
    const {isHighContrastModeEnabled, setHighContrastModeEnabled, a11YNotificationEnabled, setA11YNotificationEnabled} =
      useAccessibility();

    emit("openKeyBindingsDialoge");
    emit("closeKeyBindingsDialoge");

    const changeMode = () => {
      const isEnabled = isHighContrastModeEnabled();
      if (isEnabled) {
        theme.deactivateContrastMode();
      } else {
        theme.activateContrastMode();
      }
      setHighContrastModeEnabled(!isEnabled);
    };

    const changeA11YNotificationMode = () => {
      const isEnabled = a11YNotificationEnabled();
      setA11YNotificationEnabled(!isEnabled);
    };

    return {
      changeMode,
      isHighContrastModeEnabled,
      a11YNotificationEnabled,
      changeA11YNotificationMode

    };
  },
});
</script>
<style scoped>
.max-v-list-item-height {
  max-height: 10px !important;
}
</style>
