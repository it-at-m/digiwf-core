<template>
  <app-view-layout>
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex v-if="task !== null">
      <span class="processName grey--text">{{ task.processName }}</span>
      <h1>{{ task.name }}</h1>
      <p>{{ task.description }}</p>
      <base-form
        v-if="task.form"
        :is-saving="isSaving"
        :is-completing="isCompleting"
        :has-complete-error="hasCompleteError"
        :has-save-error="hasSaveError"
        class="taskForm"
        :form="task.form"
        :init-model="task.variables"
        @model-changed="modelChanged"
        @complete-form="completeTask"
      />
      <app-json-form
        v-else
        :value="task.variables"
        :schema="task.jsonSchema"
        @input="modelChanged"
        @complete-form="completeTask"
      />
    </v-flex>
    <v-flex class="buttonWrapper">
      <v-speed-dial
        fab
        fixed
        direction="bottom"
        transition="slide-y-transition"
      >
        <template #activator>
          <v-btn
            color="white"
            fab
            :aria-label="fab ? 'Weitere Aktionen schließen' : 'Weitere Aktionen öffnen' "
            @click="switchFab"
          >
            <v-icon v-if="fab">
              mdi-close
            </v-icon>
            <v-icon v-else>
              mdi-dots-vertical
            </v-icon>
          </v-btn>
        </template>
        <v-tooltip bottom>
          <template #activator="{ on, attrs }">
            <v-btn
              aria-label="Wiedervorlage bearbeiten"
              fab
              color="white"
              v-bind="attrs"
              @click="openFollowUp"
              v-on="on"
            >
              <v-icon> mdi-calendar-arrow-right</v-icon>
            </v-btn>
          </template>
          <span>Wiedervorlage bearbeiten</span>
        </v-tooltip>

        <loading-fab
          :is-loading="isSaving"
          :has-error="hasSaveError"
          color="white"
          button-text="Aufgabe zwischenspeichern"
          @on-click="saveTask"
        >
          <v-icon> mdi-content-save</v-icon>
        </loading-fab>

        <loading-fab
          v-if="isCancelable"
          :is-loading="isCancelling"
          :has-error="hasCancelError"
          color="white"
          :button-text="cancelText"
          @on-click="cancelTask"
        >
          <v-icon> mdi-cancel</v-icon>
        </loading-fab>

        <loading-fab
          v-if="hasDownloadButton"
          :is-loading="isDownloading"
          :has-error="hasDownloadButton"
          color="white"
          :button-text="downloadButtonText"
          @on-click="downloadPDF"
        >
          <v-icon> mdi-download</v-icon>
        </loading-fab>
      </v-speed-dial>
    </v-flex>
    <app-yes-no-dialog
      :dialogtext="saveLeaveDialogText"
      :dialogtitle="saveLeaveDialogTitle"
      :value="saveLeaveDialog"
      @yes="leave"
      @no="cancel"
    />
    <task-follow-up-dialog
      :follow-up-date="followUpDate"
      :value="followUp"
      @cancel="closeFollowUp"
      @save="saveFollowUp"
    />
  </app-view-layout>
</template>


<style scoped>

.taskForm {
  margin-top: 1rem;
}

.processName {
  font-size: 1rem;
  display: flex;
  align-items: center;
  margin-bottom: 0.2rem;
}

.v-btn--floating {
  position: relative;
}

.buttonWrapper {
  position: absolute;
  top: 70px;
  right: 0;
}

@media only screen and (max-width: 1500px) {
  .buttonWrapper {
    right: 6em;
  }
}
</style>

<script lang="ts">

import {Component, Prop, Provide} from "vue-property-decorator";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import router from "../router";
import SaveLeaveMixin from "../mixins/saveLeaveMixin";
import AppYesNoDialog from "@/components/common/AppYesNoDialog.vue";
import TaskFollowUpDialog from "@/components/task/TaskFollowUpDialog.vue";
import LoadingFab from "@/components/UI/LoadingFab.vue";
import {
  DocumentRestControllerApiFactory,
  FetchUtils,
  FollowUpTO,
  HumanTaskDetailTO,
  HumanTaskRestControllerApiFactory,
  SaveTO
} from '@muenchen/digiwf-engine-api-internal';
import {FormContext} from "@muenchen/digiwf-multi-file-input";
import {ApiConfig} from "../api/ApiConfig";
import {cancelTaskInEngine, completeTaskInEngine, loadTasksFromEngine} from "../middleware/tasks/taskMiddleware";


@Component({
  components: {TaskFollowUpDialog, BaseForm, AppToast, TaskForm: BaseForm, AppViewLayout, AppYesNoDialog, LoadingFab}
})
export default class TaskDetail extends SaveLeaveMixin {

  task: HumanTaskDetailTO | null = null;
  followUpDate: string | null = "";
  model: any = null;

  errorMessage = "";
  hasChanges = false;

  isSaving = false;
  hasSaveError = false;
  isCompleting = false;
  hasCompleteError = false;
  isCancelling = false;
  hasCancelError = false;
  isCancelable = false;
  cancelText = "Aufgabe Abbrechen";

  isDownloading = false;
  hasDownloadError = false;
  hasDownloadButton = false;
  downloadButtonText = "Dokument herunterladen";

  followUp = false;

  fab = false;

  @Prop()
  id!: string;

  @Provide('formContext')
  get formContext(): FormContext {
    return {id: this.id, type: "task"}
  };

  @Provide('apiEndpoint')
  apiEndpoint = ApiConfig.base;

  created() {
    console.log("created")
    this.loadTask();
  }

  mounted() {
    // Apply a @click.stop to the .v-speed-dial__list that wraps the default slot
    this.$el
      .querySelector(".v-speed-dial__list")!
      .addEventListener("click", (e) => {
        e.stopPropagation();
      });
  }

  async completeTask(model: any): Promise<void> {
    this.isCompleting = true;
    completeTaskInEngine(this.id, model)
      .then(result => {
        this.isCompleting = false;
        this.hasCompleteError = result.isError;
        this.errorMessage = result.errorMessage || "";
      })
  }

  async saveTask(): Promise<void> {
    this.isSaving = true;
    this.hasSaveError = false;
    let hasError = false;
    const startTime = new Date().getTime();

    const request: SaveTO = {
      taskId: this.id,
      variables: this.model,
    };
    try {
      //await TaskService.saveTask(request);
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).saveTask(request);

      this.errorMessage = "";
      this.hasChanges = false;
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht gespeichert werden.';
      hasError = true;
    }

    setTimeout(() => {
      this.isSaving = false;
      this.hasSaveError = hasError;
    }, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  loadTask() {
    console.log("loadTask")
    loadTasksFromEngine(this.id).then(({data, error}) => {
      console.log("load tasks success: ", {data, error});
      if (!!data) {
        this.task = data.task;
        this.model = data.model;
        this.followUpDate = data.followUpDate;
        this.isCancelable = data.isCancelable;
        this.cancelText = data.cancelText
        this.hasDownloadButton = data.hasDownloadButton;
        this.downloadButtonText = data.downloadButtonText;
      }
      if (!!error) {
        this.errorMessage = error;
      }
    });
  }

  async followUpTask(request: FollowUpTO): Promise<void> {
    try {
      //await TaskService.followUpTask(request);
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).followUpTask(request);

      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht gespeichert werden.';
    }
  }

  openFollowUp(): void {
    this.followUp = true;
    this.fab = false;
  }

  closeFollowUp(): void {
    this.followUp = false;
  }

  switchFab(): void {
    this.fab = !this.fab;
  }

  async saveFollowUp(followUpDate: string): Promise<void> {
    this.followUpDate = followUpDate;
    this.followUp = false;
    if (this.hasChanges) {
      await this.saveTask();
    }

    const request: FollowUpTO = {
      taskId: this.id,
      followUpDate: followUpDate
    };

    await this.followUpTask(request);
    this.$store.dispatch('tasks/getTasks', true);
    router.push({path: '/task'});
  }

  cancelTask() {
    console.log("cancel task...")
    this.isCancelling = true;
    cancelTaskInEngine(this.id).then(result => {
      this.isCancelling = false;
      this.hasCancelError = result.isError;
      this.errorMessage = result.errorMessage || ""
    })
  }

  async downloadPDF(): Promise<void> {
    this.isDownloading = true;
    this.hasDownloadError = false;
    let hasError = false;
    const startTime = new Date().getTime();

    try {
      //const statusDoc = await DocumentService.getStatusDocument(this.id);
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      const res = await DocumentRestControllerApiFactory(cfg).getStatusDokumentForTask(this.id);

      const fileURL = window.URL.createObjectURL(new Blob([this.base64ToArrayBuffer(res.data.data)], {type: 'application/pdf'}));
      const fileLink = document.createElement('a');
      fileLink.href = fileURL;
      fileLink.setAttribute('download', 'statusdokument.pdf');
      document.body.appendChild(fileLink);
      fileLink.click();
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = 'Das Statusdokument konnte nicht erstellt werden.';
      hasError = true;
    }

    setTimeout(() => {
      this.isDownloading = false;
      this.hasDownloadError = hasError;
    }, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  base64ToArrayBuffer(base64: any) {
    const binaryString = window.atob(base64);
    const binaryLen = binaryString.length;
    const bytes = new Uint8Array(binaryLen);
    for (let i = 0; i < binaryLen; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes;
  }

  modelChanged(model: any) {
    this.model = model;
    this.hasChanges = true;
  }

  isDirty(): boolean {
    return this.hasChanges;
  }

}
</script>
