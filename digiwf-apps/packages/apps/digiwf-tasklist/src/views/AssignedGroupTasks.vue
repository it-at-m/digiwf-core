<template>
  <app-view-layout>
    <task-list
      :tasks="tasks.value"
      view-name="Gruppenaufgaben in Bearbeitung"
      description="Hier sehen Sie alle Aufgaben, die in Ihrer Gruppe aktuell bearbeitet werden. Klicken Sie auf übernehmen, um eine Aufgabe zu übernehmen."
      :is-loading="isLoading"
      :error-message="errorMessage"
      :show-assignee="true"
      :filter.sync="filter"
      pageId="assignedgrouptasks"
      @loadTasks="loadTasks(true)"
      @update:filter="onFilterChanged"
    >
      <template #default="props">
        <group-task-item
          :key="props.item.id"
          :task="props.item"
          :show-assignee="true"
          :search-string="props.item.searchInput"
          @on-edit="reassignTask(props.item.id)"
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
        filter.value = store.getters["tasks/assignedGroupTasksFilter"];
        router.replace({query: {filter: filter.value}});
      }
    }

    const reassignTask = async (id: string): Promise<void> => {
      try {
        const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
        await HumanTaskRestControllerApiFactory(cfg).assignTask(id);
        store.dispatch('tasks/getTasks', true);
        store.dispatch('assignedGroupTasks/getTasks', true);
        errorMessage.value = "";
        router.push({path: '/task/' + id});
      } catch (error) {
        errorMessage.value = 'Die Aufgabe konnte nicht zugewiesen werden.';
      }
    }

    const loadTasks = async (refresh = false): Promise<void> => {
      tasks.value = store.getters['assignedGroupTasks/tasks'];
      isLoading.value = true;
      const startTime = new Date().getTime();
      try {
        await store.dispatch('assignedGroupTasks/getTasks', refresh);
        tasks.value = store.getters['assignedGroupTasks/tasks'];
        errorMessage.value = "";
      } catch (error) {
        errorMessage.value = error.message;
      }
      setTimeout(() => isLoading.value = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
    }

    const onFilterChanged = (filter: string) => {
      router.replace({query: {filter: filter}});
      store.commit('tasks/setAssignedGroupTasksFilter', filter);
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
      reassignTask,
      onFilterChanged
    };
  }
});


</script>
