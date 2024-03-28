<template>
  <c-list-group-item class="p-3">
    <c-placeholder
      component="h2"
      animation="wave"
      class="mb-3"
    >
      <c-placeholder :xs="6" />
    </c-placeholder>
    <c-placeholder
      component="p"
      animation="wave"
      class="mb-1"
    >
      <c-placeholder :xs="4" />
    </c-placeholder>
    <c-placeholder
      component="p"
      animation="wave"
      class="mb-0 mt-3"
    >
      <c-placeholder :xs="12" />
    </c-placeholder>
  </c-list-group-item>
</template>

<script setup lang="ts">
import { CListGroupItem, CPlaceholder } from "@coreui/vue";
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
