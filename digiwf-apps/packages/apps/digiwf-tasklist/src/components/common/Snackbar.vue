<script setup lang="ts">

import {useSnackbarContext} from "../../middleware/snackbar";
import {ref, watch} from "vue";

const {snackbarVisible, messageText} = useSnackbarContext();

const elem=ref();

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
