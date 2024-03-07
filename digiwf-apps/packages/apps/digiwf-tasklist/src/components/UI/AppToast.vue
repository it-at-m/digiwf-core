<template>
  <v-alert
    prominent
    style="padding: 0.5rem 1rem"
    :type="type"
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
      required: true
    },
    type: {
      type: Object as PropType<AppToastType>,
      required: true
    }
  }
});


// import {Component, Prop, Vue} from "vue-property-decorator";
//
//
//
// @Component
// export default class AppToast extends Vue {
//
//   @Prop()
//   message!: string;
//
//   @Prop()
//   type!: 'error' | 'info'
//
// }
</script>
