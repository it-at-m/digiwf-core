<template>
  <c-list-group flush>
    <c-list-group-item class="p-3">
      <dynamic-heading
        :root-offset="1"
        class="mb-3 text-title"
      >
        Fehler
      </dynamic-heading>
      <p class="mb-1">
        Beim Abrufen der {{ name }} ist ein Fehler aufgetreten. Versuchen Sie es
        zu einem späteren Zeitpunkt erneut.
      </p>
    </c-list-group-item>
  </c-list-group>
</template>

<script setup lang="ts">
import { CListGroup, CListGroupItem } from "@coreui/vue";

import DynamicHeading from "@/components/common/DynamicHeading.vue";

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
  font: var(
    --digiwf-webcomponent-font-text,
    var(--digiwf-webcomponent-font-text-default)
  );
}
.text-title {
  font: var(
    --digiwf-webcomponent-font-title,
    var(--digiwf-webcomponent-font-title-default)
  );
}
</style>
