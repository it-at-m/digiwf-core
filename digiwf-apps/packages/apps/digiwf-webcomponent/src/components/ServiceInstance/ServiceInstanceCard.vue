<template>
  <widget-card
    name="Vorgänge"
    :link-path="TASKLIST_SERVICE_INSTANCE_PATH"
    card-title="Meine Vorgänge"
    link-text="In DigiWF ansehen"
    :loading="showLoading"
    :error="error"
    :page-data="pageData"
    @reload="loadData"
    @changepage="setPage"
  >
    <template #content>
      <c-list-group flush>
        <template v-if="hasContent">
          <service-instance-list-item
            v-for="serviceInstance in data!.content"
            :key="serviceInstance.id"
            :service-instance="serviceInstance"
          />
        </template>
      </c-list-group>
    </template>
    <template #placeholder>
      <c-list-group
        flush
        aria-label="Vorgänge werden geladen"
        aria-busy="true"
      >
        <service-instance-list-item-placeholder
          v-for="i in pageSize"
          :key="i"
        />
      </c-list-group>
    </template>
  </widget-card>
</template>

<script setup lang="ts">
import { CListGroup } from "@coreui/vue";
import { computed, watch } from "vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import ServiceInstanceListItem from "@/components/ServiceInstance/ServiceInstanceListItem.vue";
import ServiceInstanceListItemPlaceholder from "@/components/ServiceInstance/ServiceInstanceListItemPlaceholder.vue";
import { useGetAssignedProcessInstances } from "@/composables/ServiceInstanceControllerApi/useGetAssignedProcessInstances";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { usePagination } from "@/composables/usePagination";
import { useInjectParameters } from "@/composables/useParameters";
import { TASKLIST_SERVICE_INSTANCE_PATH } from "@/util/constants";

const { hasAccessToken } = useHasAccessToken();
const {
  call: getAssignedProcessInstances,
  loading,
  error,
  data,
} = useGetAssignedProcessInstances();
const { pageSize } = useInjectParameters();

const totalPages = computed(() => data.value?.totalPages);
const totalElements = computed(() => data.value?.totalElements);

const { page, pageData, setPage } = usePagination(totalPages, totalElements);

const showLoading = computed(() => !hasAccessToken?.value || loading.value);
const hasContent = computed(() => data.value && data.value.content);

watch(
  hasAccessToken,
  (token: boolean) => {
    if (token) loadData();
  },
  { immediate: true }
);

watch(page, () => {
  loadData();
});

const loadData = () => {
  getAssignedProcessInstances(page.value);
};
</script>

<style scoped>
.list-group {
  --cui-list-group-border-color: var(
    --digiwf-webcomponent-color-separator,
    var(--digiwf-webcomponent-color-separator-default)
  );
}
</style>
