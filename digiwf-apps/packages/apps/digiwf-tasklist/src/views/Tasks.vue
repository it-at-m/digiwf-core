<template>
  <app-view-layout>
    <task-list
      :tasks="tasks"
      view-name="Meine Aufgaben"
      :is-loading="isLoading"
      :error-message="errorMessage"
      :filter.sync="filter"
      pageId="tasks"
      @loadTasks="loadTasks(true)"
      @update:filter="onFilterChanged"
    >
      <template #default="props">
        <task-item
          :key="props.item.id"
          :task="props.item"
          :search-string="props.item.searchInput"
        />
        <hr class="hrDivider">
      </template>
    </task-list>
    <div style="margin-left: auto">
      <v-checkbox
        v-model="followUp"
        label="Wiedervorlage anzeigen"
        hide-details
        dense
        class="followUp"
      />
    </div>
    <AppPaginationFooter
      found-data-text="Vorgänge gefunden"
      :items-per-page="pagination.pageSize.value"
      :last-page="pagination.lastPage"
      :last-page-button-disabled="pagination.lastPageButtonDisabled"
      :next-page="pagination.nextPage"
      :total-number-of-items="pagination.totalNumberOfElements"
      :next-page-button-disabled="pagination.nextPageButtonDisabled"
      :number-of-pages="pagination.numberOfPages"
      :page="pagination.getCurrentPage()"
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
import {HumanTaskTO} from '@muenchen/digiwf-engine-api-internal';
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import TaskList from "@/components/task/TaskList.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import {defineComponent, onMounted, ref, watch} from "vue";
import {useStore} from "../hooks/store";
import {useRoute, useRouter} from "vue-router/composables";
import AppPaginationFooter from "../components/UI/AppPaginationFooter.vue";
import {useMyTasksQuery} from "../middleware/tasks/taskMiddleware";

export default defineComponent({
  props: [],
  components: {AppPaginationFooter, TaskItem, TaskList, AppToast, AppViewLayout},
  setup() {

    const filter = ref<string>("");
    const followUp = ref<boolean>(false);

    const page = ref<number>(0);
    const size = ref<number>(20);
    const filteredTasks = ref<HumanTaskTO[]>([]);

    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    const {isLoading: isQueryLoading, isError, data, error, refetch} = useMyTasksQuery(page, size);

    const reloadTasks = (): void => {
      console.log("reload tasks")
      refetch().then(() => {
        console.log("data after refetch", data)
        filteredTasks.value = data.value?.content || [];
        // fixme
        // if (!followUp) {
        //   data = loadedTasks.filter((task: HumanTaskTO) => task.followUpDate == '' || new Date().getTime() > new Date(task.followUpDate!).getTime());
        // }
      });
    }

    const setPage = (newPage: number) => page.value = newPage;

    const loadTasks = async (refresh = false): Promise<void> => {
      console.log("load Tasks")
      reloadTasks();
      // isLoading.value = true;
      const startTime = new Date().getTime();
      try {
        // await store.dispatch('tasks/getTasks', refresh);
        reloadTasks();
        // errorMessage.value = "";
      } catch (error: any) {
        // errorMessage.value = error.message;
      }
      setTimeout(() => {
        // isLoading.value = false
      }, Math.max(0, 500 - (new Date().getTime() - startTime)));
    };

    const loadFilter = (): void => {
      filter.value = route.query.filter as string ?? "";
      if (filter.value) {
        filter.value = store.getters["tasks/tasksFilter"];
        router.replace({query: {filter: filter.value}});
      }
    };

    const onFilterChanged = (filter: string) => {
      router.replace({query: {filter: filter}})
      store.commit('tasks/setTasksFilter', filter);
    };
    watch(page, (p) => {
      console.log("watch of page: ", p)
      reloadTasks();
    })

    watch(followUp, (followUp) => {
      store.dispatch('tasks/setFollowUp', followUp);
      reloadTasks();
    });

    onMounted(() => {
      loadTasks(false);
      loadFilter();
      followUp.value = store.getters['tasks/followUp'];
    });

    return {
      followUp,
      isLoading: isQueryLoading,
      errorMessage: error.value,
      tasks: filteredTasks,
      filter,
      onFilterChanged,
      loadFilter,
      loadTasks,
      reloadTasks,
      pagination: {
        numberOfPages: data.value?.totalPages || 1,
        getCurrentPage: () => page.value + 1,
        setPage,
        pageSize: size,
        lastPage: () => {
          console.log("lastPage");
          page.value--;
          if (page.value === 0) {
            return;
          }
          refetch()
        },
        lastPageButtonDisabled: page.value === 1,
        nextPageButtonDisabled: page.value === data.value?.totalPages,
        nextPage: () => {
          console.log("nextpage")
          const totalPages = data.value?.totalPages;
          if (!totalPages || page.value === totalPages - 1) {
            return;
          }
          page.value++
          console.log("page.value: ", page.value)
          refetch();
        },
        totalNumberOfElements: data.value?.totalElements || 0,
        updateItemsPerPage: () => {
          console.log("updateItemsPerPage")
        }
      },
    }
  }
});

</script>
