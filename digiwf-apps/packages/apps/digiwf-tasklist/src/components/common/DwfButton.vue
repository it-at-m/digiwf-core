<script setup lang="ts">
import {useAccessibility} from "../../store/modules/accessibility";

defineProps({
  // eslint-disable-next-line vue/prop-name-casing
  "aria-label": {
    type: String,
    required: true
  }
});
defineEmits(["click"]);

const isHighContrastModeEnabled = useAccessibility().isHighContrastModeEnabled;

</script>

<template>
  <v-btn
    :aria-label="$props['aria-label']"
    :class="(isHighContrastModeEnabled()) ? 'high-contrast' : ''"
    text
    color="primary"
    large
    @click="$emit('click')"
  >
    <slot></slot>
  </v-btn>
</template>

<style scoped>
.high-contrast:focus, .high-contrast:hover {
  border: 1px solid;
}
</style>
