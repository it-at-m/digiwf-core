<template>
  <c-container fluid>
    <service-instance-card />
  </c-container>
</template>

<script setup lang="ts">
import { CContainer } from "@coreui/vue";

import ServiceInstanceCard from "@/components/ServiceInstanceCard.vue";
import { useAccessToken } from "@/composables/useAccessToken";
import {
  ACCESS_TOKEN_EVENT_NAME,
  DIGIWF_BASE_URL_DEFAULT,
} from "@/util/constants";
import { useAPI } from "@/composables/useAPI";
import { toRefs } from "vue";
import { useProvideBaseURL } from "@/composables/useBaseURL";

const props = withDefaults(
  defineProps<{
    digiWfBaseUrl?: string;
    accessTokenEventName?: string;
  }>(),
  {
    digiWfBaseUrl: DIGIWF_BASE_URL_DEFAULT,
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
