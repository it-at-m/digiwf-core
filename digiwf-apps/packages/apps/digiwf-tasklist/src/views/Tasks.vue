<template>
  <app-view-layout>
    <task-list
      :tasks="tasks.value"
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
import {defineComponent, onMounted, reactive, ref, watch} from "vue";
import {useStore} from "../hooks/store";
import {useRoute, useRouter} from "vue-router/composables";

export default defineComponent({
  props: [],
  components: {TaskItem, TaskList, AppToast, AppViewLayout},
  setup() {

    const isLoading = ref<boolean>(false);
    const errorMessage = ref<string>("");
    const filter = ref<string>("");
    const followUp = ref<boolean>(false);
    const tasks = reactive({value: []});
    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    const reloadTasks = (): void => {
      let loadedTasks = store.getters['tasks/tasks'];
      const followUp = store.getters['tasks/followUp'];

      if (!followUp) {
        loadedTasks = loadedTasks.filter((task: HumanTaskTO) => task.followUpDate == '' || new Date().getTime() > new Date(task.followUpDate!).getTime());
      }
      tasks.value = loadedTasks;
    }

    const loadTasks = async (refresh = false): Promise<void> => {
      reloadTasks();
      isLoading.value = true;
      const startTime = new Date().getTime();
      try {
        await store.dispatch('tasks/getTasks', refresh);
        reloadTasks();
        errorMessage.value = "";
      } catch (error) {
        errorMessage.value = error.message;
      }
      setTimeout(() => isLoading.value = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
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
      isLoading,
      errorMessage,
      tasks,
      filter,
      onFilterChanged,
      loadFilter,
      loadTasks,
      reloadTasks
    }

  }
});

</script>
