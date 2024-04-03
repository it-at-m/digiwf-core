<script setup lang="ts">

import {useSnackbarContext} from "../../middleware/snackbar";

const {messages} = useSnackbarContext();

</script>

<template>

  <section>
    <h3
      aria-label="Mitteilungen"
        tabindex="0"
    >
      Mitteilungen
    </h3>


    <template v-for="message in messages">
      <div :key="message.time.toISO()" tabindex="0">
        <p>{{ message.message }}</p>
        <p>{{ message.time }}</p>
      </div>
    </template>
  </section>

</template>

<style scoped>

</style>
