<template>
  <v-alert
    :type="type"
    prominent
    style="padding: 0.5rem 1rem"
  >
    <v-flex class="d-flex align-center justify-space-between">
      {{ message }}
    </v-flex>
  </v-alert>
</template>

<script lang="ts">
import {defineComponent, PropType} from "vue";

export type AppToastType = "error" | "info";

export default defineComponent({
  props: {
    message: {
      type: String,
      required: true,
    },
    type: {
      type: String as PropType<AppToastType>,
      required: true,
    },
  },
});
</script>
