<template>
  <c-container
    fluid
  >
    <service-instance-card/>
  </c-container>
</template>

<script setup lang="ts">
import { CContainer } from "@coreui/vue";
import { provide, readonly, ref} from "vue";

import { DIGIWF_BASE_URL_INJECT_KEY } from "@/composables/useDigiWFBaseURL";
import { ACCESS_TOKEN_EVENT_NAME, DIGIWF_BASE_URL_DEFAULT } from "@/util/constants";
import { useEventListener } from "@vueuse/core";
import type { AccessTokenLoadedEvent } from "@/types/AccessTokenLoadedEvent";
import ServiceInstanceCard from "@/components/ServiceInstanceCard.vue";
import {ACCESS_TOKEN_INJECT_KEY} from "@/composables/useAccessToken";

const props = withDefaults(
  defineProps<{
    digiWfBaseUrl?: string;
    accessTokenEventName?: string;
  }>(),
  {
    digiWfBaseUrl: DIGIWF_BASE_URL_DEFAULT,
    accessTokenEventName: ACCESS_TOKEN_EVENT_NAME
  }
);

const accessToken = ref("");

useEventListener(document, props.accessTokenEventName, (event: AccessTokenLoadedEvent) => {
  accessToken.value = event.detail.accessToken;
})

provide(DIGIWF_BASE_URL_INJECT_KEY, props.digiWfBaseUrl);
provide(ACCESS_TOKEN_INJECT_KEY, readonly(accessToken))
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
