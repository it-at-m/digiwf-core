<template>
  <app-view-layout>
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex v-if="task !== null">
      <span class="processName">{{ task.processName }}</span>
      <h1>{{ task.name }}</h1>
      <span>{{ task.description }}</span>
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
        :schema="task.jsonSchema"
      />
    </v-flex>
    <v-flex
      v-if="task !== null"
      class="buttonWrapper"
    >
      <v-btn
        class="assignButton"
        color="primary"
        @click="checkTaskAssignment"
      >
        <v-icon left>
          mdi-pencil
        </v-icon>
        Bearbeiten
      </v-btn>
    </v-flex>

    <app-yes-no-dialog v-if="task"
      dialogtitle="Aufgabenzuweisung"
      :value="showModal"
      @yes="assignTask"
      @no="showModal = false"
    >
      <div>
        Die Aufgabe ist aktuell folgender Person zugewiesen:
        <h3>{{task.assigneeFormatted}}</h3>
        <br>
        Wollen Sie die Aufgabe übernehmen?
      </div>
    </app-yes-no-dialog>

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
  position: absolute;
  top: 50px;
  right: 11rem;
}

.assignButton {
  width: 8rem;
  position: fixed;
}

</style>

<script lang="ts">

import {Component, Prop, Provide, Vue} from "vue-property-decorator";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import router from "../router";
import {FetchUtils, HumanTaskDetailTO, HumanTaskRestControllerApiFactory} from '@muenchen/digiwf-engine-api-internal';
import {FormContext} from "@muenchen/digiwf-multi-file-input";
import {EngineServiceApiConfig} from "../api/EngineServiceApiConfig";
import {UserTO} from "@muenchen/digiwf-engine-api-internal";

@Component({
  components: {BaseForm, AppToast, TaskForm: BaseForm, AppViewLayout}
})
export default class MyTaskDetail extends Vue {

  task: HumanTaskDetailTO | null = null;
  isLoading = false;
  errorMessage = "";
  showModal = false;

  @Prop()
  id!: string;

  @Provide('formContext')
  get formContext(): FormContext { return {id: this.id, type: "task"}};

  @Provide('apiEndpoint')
  apiEndpoint = EngineServiceApiConfig.base;

  created() {
    this.loadTask();
  }

  async checkTaskAssignment(): Promise<void> {
    await this.loadTask();
    if (this.task?.assignee) {
      const currentUser: UserTO = this.$store.getters['user/info'];
      if (this.task?.assignee != currentUser.lhmObjectId){
        this.showModal = true;
        setTimeout(() => this.showModal = false, 10000);
      }
      else {
        router.push({path: '/task/' + this.id});
      }
    }
    else {
      this.assignTask();
    }
  }

  async assignTask(): Promise<void> {
    this.showModal = false;
    try {

      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).assignTask(this.id);

      this.$store.dispatch('tasks/getTasks', true);
      this.$store.dispatch('openGroupTasks/getTasks', true);
      this.$store.dispatch('assignedGroupTasks/getTasks', true);
      this.errorMessage = "";
      router.push({path: '/task/' + this.id});
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht zugewiesen werden.';
    }
  }

  async loadTask(): Promise<void> {
    const loadingTimeout = setTimeout(() => this.isLoading = true, 500);
    try {
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      cfg.baseOptions.validateStatus = function (status: number) {
        return status >= 200 && status < 500;
      }; // override axios default impl. (holding back http statuses >= 300)
      const res = await HumanTaskRestControllerApiFactory(cfg).getTaskDetail(this.id);
      if (res.status >= 200 && res.status < 300) { // as in axios default impl.
        this.task = res.data;
        this.errorMessage = "";
      } else {
        if (res.status === 404) {
          this.errorMessage = 'Die Aufgabe oder der zugehörige Vorgang wurden bereits abgeschlossen. Die Aufgabe kann daher nicht mehr angezeigt oder bearbeitet werden.';
        } else {
          this.errorMessage = 'Die Aufgabe konnte nicht geladen werden.';
        }
      }
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht geladen werden.';
    }

    clearTimeout(loadingTimeout);
    this.isLoading = false;
  }

}
</script>
