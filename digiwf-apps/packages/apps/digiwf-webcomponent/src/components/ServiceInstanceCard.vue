<template>
  <widget-card
    :link-path="FRONTEND_INSTANCE_PATH"
    card-title="Meine Vorgänge"
    link-text="In DigiWF ansehen"
    :loading="showLoading"
    :error="error"
    :page-data="pageData"
    @reload="loadData"
    @changepage="(newPage) => (page = newPage)"
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
        <service-instance-list-item-placeholder
          v-for="i in pageSize"
          :key="i"
        />
      </c-list-group>
    </template>
  </widget-card>
</template>

<script setup lang="ts">
import type { PageData } from "@/types/PageData";

import { CListGroup } from "@coreui/vue";
import { computed, ref, watch } from "vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListItemPlaceholder from "@/components/placeholders/ServiceInstanceListItemPlaceholder.vue";
import ServiceInstanceListItem from "@/components/ServiceInstanceListItem.vue";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { useGetAssignedProcessInstances } from "@/composables/useGetAssignedProcessInstances";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";

const { call: getAssignedProcessInstances, loading, error, data } = useGetAssignedProcessInstances();

const { hasAccessToken } = useHasAccessToken();

const showLoading = computed(() => !hasAccessToken?.value || loading.value);

const page = ref(0);
const pageSize = ref(3); // Maybe later set dynamically or as widget parameter?

const totalPages = computed(() => data.value?.totalPages);

watch(
  hasAccessToken,
  (token: boolean) => {
    if (token) loadData();
  },
  { immediate: true }
);

// Change to new last page if page size decreased
watch(totalPages, (newTotalPages) => {
  if (newTotalPages && page.value + 1 > newTotalPages) {
    page.value = newTotalPages - 1;
  }
});

const pageData = computed<PageData | undefined>(() => {
  if (!data.value) return undefined;
  const { totalPages, totalElements } = data.value;
  return {
    totalPages,
    number: page.value,
    totalElements,
  };
});

watch(page, () => {
  loadData();
});

const loadData = () => {
  getAssignedProcessInstances(page.value, pageSize.value);
};
</script>
