<template>
  <widget-card
    name="Aufgaben"
    :link-path="TASKLIST_MYTASK_PATH"
    card-title="Meine Aufgaben"
    link-text="In DigiWF ansehen"
    :icon-path="mdiCheckboxOutline"
    :loading="showLoading"
    :error="error"
    :page-data="pageData"
    @reload="loadData"
    @changepage="setPage"
  >
    <template #content>
      <c-list-group flush>
        <template v-if="hasContent">
          <task-list-item
            v-for="task in data!.content"
            :key="task.id"
            :task="task"
          />
        </template>
      </c-list-group>
    </template>
    <template #placeholder>
      <c-list-group
        flush
        aria-label="Aufgaben werden geladen"
        aria-busy="true"
      >
        <task-list-item-placeholder
          v-for="i in pageSize"
          :key="i"
        />
      </c-list-group>
    </template>
  </widget-card>
</template>

<script setup lang="ts">
import { CListGroup } from "@coreui/vue";
import { mdiCheckboxOutline } from "@mdi/js";
import { computed, watch } from "vue";

import WidgetCard from "@/components/common/WidgetCard.vue";
import TaskListItem from "@/components/Task/TaskListItem.vue";
import TaskListItemPlaceholder from "@/components/Task/TaskListItemPlaceholder.vue";
import { useGetCurrentUserTasks } from "@/composables/TasksApi/useGetCurrentUserTasks";
import { useHasAccessToken } from "@/composables/useAccessToken";
import { usePagination } from "@/composables/usePagination";
import { useInjectParameters } from "@/composables/useParameters";
import { TASKLIST_MYTASK_PATH } from "@/util/constants";

const { hasAccessToken } = useHasAccessToken();
const {
  call: getCurrentUserTasks,
  loading,
  error,
  data,
} = useGetCurrentUserTasks();
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
  getCurrentUserTasks(page.value);
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
