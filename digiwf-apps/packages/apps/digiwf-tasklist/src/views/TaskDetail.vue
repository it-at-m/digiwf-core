<template>
  <app-view-layout :ref="el">
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
      <v-flex
        v-if="task.links.length > 0"
        style="margin-bottom: 1em"
      >
        <task-links
          :links="task.links || []"
        />
      </v-flex>

      <base-form
        v-if="task.form"
        :is-saving="isSaving"
        :is-completing="isCompleting"
        :has-complete-error="hasCompleteError"
        :has-save-error="hasSaveError"
        class="taskForm"
        :form="task.form"
        :init-model="formFields"
        @model-changed="modelChanged"
        @complete-form="handleCompleteTask"
      />
      <app-json-form
        v-else
        :value="formFields"
        :schema="task.schema"
        @input="modelChanged"
        @complete-form="handleCompleteTask"
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
            :aria-label="
              fab ? 'Weitere Aktionen schließen' : 'Weitere Aktionen öffnen'
            "
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
          @on-click="onSaveTaskClick"
        >
          <v-icon> mdi-content-save</v-icon>
        </loading-fab>

        <loading-fab
          v-if="task?.isCancelable"
          :is-loading="isCancelling"
          :has-error="hasCancelError"
          color="white"
          :button-text="cancelText"
          @on-click="handleCancelTask"
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

    <leave-site-dialog
      :open="saveLeaveDialogOpen"
      @submit="onLeaveDialogSubmit"
      @cancel="onLeaveDialogCancel"
    />
    <task-follow-up-dialog
      :follow-up-date="followUpDate"
      :value="isFollowUpDialogVisible"
      @cancel="closeFollowUp"
      @submit="saveFollowUp"
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

<script lang="ts" setup>
import {onMounted, provide, ref, watch} from "vue";
import {onBeforeRouteLeave, useRouter} from "vue-router/composables";
import {NavigationGuardNext} from "vue-router/types/router";

import BaseForm from "@/components/form/BaseForm.vue";
import TaskFollowUpDialog from "@/components/task/TaskFollowUpDialog.vue";
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import LoadingFab from "@/components/UI/LoadingFab.vue";
import {ApiConfig} from "../api/ApiConfig";
import LeaveSiteDialog from "../components/common/LeaveSiteDialog.vue";
import TaskLinks from "../components/task/links/TaskLinks.vue";
import {
  cancelTask,
  completeTask,
  deferTask,
  downloadPDFFromEngine,
  loadTask,
  saveTask,
} from "../middleware/tasks/taskMiddleware";
import {HumanTaskDetails} from "../middleware/tasks/tasksModels";
import {mergeObjects} from "../utils/mergeObjects";
import {parseQueryParameterInputs} from "../utils/urlQueryForFormFields";
import {validateSchema} from "../utils/validateSchema";

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
const taskId = props.id!;

const task = ref<HumanTaskDetails | null>(null);
const followUpDate = ref("");
const model = ref<any>();

const errorMessage = ref("");
const hasChanges = ref(false);

const isSaving = ref(false);
const hasSaveError = ref(false);
const isCompleting = ref(false);
const hasCompleteError = ref(false);
const isCancelling = ref(false);
const hasCancelError = ref(false);
const cancelText = ref("Aufgabe Abbrechen");

const isDownloading = ref(false);
const hasDownloadError = ref(false);
const hasDownloadButton = ref(false);
const downloadButtonText = ref("Dokument herunterladen");

const isFollowUpDialogVisible = ref(false);

const el = ref<any>(null);

const saveLeaveDialogOpen = ref(false);
const next = ref<NavigationGuardNext | null>(null);
/**
 * toggle for showing fab menu
 */
const fab = ref(false);

provide("formContext", {id: props.id, type: "task"});
provide("apiEndpoint", ApiConfig.base);
provide("taskServiceApiEndpoint", ApiConfig.tasklistBase);
provide("mucsDmsApiEndpoint", ApiConfig.mucsDmsBase);
provide("alwDmsApiEndpoint", ApiConfig.alwDmsBase);

const router = useRouter();

const formFields = ref<any>({});

onBeforeRouteLeave((to, from, nxt) => {
  if (isDirty()) {
    saveLeaveDialogOpen.value = true;
    next.value = nxt;
  } else {
    saveLeaveDialogOpen.value = false;
    nxt();
  }
});

const onLeaveDialogSubmit = () => {
  const nextCallback = next.value;
  if (nextCallback) {
    nextCallback();
  }
};

const onLeaveDialogCancel = () => {
  saveLeaveDialogOpen.value = false;
};

const onInit = () => {
  loadTask(taskId)
    .then(({data, error}) => {
      if (data) {
        task.value = data.task;
        model.value = data.model;
        followUpDate.value = data.followUpDate;
        cancelText.value = data.cancelText;
        hasDownloadButton.value = data.hasDownloadButton;
        downloadButtonText.value = data.downloadButtonText;

        const urlQueryParameter = router.currentRoute.query;

        const inputs = parseQueryParameterInputs(
          urlQueryParameter.inputs as string
        );

        if (data.task.form) {
          formFields.value = mergeObjects(data.task.variables, inputs);
        } else {
          // use potential value of query parameter if variable is undefined or empty
          formFields.value = validateSchema(
            data.task.schema,
            mergeObjects(data.task.variables, inputs)
          );
        }
      }
      if (error) {
        errorMessage.value = error;
      }
      hasChanges.value = false;
    });
};

onMounted(() => {
  // Apply a @click.stop to the .v-speed-dial__list that wraps the default slot
  el.value
    ?.querySelector(".v-speed-dial__list")
    .addEventListener("click", (e: Event) => {
      e.stopPropagation();
    });
});

const handleCompleteTask = (model: any) => {
  isCompleting.value = true;
  completeTask(taskId, model)
    .then((result) => {
      isCompleting.value = false;
      hasCompleteError.value = result.isError;
      errorMessage.value = result.errorMessage || "";
      if (!result.isError) {
        hasChanges.value = false;
        router.push({path: "/task"}); // TODO: copied from old source code. Question is why /task is called (path does not exist). check later
      }
    });
};

const onSaveTaskClick = (): Promise<void> => {
  isSaving.value = true;
  hasSaveError.value = false;

  return saveTask(taskId, model.value).then((result) => {
    isSaving.value = false;
    errorMessage.value = result.errorMessage || "";
    hasSaveError.value = result.isError;
    if (!result.isError) {
      hasChanges.value = false;
    }

    return result.isError ? Promise.reject() : Promise.resolve();
  });
};

const openFollowUp = (): void => {
  isFollowUpDialogVisible.value = true;
  fab.value = false;
};

const closeFollowUp = (): void => {
  isFollowUpDialogVisible.value = false;
};

const switchFab = () => {
  fab.value = !fab.value;
};

const saveFollowUp = (newFollowUpDate: string) => {
  followUpDate.value = newFollowUpDate;
  isFollowUpDialogVisible.value = false;

  (hasChanges.value ? onSaveTaskClick() : Promise.resolve()).then(() => {
    deferTask(taskId, newFollowUpDate).then((result) => {
      errorMessage.value = result.errorMessage || "";
    });
  });
};

const handleCancelTask = () => {
  isCancelling.value = true;
  cancelTask(taskId).then((result) => {
    isCancelling.value = false;
    hasCancelError.value = result.isError;
    errorMessage.value = result.errorMessage || "";
  });
};

const downloadPDF = () => {
  isDownloading.value = true;
  hasDownloadError.value = false;
  downloadPDFFromEngine(taskId).then((result) => {
    errorMessage.value = result.errorMessage || "";
    hasDownloadError.value = result.isError;
  });
};

const modelChanged = (newModel: any) => {
  model.value = newModel;
  hasChanges.value = true;
};

const isDirty = (): boolean => {
  return hasChanges.value;
};

onInit();
</script>
