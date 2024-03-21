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
import { ACCESS_TOKEN_EVENT_NAME_DEFAULT, MAX_PAGES_VISIBLE_DEFAULT, PAGE_SIZE_DEFAULT } from "@/util/constants";
import { useProvideParameters } from "@/composables/useParameters";

const props = withDefaults(
  defineProps<{
    digiWfBaseUrl: string;
    accessTokenEventName?: string;
    pageSize?: number;
    maxPagesVisible?: number;
  }>(),
  {
    accessTokenEventName: ACCESS_TOKEN_EVENT_NAME_DEFAULT,
    pageSize: PAGE_SIZE_DEFAULT,
    maxPagesVisible: MAX_PAGES_VISIBLE_DEFAULT
  }
);
const { digiWfBaseUrl, accessTokenEventName, pageSize, maxPagesVisible } = toRefs(props);

useProvideBaseURL(digiWfBaseUrl);
useProvideParameters(pageSize, maxPagesVisible);

const { accessToken } = useAccessToken(accessTokenEventName);
useAPI(digiWfBaseUrl, accessToken);
</script>

<style lang="scss">
@import "@/assets/coreui";
@import "@/assets/default.css";

:root,
:host {
  @extend :root;
  font-family: var(
    --digiwf-webcomponent-font-family,
    var(--digiwf-webcomponent-font-family-default)
  );
}
</style>

<style scoped>
.container-fluid {
  padding-left: 0px;
  padding-right: 0px;
}
</style>
