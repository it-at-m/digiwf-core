<template>
  <v-snackbar
    v-model="snackbarVisible"
    :color="getMessageType()"
  >
    <v-icon v-if="messageType === MessageType.SUCCESS">
      mdi-check-circle-outline
    </v-icon>
    <v-icon v-if="messageType === MessageType.ERROR">
      mdi-close-circle-outline
    </v-icon>
    <span>
      {{ messageText }}
    </span>

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

<script lang="ts" setup>

import {MessageType, useNotificationContext} from "../../middleware/snackbar";
import {useAccessibility} from "../../store/modules/accessibility";

const {snackbarVisible, messageText, messageType} = useNotificationContext();

const isHighContrastModeEnabled = useAccessibility().isHighContrastModeEnabled;

const getMessageType = (): string => {
  if (isHighContrastModeEnabled()) {
    return "primary";
  }
  return messageType.value;
};

</script>
