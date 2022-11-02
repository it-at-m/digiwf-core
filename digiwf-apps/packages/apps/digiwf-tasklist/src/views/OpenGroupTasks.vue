<template>
  <app-view-layout>
    <task-list
      :tasks="tasks"
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
export default class OpenGroupTasks extends Vue {

  tasks: HumanTaskTO[] = [];
  isLoading = false;
  filter = "";
  errorMessage = "";

  created(): void {
    this.loadTasks();
    this.loadFilter();
  }

  loadFilter(): void {
    this.filter = this.$store.getters["tasks/openGroupTasksFilter"];
  }

  async assignTask(id: string): Promise<void> {
    try {
      //await TaskService.assignTask(id);
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).assignTask(id);

      this.$store.dispatch('tasks/getTasks', true);
      this.$store.dispatch('openGroupTasks/getTasks', true);
      this.errorMessage = "";
      router.push({path: '/task/' + id});
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht zugewiesen werden.';
    }
  }

  async loadTasks(refresh = false): Promise<void> {
    this.tasks = this.$store.getters['openGroupTasks/tasks'];
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('openGroupTasks/getTasks', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  onFilterChanged(filter: string) {
    this.$store.commit('tasks/setOpenGroupTasksFilter', filter);
  }

  @Watch('$store.state.openGroupTasks.tasks')
  setTasks(): void {
    this.tasks = this.$store.getters['openGroupTasks/tasks'];
  }

}
</script>
