<template>
  <widget-card
    :link-path="FRONTEND_INSTANCE_PATH"
    card-title="Meine Vorgänge"
    link-text="Alle Vorgänge ansehen (DigiWF)"
    :loading="showLoading"
    :page-data="pageData"
    @reload="loadData"
    @changepage="newPage => page = newPage"
  >
    <template #content>
      <c-list-group flush>
        <template v-if="data && data.content">
          <service-instance-list-item
            v-for="serviceInstance in data.content"
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

const page = ref(0);
const pageSize = ref(3); // Maybe later set dynamically or as widget parameter?

const data = ref<PageServiceInstanceTO>();
const totalPages = computed<number | undefined>(() => data.value?.totalPages);

watch(hasAccessToken, (token: boolean) => {
  if(token) loadData();
}, { immediate: true })

// Change to new last page if page size decreased
watch(totalPages, (newTotalPages: number) => {
  if(newTotalPages && (page.value + 1) > newTotalPages) {
    page.value = (newTotalPages - 1);
  }
});

const pageData = computed<PageData | undefined>(() => {
  if (!data.value) return undefined;
  const { totalPages, totalElements } = data;
  return {
    totalPages,
    number: page.value,
    totalElements
  };
});

watch(page, () => {
  loadData();
});

// TODO REPLACE METHOD WITH REAL CALL
const loadData = () => {
  loading.value = true
  setTimeout(() => {
    data.value = PAGE_SERVICE_INSTANCE_DUMMY;
    loading.value = false;
  }, 2000);
}
</script>
