<template>
  <app-view-layout>
    <task-list
      :tasks="tasks.value"
      view-name="Offene Gruppenaufgaben"
      description="Hier sehen Sie alle Aufgaben Ihrer Gruppe. Klicken Sie auf bearbeiten, um sich eine Aufgabe zu nehmen."
      :is-loading="isLoading"
      :error-message="errorMessage"
      :filter.sync="filter"
      pageId="opengrouptasks"
      @loadTasks="loadTasks(true)"
      @update:filter="onFilterChanged"
    >
      <template #default="props">
        <group-task-item
          :key="props.item.id"
          :task="props.item"
          :search-string="props.item.searchInput"
          @on-edit="assignTask(props.item.id)"
        />
        <hr class="hrDivider">
      </template>
    </task-list>
  </app-view-layout>
</template>

<style scoped>

</style>

<script lang="ts">
import {FetchUtils, HumanTaskRestControllerApiFactory} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../api/ApiConfig";
import {defineComponent, onMounted, reactive, ref} from "vue";
import {useStore} from "../hooks/store";
import {useRoute, useRouter} from "vue-router/composables";


export default defineComponent({
  setup() {
    const tasks = reactive({value: []});
    const isLoading = ref<boolean>(false);
    const errorMessage = ref<string>("");
    const filter = ref<string>("");
    const store = useStore();
    const route = useRoute();
    const router = useRouter();


    const loadFilter = (): void => {
      filter.value = route.query.filter as string ?? "";
      if (!filter.value) {
        filter.value = store.getters["tasks/openGroupTasksFilter"];
        router.replace({query: {filter: filter.value}});
      }
    }

    const assignTask = async (id: string): Promise<void> => {
      try {
        //await TaskService.assignTask(id);
        const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
        await HumanTaskRestControllerApiFactory(cfg).assignTask(id);

        store.dispatch('tasks/getTasks', true);
        store.dispatch('openGroupTasks/getTasks', true);
        errorMessage.value = "";
        router.push({path: '/task/' + id});
      } catch (error) {
        errorMessage.value = 'Die Aufgabe konnte nicht zugewiesen werden.';
      }
    }

    const loadTasks = async (refresh = false): Promise<void> => {
      tasks.value = store.getters['openGroupTasks/tasks'];
      isLoading.value = true;
      const startTime = new Date().getTime();
      try {
        await store.dispatch('openGroupTasks/getTasks', refresh);
        tasks.value = store.getters['openGroupTasks/tasks'];
        errorMessage.value = "";
      } catch (error) {
        errorMessage.value = error.message;
      }
      setTimeout(() => isLoading.value = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
    }

    const onFilterChanged = (filter: string) => {
      router.replace({query: {filter: filter}});
      store.commit('tasks/setOpenGroupTasksFilter', filter);
    }

    onMounted(() => {
      loadTasks();
      loadFilter();
    });

    return {
      isLoading,
      errorMessage,
      tasks,
      filter,
      loadFilter,
      loadTasks,
      assignTask,
      onFilterChanged
    }

  }
});
</script>
