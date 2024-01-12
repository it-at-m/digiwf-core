<template>
  <p>{{ hello }}</p>
</template>

<script setup lang="ts">
import { ref } from "vue";

const hello = ref("Hello World");
</script>
