<script lang="ts">
import {defineComponent} from "vue";
import {useTheme} from "../../plugins/vuetify";
import {useAccessibility} from "../../store/modules/accessibility";
import HighContrastIcon from "./icons/HighContrastIcon.vue";

export default defineComponent({
  components: {HighContrastIcon},
  setup: () => {
    const theme = useTheme();
    const {isHighContrastModeEnabled, setHighContrastModeEnabled} = useAccessibility();

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
      onClick: changeMode,
      isHighContrastModeEnabled
    };
  }
});
</script>

<template>
  <v-switch
    :aria-label="isHighContrastModeEnabled() ? 'Hohen Kontrast deaktivieren' : 'Hohen Kontrast aktivieren'"
    :value="isHighContrastModeEnabled()"
    label="Hohen Kontrast"
    @click.stop="onClick"
  >
    <template v-slot:label>
      <high-contrast-icon /> Hoher Kontrast
    </template>
  </v-switch>
</template>

<style scoped>

</style>
