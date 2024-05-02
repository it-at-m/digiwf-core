<template>
  <c-list-group-item class="p-3">
    <c-placeholder
      :as="headingTag"
      animation="wave"
      class="mb-3 text-title"
    >
      <c-placeholder :xs="6" />
    </c-placeholder>
    <c-placeholder
      as="p"
      animation="wave"
      class="mb-1"
    >
      <c-placeholder :xs="4" />
    </c-placeholder>
    <c-placeholder
      as="p"
      animation="wave"
      class="mb-1"
    >
      <c-placeholder :xs="4" />
    </c-placeholder>
    <c-placeholder
      as="p"
      animation="wave"
      class="mb-0 mt-3"
    >
      <c-placeholder :xs="12" />
    </c-placeholder>
  </c-list-group-item>
</template>

<script setup lang="ts">
import { CListGroupItem, CPlaceholder } from "@coreui/vue";
import { ref } from "vue";

import { useHeadingTag } from "@/composables/useHeadingTag";

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
