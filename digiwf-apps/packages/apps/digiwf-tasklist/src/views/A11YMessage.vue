<script lang="ts" setup>

import {useNotificationContext} from "../middleware/snackbar";
import AppViewLayout from "../components/UI/AppViewLayout.vue";
import DwfButton from "../components/common/DwfButton.vue";
import {onMounted, ref} from 'vue';

const {messageText, forwardToTarget} = useNotificationContext();

const message = ref();

onMounted(() => {
  message.value.focus();
});

</script>

<template>
  <app-view-layout>
    <v-flex>
      <div
        ref="message"
        tabindex="0"
      >
        <h1>
          Mitteilung
        </h1>
        <p>
          {{ messageText }}
        </p>
      </div>
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
