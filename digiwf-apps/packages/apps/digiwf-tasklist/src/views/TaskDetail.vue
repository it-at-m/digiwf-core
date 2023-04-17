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
  CompleteTO,
  DocumentRestControllerApiFactory,
  FetchUtils,
  FollowUpTO,
  HumanTaskDetailTO,
  HumanTaskRestControllerApiFactory,
  SaveTO
} from '@muenchen/digiwf-engine-api-internal';
import {FormContext} from "@muenchen/digiwf-multi-file-input";
import {EngineServiceApiConfig} from "../api/EngineServiceApiConfig";


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
  get formContext(): FormContext { return {id: this.id, type: "task"}};

  @Provide('apiEndpoint')
  apiEndpoint = EngineServiceApiConfig.base;

  created() {
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
    this.hasCompleteError = false;
    let hasError = false;
    const startTime = new Date().getTime();

    const request: CompleteTO = {
      taskId: this.id,
      variables: model
    };
    try {
      //await TaskService.completeTask(request);
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).completeTask(request);

      this.errorMessage = "";
      this.$store.dispatch('tasks/getTasks', true);
      this.hasChanges = false;
      router.push({path: '/task'});
    } catch (error) {
      hasError = true;
      this.errorMessage = 'Die Aufgabe konnte nicht abgeschlossen werden.';
    }

    setTimeout(() => {
      this.isCompleting = false;
      this.hasCompleteError = hasError;
    }, Math.max(0, 500 - (new Date().getTime() - startTime)));
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
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
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

  async loadTask(): Promise<void> {
    try {
      // this.task = await TaskService.getTaskDetail(this.id);
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      cfg.baseOptions.validateStatus = function (status: number) {
        return status >= 200 && status < 500;
      }; // override axios default impl. (holding back http statuses >= 300)
      const res = await HumanTaskRestControllerApiFactory(cfg).getTaskDetail(this.id);
      if (res.status >= 200 && res.status < 300) { // as in axios default impl.
        this.task = res.data;
        this.model = this.task.variables;
        this.followUpDate = this.task.followUpDate!;
        if (this.task.form && this.task.form.buttons) {
          this.isCancelable = this.task.form.buttons.cancel!.showButton || false;
          this.cancelText = this.task.form.buttons.cancel!.buttonText || '';
          this.hasDownloadButton = this.task.form.buttons.statusPdf!.showButton || false;
          this.downloadButtonText = this.task.form.buttons.statusPdf!.buttonText || '';
        } else if (this.task.statusDocument) {
          this.hasDownloadButton = true;
        }
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
  }

  async followUpTask(request: FollowUpTO): Promise<void> {
    try {
      //await TaskService.followUpTask(request);
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
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

  async cancelTask(): Promise<void> {
    this.isCancelling = true;
    this.hasCancelError = false;
    let hasError = false;
    const startTime = new Date().getTime();
    try {
      //await TaskService.cancelTask(this.id);
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await HumanTaskRestControllerApiFactory(cfg).cancelTask(this.id);

      this.errorMessage = "";
      this.$store.dispatch('tasks/getTasks', true);
      router.push({path: '/task'});
    } catch (error) {
      this.errorMessage = 'Die Aufgabe konnte nicht abgebrochen werden.';
      hasError = true;
    }

    setTimeout(() => {
      this.isCancelling = false;
      this.hasCancelError = hasError;
    }, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  async downloadPDF(): Promise<void> {
    this.isDownloading = true;
    this.hasDownloadError = false;
    let hasError = false;
    const startTime = new Date().getTime();

    try {
      //const statusDoc = await DocumentService.getStatusDocument(this.id);
      const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
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
