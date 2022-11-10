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
  </app-view-layout>
</template>

<style scoped>
.followUp {
  margin: 0;
}

</style>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator';
import {HumanTaskTO} from '@muenchen/digiwf-engine-api-internal';
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import TaskList from "@/components/task/TaskList.vue";
import TaskItem from "@/components/task/TaskItem.vue";

@Component({
  components: {TaskItem, TaskList, AppToast, AppViewLayout}
})
export default class Tasks extends Vue {

  tasks: HumanTaskTO[] = [];
  isLoading = false;
  filter = "";
  errorMessage = "";
  followUp = false;

  mounted(): void {
    this.loadTasks(false);
    this.followUp = this.$store.getters['tasks/followUp'];
  }

  created(): void {
    this.loadFilter();
  }

  loadFilter(): void {
    this.filter = this.$store.getters["tasks/tasksFilter"];
  }

  async loadTasks(refresh = false): Promise<void> {
    this.reloadTasks();
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('tasks/getTasks', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  @Watch('$store.state.tasks.tasks')
  setTasks(): void {
    this.reloadTasks();
  }

  @Watch("followUp")
  setFollowUp(): void {
    this.$store.dispatch('tasks/setFollowUp', this.followUp);
    this.reloadTasks();
  }

  reloadTasks(): void {
    let tasks = this.$store.getters['tasks/tasks'];
    const followUp = this.$store.getters['tasks/followUp'];

    if (!followUp) {
      tasks = tasks.filter((task: HumanTaskTO) => task.followUpDate == '' || new Date().getTime() > new Date(task.followUpDate!).getTime());
    }
    this.tasks = tasks;
  }

  onFilterChanged(filter: string) {
    this.$store.commit('tasks/setTasksFilter', filter);
  }

}
</script>
