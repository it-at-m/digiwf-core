<template>
  <v-tooltip bottom>
    <template #activator="{ on, attrs }">
      <v-btn
        :aria-label="buttonText"
        fab
        :color="color"
        :disabled="disabled"
        v-bind="attrs"
        @click="$emit('on-click')"
        v-on="on"
      >
        <div class="button-group">
          <v-progress-circular
            v-if="isLoading"
            :class="isLoading ? undefined : 'button-hidden'"
            :color="color === 'secondary' ? 'white' : 'primary'"
            indeterminate
          />
          <slot v-else />
        </div>
      </v-btn>
    </template>
    <span> {{ buttonText }} </span>
  </v-tooltip>
</template>

<style scoped>
.button-group {
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-hidden {
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
    },
    hasError: {
      type: Boolean,
      required: false,
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
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  emits: ["on-click"],
});
</script>
