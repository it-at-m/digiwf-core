<template>
  <c-list-group flush>
    <c-list-group-item class="p-3">
      <component :is="headingTag" class="mb-3 text-title">
        Fehler
      </component>
      <p class="mb-1">
        Beim Abrufen der {{ name }} ist ein Fehler aufgetreten. Versuchen Sie es
        zu einem späteren Zeitpunkt erneut.
      </p>
    </c-list-group-item>
  </c-list-group>
</template>

<script setup lang="ts">
import { CListGroup, CListGroupItem } from "@coreui/vue";
import { useHeadingTag } from "@/composables/useHeadingTag";
import { ref } from "vue";

withDefaults(
  defineProps<{
    name?: string;
  }>(),
  {
    name: "Daten",
  }
);

const { headingTag } = useHeadingTag(ref(1));
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
.text-title {
  font-size: var(
    --digiwf-webcomponent-font-size-title,
    var(--digiwf-webcomponent-font-size-title-default)
  );
}
</style>
