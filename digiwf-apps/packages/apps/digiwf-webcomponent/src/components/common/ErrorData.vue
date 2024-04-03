<template>
  <c-list-group flush>
    <c-list-group-item class="p-3">
      <h2 class="mb-3">
        <strong>Fehler</strong>
      </h2>
      <p class="mb-1">
        Beim Abrufen der {{ name }} ist ein Fehler aufgetreten. Versuchen Sie es
        zu einem späteren Zeitpunkt erneut.
      </p>
    </c-list-group-item>
  </c-list-group>
</template>

<script setup lang="ts">
import { CListGroup, CListGroupItem } from "@coreui/vue";

withDefaults(
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
p {
  font-size: var(
    --digiwf-webcomponent-font-size-text,
    var(--digiwf-webcomponent-font-size-text-default)
  );
}
h2 {
  font-size: var(
    --digiwf-webcomponent-font-size-title,
    var(--digiwf-webcomponent-font-size-title-default)
  );
}
</style>
