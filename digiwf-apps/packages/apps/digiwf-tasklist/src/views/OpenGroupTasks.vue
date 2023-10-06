<template>
  <app-view-layout>
    <task-list
      view-name="Offene Gruppenaufgaben"
      description="Hier sehen Sie alle Aufgaben Ihrer Gruppe. Klicken Sie auf bearbeiten, um sich eine Aufgabe zu nehmen."
      :tasks="data?.content || []"
      :is-loading="isLoading || isRefetching"
      :errorMessage="errorMessage"
      :filter="filter"
      :tag="tag"
      @loadTasks="reloadTasks"
      @changeFilter="onFilterChange"
      @changeTag="onTagChange"
    >
      <template #default="props">
        <group-task-item
          :key="props.item.id"
          :task="props.item"
          :search-string="props.item.searchInput"
          @edit="assignTask(props.item.id)"
          @clickTag="onTagChange(props.item.tag)"
        />
        <hr class="hrDivider">
      </template>
    </task-list>
    <AppPaginationFooter
      found-data-text="Vorgänge gefunden"
      :size="pagination.size?.value || 20"
      :on-size-change="pagination.onSizeChange"
      :last-page="pagination.lastPage"
      :last-page-button-disabled="pagination.isLastPageButtonDisabled()"
      :next-page="pagination.nextPage"
      :total-number-of-items="data?.totalElements || 0"
      :next-page-button-disabled="pagination.isNextPageButtonDisabled()"
      :number-of-pages="data?.totalPages || 1"
      :page="pagination.getCurrentPageLabel()"
      :update-items-per-page="pagination.updateItemsPerPage"
    />
  </app-view-layout>
</template>

<style scoped>

</style>

<script lang="ts">
import {defineComponent, watch} from "vue";
import {useRouter} from "vue-router/composables";
import {useAssignTaskToCurrentUserMutation, useOpenGroupTasksQuery} from "../middleware/tasks/taskMiddleware";
import {usePageId} from "../middleware/pageId";
import {useGetPaginationData} from "../middleware/paginationData";
import {usePageFilters} from "../store/modules/filters";
import AppPaginationFooter from "../components/UI/AppPaginationFooter.vue";
import GroupTaskItem from "../components/task/GroupTaskItem.vue";
import TaskList from "../components/task/TaskList.vue";
import AppViewLayout from "../components/UI/AppViewLayout.vue";

export default defineComponent({
  components: {AppViewLayout, TaskList, GroupTaskItem, AppPaginationFooter},
  setup() {
    const router = useRouter();
    const pageId = usePageId();
    const {searchQuery, size, page, setSize, setPage, setSearchQuery, tag, setTag} = useGetPaginationData();
    const {currentSortDirection} = usePageFilters();
    const {isLoading, data, error, refetch, isRefetching} = useOpenGroupTasksQuery(page, size, searchQuery, tag, currentSortDirection);
    const assignMutation = useAssignTaskToCurrentUserMutation();
    watch(currentSortDirection, () => {
      refetch();
    });
    watch(page, (newPage) => {
      setPage(newPage);
      refetch();
    });
    watch(size, (newSize) => {
      setSize(newSize);
      refetch();
    });

    const assignTask = async (id: string): Promise<void> => {
      assignMutation.mutateAsync(id).then(() => router.push({path: '/task/' + id}));
    };

    return {
      assignTask,
      pageId,
      isLoading,
      isRefetching,
      errorMessage: error || assignMutation.error,
      data,
      filter: searchQuery,
      tag,
      reloadTasks: refetch,
      pagination: {
        page,
        size,
        onSizeChange: setSize,
        getCurrentPageLabel: () => page.value + 1,
        setPage,
        lastPage: () => {
          if (page.value === 0) {
            return;
          }
          setPage(page.value - 1);
          refetch();
        },
        nextPage: () => {
          const totalPages = data.value?.totalPages;
          if (!totalPages || page.value === totalPages - 1) {
            return;
          }
          setPage(page.value + 1);
          refetch();
        },
        isLastPageButtonDisabled: () => page.value === 0,
        isNextPageButtonDisabled: () => page.value + 1 >= (data.value?.totalPages || 0),
        updateItemsPerPage: setSize
      },
      onFilterChange: (newFilter?: string) => {
        setSearchQuery(newFilter || "");
        refetch();
      },
      onTagChange: (newTag?: string) => {
        setTag(newTag || "");
        refetch();
      },
    };
  }
});
</script>
