<template>
  <c-list-group-item class="p-3">
    <h5 class="mb-3">
      <strong>Keine {{ name }} gefunden</strong>
    </h5>
    <p class="mb-1">Es konnten keine {{ name }} gefunden werden.</p>
  </c-list-group-item>
</template>

<script setup lang="ts">
import { CListGroupItem } from "@coreui/vue";

const props = withDefaults(
  defineProps<{
    name?: string;
  }>(),
  {
    name: "Daten",
  }
);
</script>
