<template>
  <c-container fluid>
    <service-instance-card />
  </c-container>
</template>

<script setup lang="ts">
import type { RootHeadingLevel } from "@/types/RootHeadingLevel";

import { CContainer } from "@coreui/vue";
import { defineProps, toRefs, withDefaults } from "vue";

import ServiceInstanceCard from "@/components/ServiceInstanceCard.vue";
import { useAccessToken } from "@/composables/useAccessToken";
import { useAPI } from "@/composables/useAPI";
import { useBaseURL } from "@/composables/useBaseURL";
import { useProvideParameters } from "@/composables/useParameters";
import {
  ACCESS_TOKEN_EVENT_NAME_DEFAULT,
  MAX_PAGES_VISIBLE_DEFAULT,
  PAGE_SIZE_DEFAULT,
  ROOT_HEADING_LEVEL_DEFAULT,
} from "@/util/constants";

const props = withDefaults(
  defineProps<{
    accessTokenEventName?: string;
    pageSize?: number;
    maxPagesVisible?: number;
    rootHeadingLevel?: RootHeadingLevel;
  }>(),
  {
    accessTokenEventName: ACCESS_TOKEN_EVENT_NAME_DEFAULT,
    pageSize: PAGE_SIZE_DEFAULT,
    maxPagesVisible: MAX_PAGES_VISIBLE_DEFAULT,
    rootHeadingLevel: ROOT_HEADING_LEVEL_DEFAULT,
  }
);
const { accessTokenEventName, pageSize, maxPagesVisible, rootHeadingLevel } =
  toRefs(props);

useProvideParameters(pageSize, maxPagesVisible, rootHeadingLevel);

const { baseURL } = useBaseURL();
const { accessToken } = useAccessToken(accessTokenEventName);
useAPI(baseURL, accessToken);
</script>

<style lang="scss">
@import "@/assets/coreui";
@import "@/assets/default.css";

:root,
:host {
  @extend :root;
}
</style>

<style scoped>
.container-fluid {
  padding-left: 0px;
  padding-right: 0px;
}
</style>
