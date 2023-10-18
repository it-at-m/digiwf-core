<script lang="ts">
import {defineComponent} from "vue";
import {useTheme} from "../../plugins/vuetify";
import {useAccessibility} from "../../store/modules/accessibility";

export default defineComponent({
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
  <button
    :aria-label="isHighContrastModeEnabled() ? 'Hohen Kontrast deaktivieren' : 'Hohen Kontrast aktivieren'"
    @click.stop="onClick" >
    {{ isHighContrastModeEnabled() ? "Hohen Kontrast deaktivieren" : "Hohen Kontrast aktivieren" }}
  </button>
</template>

<style scoped>

</style>
