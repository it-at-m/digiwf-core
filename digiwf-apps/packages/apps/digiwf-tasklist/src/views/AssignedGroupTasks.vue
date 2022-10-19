<template>
  <app-view-layout>
    <task-list
      :tasks="tasks"
      view-name="Gruppenaufgaben in Bearbeitung"
      description="Hier sehen Sie alle Aufgaben, die in Ihrer Gruppe aktuell bearbeitet werden. Klicken Sie auf übernehmen, um eine Aufgabe zu übernehmen."
      :is-loading="isLoading"
      :error-message="errorMessage"
      :show-assignee="true"
      :filter.sync="filter"
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
import {Component, Vue, Watch} from 'vue-property-decorator';
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import TaskList from "@/components/task/TaskList.vue";
import GroupTaskItem from "@/components/task/GroupTaskItem.vue";
import router from "../router";
import {FetchUtils, HumanTaskRestControllerApiFactory, HumanTaskTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../api/ApiConfig";

@Component({
  components: {GroupTaskItem, TaskList, AppToast, AppViewLayout}
})
export default class AssignedGroupTasks extends Vue {

  tasks: HumanTaskTO[] = [];
  isLoading = false;
  filter = "";
  errorMessage = "";

  created(): void {
    this.loadTasks();
    this.loadFilter();
  }

  loadFilter(): void {
    this.filter = this.$store.getters["tasks/assignedGroupTasksFilter"];
  }

  async reassignTask(id: string): Promise<void> {
    try {
      //await TaskService.assignTask(id);
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).assignTask(id);

      this.$store.dispatch('tasks/getTasks', true);
      this.$store.dispatch('assignedGroupTasks/getTasks', true);
      this.errorMessage = "";
      router.push({path: '/task/' + id});
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht zugewiesen werden.';
    }
  }

  async loadTasks(refresh = false): Promise<void> {
    this.tasks = this.$store.getters['assignedGroupTasks/tasks'];
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('assignedGroupTasks/getTasks', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  onFilterChanged(filter: string) {
    this.$store.commit('tasks/setAssignedGroupTasksFilter', filter);
  }

  @Watch('$store.state.assignedGroupTasks.tasks')
  setTasks(): void {
    this.tasks = this.$store.getters['assignedGroupTasks/tasks'];
  }

}
</script>
