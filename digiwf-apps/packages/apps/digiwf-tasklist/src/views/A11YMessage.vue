<script setup lang="ts">

import {useNotificationContext} from "../middleware/snackbar";
import AppViewLayout from "../components/UI/AppViewLayout.vue";
import DwfButton from "../components/common/DwfButton.vue";

const {messageText, forwardToTarget} = useNotificationContext();

</script>

<template>
  <app-view-layout>
    <v-flex>
      <h1 tabindex="0">
        Mitteilung
      </h1>
      <p tabindex="0">
        {{ messageText }}
      </p>
      <DwfButton
        aria-label="weiter"
        @click="forwardToTarget"
      >
        weiter
      </DwfButton>
    </v-flex>
  </app-view-layout>
</template>

<style scoped>

</style>
