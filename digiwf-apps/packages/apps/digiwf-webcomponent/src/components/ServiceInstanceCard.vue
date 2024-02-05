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
  <button @click="sendTest">TEST API CODE</button>
</template>

<script setup lang="ts">
import { computed, inject } from "vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListPlaceholder from "@/components/placeholders/ServiceInstanceListPlaceholder.vue";
import ServiceInstanceList from "@/components/ServiceInstanceList.vue";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";
import { SERVICE_INSTANCE_INJECT_KEY } from "@/composables/useAPI";
import { FetchUtils } from "@muenchen/digiwf-engine-api-internal";

const { hasAccessToken } = useHasAccessToken();

const loading = computed(() => !hasAccessToken?.value);

const service = inject(SERVICE_INSTANCE_INJECT_KEY);

const sendTest = () => {
  service!!.value.getAssignedInstances(4, 4, "test", FetchUtils.getPOSTConfig(null));
}
</script>
