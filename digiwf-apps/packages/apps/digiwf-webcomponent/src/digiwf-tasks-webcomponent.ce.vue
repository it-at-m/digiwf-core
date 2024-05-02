<template>
  <c-container fluid>
    <task-card />
  </c-container>
</template>

<script setup lang="ts">
import type { RootHeadingLevel } from "@/types/RootHeadingLevel";

import { CContainer } from "@coreui/vue";
import { defineProps, toRefs, withDefaults } from "vue";

import TaskCard from "@/components/Task/TaskCard.vue";
import { useTasksAPI } from "@/composables/TasksApi/useTasksAPI";
import { useAccessToken } from "@/composables/useAccessToken";
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
useTasksAPI(baseURL, accessToken);
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
