<template>
  <c-list-group-item
    class="p-3"
  >
    <h6 class="mb-3">
      <strong>Keine {{ name }} gefunden</strong>
    </h6>
    <p class="mb-1">
      Es konnten keine {{ name }} gefunden werden. Versuchen Sie es zu einem späteren Zeitpunkt erneut.
    </p>
  </c-list-group-item>
</template>

<script setup lang="ts">
import { CListGroupItem } from "@coreui/vue";

const props = withDefaults(
  defineProps<{
    name?: string
  }>(),
  {
    name: "Daten"
  }
);
</script>
