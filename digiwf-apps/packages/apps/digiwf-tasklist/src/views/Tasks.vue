<template>
  <app-view-layout>
    <task-list
      view-name="Meine Aufgaben"
      :tasks="data?.content || []"
      :is-loading="isLoading || isRefetching"
      :error-message="errorMessage"
      :filter="filter"
      :tag="tag"
      @changeFilter="onFilterChange"
      @loadTasks="reloadTasks"
      @changeTag="onTagChange"
    >
      <template #default="props">
        <task-item
          :key="props.item.id"
          :task="props.item"
          :search-string="props.item.searchInput"
          @clickTag="onTagChange(props.item.tag)"
        />
        <hr class="hrDivider">
      </template>
    </task-list>
    <div style="margin-left: auto">
      <v-checkbox
        v-model="shouldIgnoreFollowUpTasks"
        label="Wiedervorlage anzeigen"
        hide-details
        dense
        class="followUp"
      />
    </div>
    <AppPaginationFooter
      found-data-text="Aufgaben gefunden"
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
.followUp {
  margin: 0;
}
</style>

<script lang="ts">
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import TaskList from "@/components/task/TaskList.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import {defineComponent, ref, watch} from "vue";
import {useRouter} from "vue-router/composables";
import AppPaginationFooter from "../components/UI/AppPaginationFooter.vue";
import {useMyTasksQuery} from "../middleware/tasks/taskMiddleware";
import {useGetPaginationData} from "../middleware/paginationData";
import {usePageId} from "../middleware/pageId";
import {usePageFilters} from "../store/modules/filters";

export default defineComponent({
  components: {AppPaginationFooter, TaskItem, TaskList, AppViewLayout},
  props: [],
  setup() {
    const router = useRouter();
    const pageId = usePageId();
    const {searchQuery, size, page, setSize, setPage, setSearchQuery, tag, setTag} = useGetPaginationData();

    const {currentSortDirection} = usePageFilters();
    const getFollowOfUrl = (): boolean => router.currentRoute.query?.followUp === "true";
    const shouldIgnoreFollowUpTasks = ref<boolean>(getFollowOfUrl());
    const {isLoading, data, error, refetch, isRefetching} = useMyTasksQuery(page, size, searchQuery, tag,shouldIgnoreFollowUpTasks, currentSortDirection);

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

    watch(shouldIgnoreFollowUpTasks, (followUp) => {
      router.replace({
        query: {
          ...router.currentRoute.query,
          followUp: followUp ? "true" : "false"
        }
      });
      refetch();
    });

    return {
      pageId,
      shouldIgnoreFollowUpTasks,
      isLoading,
      isRefetching,
      errorMessage: error,
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
