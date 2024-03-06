<template>
  <c-container fluid>
    <service-instance-card />
  </c-container>
</template>

<script setup lang="ts">
import { CContainer } from "@coreui/vue";
import { defineProps, toRefs, withDefaults } from "vue";

import ServiceInstanceCard from "@/components/ServiceInstanceCard.vue";
import { useAccessToken } from "@/composables/useAccessToken";
import { useAPI } from "@/composables/useAPI";
import { useProvideBaseURL } from "@/composables/useBaseURL";
import { ACCESS_TOKEN_EVENT_NAME } from "@/util/constants";

const props = withDefaults(
  defineProps<{
    digiWfBaseUrl: string;
    accessTokenEventName?: string;
  }>(),
  {
    accessTokenEventName: ACCESS_TOKEN_EVENT_NAME,
  }
);
const { digiWfBaseUrl, accessTokenEventName } = toRefs(props);
const { accessToken } = useAccessToken(accessTokenEventName);

useProvideBaseURL(digiWfBaseUrl);
useAPI(digiWfBaseUrl, accessToken);
</script>

<style lang="scss">
@import "@/assets/coreui";
@import "@/assets/default.css";

:root,
:host {
  @extend :root;
  font-family: var(
    --lhm-widget-font-family,
    var(--lhm-widget-font-family-default)
  );
}
</style>

<style scoped>
.container-fluid {
  padding-left: 0px;
  padding-right: 0px;
}
</style>
