<template>
  <widget-card
    :link-path="FRONTEND_INSTANCE_PATH"
    card-title="Neueste Anträge"
    link-text="Alle Anträge ansehen (DigiWF)"
    :loading="loading"
  >
    <service-instance-list-placeholder v-if="loading" />
    <service-instance-list v-else />
  </widget-card>
</template>

<script setup lang="ts">
import { computed } from "vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListPlaceholder from "@/components/placeholders/ServiceInstanceListPlaceholder.vue";
import ServiceInstanceList from "@/components/ServiceInstanceList.vue";
import { useAccessToken } from "@/composables/useAccessToken";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";

const { accessToken } = useAccessToken();

const loading = computed(() => !accessToken.value);
</script>
