<template>
  <v-container class="viewContainer">
    <v-layout column>
      <slot />
    </v-layout>
  </v-container>
</template>

<style scoped>
.viewContainer {
  max-width: 1100px;
  position: relative;
}
</style>
