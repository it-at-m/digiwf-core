<script lang="ts" setup>

import {useNotificationContext} from "../../middleware/snackbar";

const {snackbarVisible, messageText, messageType} = useNotificationContext();

const getMessageType = (): string => {
  return messageType.value;
};

</script>

<template>
  <v-snackbar
    v-model="snackbarVisible"
    :color="getMessageType()"
  >
    <span>{{ messageText }}</span>

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
