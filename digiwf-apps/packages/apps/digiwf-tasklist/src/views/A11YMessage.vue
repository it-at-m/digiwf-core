<template>
  <app-view-layout>
    <v-flex>
      <div
        ref="message"
        tabindex="0"
      >
        <h1 class="mb-5">
          Mitteilung
        </h1>
        <p>
          {{ messageText }}
        </p>
      </div>
      <v-btn
        aria-label="Schließen"
        color="primary"
        @click="forwardToTarget"
      >
        Schließen
      </v-btn>
    </v-flex>
  </app-view-layout>
</template>

<script lang="ts" setup>

import {useNotificationContext} from "../middleware/snackbar";
import AppViewLayout from "../components/UI/AppViewLayout.vue";
import {onMounted, ref} from 'vue';

const {messageText, forwardToTarget} = useNotificationContext();

const message = ref();

onMounted(() => {
  message.value.focus();
});

</script>

