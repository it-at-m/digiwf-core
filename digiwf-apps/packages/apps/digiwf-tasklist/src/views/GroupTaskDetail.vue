<template>
  <app-view-layout>
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex v-if="task !== null">
      <!-- header -->
      <v-flex style="justify-content: space-between">
        <v-row>
          <v-col cols="12" sm="6">
            <span class="processName">{{ task.processName }}</span>
            <h1>{{ task.name }}</h1>
            <span>{{ task.description }}</span>
          </v-col>
          <v-col cols="12" sm="6" style="display: flex">
            <v-flex style="align-items: flex-end; justify-content: flex-end; display: flex; padding-bottom: 10pt">
              <v-btn
                color="primary"
                @click="checkTaskAssignment"
              >
                <v-icon left>
                  mdi-pencil
                </v-icon>
                Bearbeiten
              </v-btn>
              <v-btn
                style="margin-left: 5pt"
                color="primary"
                @click="openAssignDialog"
              >
                <v-icon left>
                  mdi-send-outline
                </v-icon>
                Zuweisen
              </v-btn>
            </v-flex>
          </v-col>
        </v-row>
        <base-form
          v-if="task.form"
          :readonly-mode="true"
          class="taskForm"
          :form="task.form"
          :init-model="task.variables"
          :buttons-disabled="true"
        />
        <app-json-form
          v-else
          :readonly="true"
          :value="task.variables"
          :schema="task.schema"
        />
        <app-yes-no-dialog
          dialogtitle="Aufgabenzuweisung"
          :value="showModal"
          @yes="triggerAssignTask"
          @no="showModal = false"
        >
          <div>
            Die Aufgabe ist aktuell folgender Person zugewiesen:
            <h3>{{ task.assigneeFormatted }}</h3>
            <br>
            Wollen Sie die Aufgabe übernehmen?
          </div>
        </app-yes-no-dialog>
        <assign-task-dialog
          v-if="showAssignDialog"
          :open="true"
          :task-name="task.name"
          :task-id="task.id"
          @close="closeAssignDialog"
          @success="handleSuccessfullyAssignment"
        />
      </v-flex>
    </v-flex>

  </app-view-layout>
</template>


<style scoped>

.taskForm {
  margin-top: 1rem;
}

.processName {
  font-style: italic;
  font-size: 1rem;
  font-weight: bold;
  color: rgba(0, 0, 0, 0.54);
  display: flex;
  align-items: center;
  margin-bottom: 0.3rem;
}

.buttonWrapper {
//position: absolute; //top: 50px; //right: 11rem;
}

.assignButton {
  width: 8rem;
//position: fixed;
}

</style>

<script lang="ts">

import {Component, Prop, Provide, Vue} from "vue-property-decorator";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import router from "../router";
import {UserTO} from '@muenchen/digiwf-engine-api-internal';
import {FormContext} from "@muenchen/digiwf-multi-file-input";
import {ApiConfig} from "../api/ApiConfig";
import {assignTask, loadTask} from "../middleware/tasks/taskMiddleware";
import {HumanTaskDetails} from "../middleware/tasks/tasksModels";
import {ApiConfig} from "../api/ApiConfig";
import {UserTO} from "@muenchen/digiwf-engine-api-internal";
import {shouldUseTaskService} from "../utils/featureToggles";
import AssignTaskDialog from "../components/task/AssignTaskDialog.vue";

@Component({
  components: {AssignTaskDialog, BaseForm, AppToast, TaskForm: BaseForm, AppViewLayout}
})
export default class GroupTaskDetail extends Vue {

  task: HumanTaskDetails | null = null;
  isLoading = false;
  errorMessage = "";
  showModal = false;

  showAssignDialog = false;

  @Prop()
  id!: string;

  @Provide('formContext')
  get formContext(): FormContext {
    return {id: this.id, type: "task"};
  }

  @Provide('apiEndpoint')
  apiEndpoint = ApiConfig.base;

  @Provide('taskServiceApiEndpoint')
  taskServiceApiEndpoint = ApiConfig.tasklistBase;

  @Provide('shouldUseTaskService')
  shouldUseTaskService = shouldUseTaskService();

  created() {
    this.isLoading = true;
    loadTask(this.id)
      .then(result => {
        this.isLoading = false;
        if (result.data) {
          this.task = result.data.task;
          this.errorMessage = "";
        }
        if (result.error) {
          this.errorMessage = result.error;
        }
      });
  }

  checkTaskAssignment() {
    loadTask(this.id)
      .then(result => {
        if (result.data?.task?.assigneeId) {
          const currentUser: UserTO = this.$store.getters['user/info'];
          if (this.task?.assigneeId != currentUser.lhmObjectId) {
            this.showModal = true;
            setTimeout(() => this.showModal = false, 10000);
          } else {
            router.push({path: '/task/' + this.id});
          }
        } else {
          this.triggerAssignTask();
        }
      });
  }

  openAssignDialog() {
    this.showAssignDialog = true;
  }

  closeAssignDialog() {
    this.showAssignDialog = false;
  }
  handleSuccessfullyAssignment() {
    router.push("/opengrouptask");
  }
  triggerAssignTask() {
    this.showModal = false;

    assignTask(this.id).then((result) => {
      this.errorMessage = result.isError ? "Die Aufgabe konnte nicht zugewiesen werden." : "";
    });
  }
}
</script>
