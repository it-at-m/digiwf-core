<template>
  <main>
    <hello-world />
  </main>
</template>

<script setup lang="ts">
import HelloWorld from "@/components/HelloWorld.vue";
</script>

<style>
@import "@/assets/stylesheets/custom.css";

:host {
  font-family:
    Open Sans,
    sans-serif;
}
</style>
