<template>
  <widget-card
    :link-path="FRONTEND_INSTANCE_PATH"
    card-title="Neueste Anträge"
    link-text="Alle Anträge ansehen (DigiWF)"
    :loading="loading"
  >
    <template #content>
      <service-instance-list />
    </template>
    <template #placeholder>
      <service-instance-list-placeholder />
    </template>
  </widget-card>
  <button @click="sendTest">TEST API CODE</button>
</template>

<script setup lang="ts">
import { computed } from "vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListPlaceholder from "@/components/placeholders/ServiceInstanceListPlaceholder.vue";
import ServiceInstanceList from "@/components/ServiceInstanceList.vue";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { useServiceInstanceControllerAPI } from "@/composables/useServiceInstanceControllerAPI";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";

const { hasAccessToken } = useHasAccessToken();

const loading = computed(() => !hasAccessToken?.value);

const { callGetAssignedProcessInstances } = useServiceInstanceControllerAPI();

const sendTest = async () => {
  const result = await callGetAssignedProcessInstances(1, 2, "test");
  if (!result) {
    console.log("ERROR MUST HAVE BEEN OCCURED");
  }
};
</script>
