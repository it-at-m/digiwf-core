<template>
  <c-list-group-item class="p-3">
    <c-placeholder
      component="h5"
      animation="wave"
      class="mb-3"
    >
      <c-placeholder
        :xs="6"
        size="lg"
      />
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
  --cui-list-group-color: var(--lhm-widget-color-text, var(--lhm-widget-color-text-default));
  --cui-list-group-bg: var(--lhm-widget-color-background, var(--lhm-widget-color-background-default));
}
</style>
