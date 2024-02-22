<template>
  <widget-card
    :link-path="FRONTEND_INSTANCE_PATH"
    card-title="Neueste Anträge"
    link-text="Alle Anträge ansehen (DigiWF)"
    :loading="loading"
  >
    <template #content>
      <c-list-group flush>
        <service-instance-list-item
          v-for="serviceInstance in SERVICE_INSTANCES_DUMMIES"
          :key="serviceInstance.id"
          :service-instance="serviceInstance"
        />
      </c-list-group>
    </template>
    <template #placeholder>
      <c-list-group flush>
        <service-instance-list-item-placeholder v-for="i in 3" :key="i"/>
      </c-list-group>
    </template>
  </widget-card>
  <button @click="sendTest">TEST API CODE</button>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { CListGroup } from "@coreui/vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListItem from "@/components/ServiceInstanceListItem.vue";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { useServiceInstanceControllerAPI } from "@/composables/useServiceInstanceControllerAPI";
import SERVICE_INSTANCES_DUMMIES from "@/dev/dummy-data";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";
import ServiceInstanceListItemPlaceholder from "@/components/placeholders/ServiceInstanceListItemPlaceholder.vue";

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
