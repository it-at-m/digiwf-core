<template>
  <v-form ref="form">
    <dwf-form-renderer-frame
      :schema="schema"
      :value="value"
      :readonly="readonly"
    />
  </v-form>
</template>

<script lang="ts">
import { PropType } from "vue";

import DwfFormRendererFrame from "./DwfFormRendererFrame.vue";

export default {
  components: { DwfFormRendererFrame },
  props: {
    value: {
      type: Object,
      required: true,
    },
    schema: {
      type: Object,
      required: true,
    },
    readonly: {
      type: Boolean,
      required: true,
    },
  },
  emits: {
    input: {
      type: Function as PropType<(value: any) => any>,
    },
  },
};
</script>
