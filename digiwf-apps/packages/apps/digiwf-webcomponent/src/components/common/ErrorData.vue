<template>
  <c-list-group-item class="p-3">
    <h5 class="mb-3">
      <strong>Fehler</strong>
    </h5>
    <p class="mb-1">
      Beim Abrufen der {{ name }} ist ein Fehler aufgetreten. Versuchen Sie es
      zu einem späteren Zeitpunkt erneut.
    </p>
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

<style scoped>
.list-group-item {
  --cui-list-group-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-list-group-bg: var(
    --digiwf-webcomponent-color-background,
    var(--digiwf-webcomponent-color-background-default)
  );
}
</style>
