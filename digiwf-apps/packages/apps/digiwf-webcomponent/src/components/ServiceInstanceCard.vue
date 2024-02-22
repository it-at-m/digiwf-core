<template>
  <widget-card
    :link-path="FRONTEND_INSTANCE_PATH"
    card-title="Neueste Anträge"
    link-text="Alle Anträge ansehen (DigiWF)"
    :loading="showLoading"
    :page-data="pageData"
    @reload="reload"
    @changepage="changePage"
  >
    <template #content>
      <c-list-group flush>
        <template v-if="serviceInstanceData &&  serviceInstanceData.content">
          <service-instance-list-item
            v-for="serviceInstance in serviceInstanceData.content"
            :key="serviceInstance.id"
            :service-instance="serviceInstance"
          />
        </template>
      </c-list-group>
    </template>
    <template #placeholder>
      <c-list-group flush>
        <service-instance-list-item-placeholder v-for="i in pageSize" :key="i"/>
      </c-list-group>
    </template>
  </widget-card>
  <button @click="sendTest">TEST API CODE</button>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue";
import { CListGroup } from "@coreui/vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListItem from "@/components/ServiceInstanceListItem.vue";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { useServiceInstanceControllerAPI } from "@/composables/useServiceInstanceControllerAPI";
import PAGE_SERVICE_INSTANCE_DUMMY from "@/dev/dummy-data";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";
import ServiceInstanceListItemPlaceholder from "@/components/placeholders/ServiceInstanceListItemPlaceholder.vue";
import type { PageData } from "@/types/PageData";
import type { PageServiceInstanceTO } from "@muenchen/digiwf-engine-api-internal";

const { callGetAssignedProcessInstances } = useServiceInstanceControllerAPI();

const { hasAccessToken } = useHasAccessToken();
const loading = ref(false);
const showLoading = computed(() => !hasAccessToken?.value || loading.value);

const pageSize = 3;
const page = ref(0);

const serviceInstanceData = ref<PageServiceInstanceTO>(null);

watch(hasAccessToken, (token: boolean) => {
  if(token) {
    // TODO REPLACE WITH REAL CALL;
    getProcessInstances();
  }
})

const pageData = computed<PageData>(() => {
  if (!serviceInstanceData.value) return undefined;
  const { totalPages, totalElements } = serviceInstanceData.value;
  return {
    totalPages,
    number: page.value,
    totalElements
  };
});

const reload = () => {
  // TODO IMPLEMENT
  console.log("TODO IMPLEMENT");
}

const changePage = (newPage: number) => {
  page.value = newPage;
}

watch(page, () => {
  if(hasAccessToken?.value) {
    // TODO REPLACE WITH REAL CALL
    getProcessInstances();
  }
});

// TODO REPLACE METHOD WITH REAL CALL
const getProcessInstances = async () => {
  loading.value = true
  setTimeout(() => {
    serviceInstanceData.value = PAGE_SERVICE_INSTANCE_DUMMY;
    loading.value = false;
  }, 2000);
}

const sendTest = async () => {
  const result = await callGetAssignedProcessInstances(1, 2, "test");
  if (!result) {
    console.log("ERROR MUST HAVE BEEN OCCURED");
  }
};
</script>
