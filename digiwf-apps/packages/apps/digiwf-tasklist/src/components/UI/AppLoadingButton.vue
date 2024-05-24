<template>
  <v-btn
    aria-label="Aufgabe abschließen"
    style="padding-left: 0.1rem; padding-right: 1.2rem"
    :color="color"
    :disabled="isLoading"
    @click="$emit('click')"
  >
    <div class="buttonGroup">
      <v-icon
        v-if="hasError"
        small
        style="margin-right: 0.5rem; width: 0.7rem"
        :color="loadingColor"
      >
        mdi-alert
      </v-icon>
      <v-progress-circular
        v-else
        style="margin-right: 0.5rem; width: 0.7rem"
        :class="loadingClass"
        width="2"
        :color="loadingColor"
        indeterminate
      />
      <slot />
    </div>
  </v-btn>
</template>

<style scoped>
.buttonGroup {
  display: flex;
  justify-content: center;
  align-items: center;
}

.isNotLoading {
  visibility: hidden;
}
</style>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    isLoading: {
      type: Boolean,
      required: false,
      default: undefined,
    },
    hasError: {
      type: Boolean,
      required: false,
      default: undefined,
    },
    buttonText: {
      type: String,
      required: true,
    },
    color: {
      type: String,
      required: false,
      default: undefined,
    },
  },
  emits: ["click"],
  setup: (props) => {
    return {
      loadingClass: () => (props.isLoading ? "" : "isNotLoading"),
      loadingColor: () => (props.color === "primary" ? "white" : "primary"),
    };
  },
});
</script>
