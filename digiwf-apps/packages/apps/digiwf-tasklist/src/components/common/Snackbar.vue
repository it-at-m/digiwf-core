<script setup lang="ts">

import {useNotificationContext} from "../../middleware/snackbar";
import {ref, watch} from "vue";

const {snackbarVisible, messageText} = useNotificationContext();

const elem=ref(); // FIXME: glaube nicht mehr notwendig, wenn Barrierefreiheit anders gelöst

watch(snackbarVisible, (v) => {
  if(v === true) {
    elem.value?.focus();
  }
});

</script>

<template>
  <v-snackbar
    v-model="snackbarVisible"
  >
    <span ref="elem">{{ messageText }}</span>

    <template v-slot:action="{ attrs }">
      <v-btn
        color="white"
        text
        v-bind="attrs"
        @click="snackbarVisible = false"
      >
        Schließen
      </v-btn>
    </template>
  </v-snackbar>
</template>

<style scoped>

</style>
