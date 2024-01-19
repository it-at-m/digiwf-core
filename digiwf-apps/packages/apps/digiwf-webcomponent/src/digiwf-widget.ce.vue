<template>
  <c-container
    fluid
  >
    <service-instance-list />
  </c-container>
</template>

<script setup lang="ts">
import { CContainer } from "@coreui/vue";
import { provide } from "vue";

import ServiceInstanceList from "@/components/ServiceInstanceList.vue";
import { DIGIWF_BASE_URL_INJECT_KEY } from "@/composables/useDigiWFBaseURL";
import { DIGIWF_BASE_URL_DEFAULT } from "@/util/constants";

const props = withDefaults(
  defineProps<{
    digiWfBaseUrl?: string;
  }>(),
  {
    digiWfBaseUrl: DIGIWF_BASE_URL_DEFAULT,
  }
);

provide(DIGIWF_BASE_URL_INJECT_KEY, props.digiWfBaseUrl);
</script>

<style lang="scss">
@import "@/assets/coreui";

:root,
:host {
  @extend :root;
}

:host {
  font-family:
    Open Sans,
    sans-serif;
}
</style>
