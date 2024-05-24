<template>
  <app-view-layout :ref="el">
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex v-if="task">
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
        :form="task.form"
        :has-complete-error="hasCompleteError"
        :has-save-error="hasSaveError"
        :init-model="formFields"
        :is-completing="isCompleting"
        :is-saving="isSaving"
        class="taskForm"
        @model-changed="modelChanged"
        @complete-form="handleCompleteTask"
        @completion-failed="showError"
      />
      <app-json-form
        v-else
        :is-completing="isCompleting"
        :safe-validation="safeValidation"
        :schema="task.schema"
        :value="formFields"
        @input="modelChanged"
        @complete-form="handleCompleteTask"
        @completion-failed="showError"
        @reset-safe-validation="resetSafeValidation"
      />
    </v-flex>
    <v-flex v-if="isLoading" class="loadingAnimation">
      <v-progress-circular
        :size="50"
        aria-label="Daten werden geladen"
        color="primary"
        indeterminate
        tabindex="0"
      ></v-progress-circular>
    </v-flex>
    <v-flex v-if="task" class="buttonWrapper">
      <v-speed-dial
        direction="bottom"
        fab
        fixed
        transition="slide-y-transition"
      >
        <template #activator>
          <v-btn
            :aria-label="
              fab ? 'Weitere Aktionen schließen' : 'Weitere Aktionen öffnen'
            "
            color="white"
            fab
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
              color="white"
              fab
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
          :has-error="hasSaveError"
          :is-loading="isSaving"
          button-text="Aufgabe zwischenspeichern"
          color="white"
          @on-click="handleSaveTask"
        >
          <v-icon> mdi-content-save</v-icon>
        </loading-fab>

        <loading-fab
          v-if="task?.isCancelable"
          :button-text="cancelText"
          :has-error="hasCancelError"
          :is-loading="isCancelling"
          color="white"
          @on-click="handleCancelTask"
        >
          <v-icon> mdi-cancel</v-icon>
        </loading-fab>

        <loading-fab
          v-if="hasDownloadButton"
          :button-text="downloadButtonText"
          :has-error="hasDownloadButton"
          :is-loading="isDownloading"
          color="white"
          @on-click="downloadPDF"
        >
          <v-icon> mdi-download</v-icon>
        </loading-fab>
      </v-speed-dial>
    </v-flex>

    <leave-site-dialog
      :open="saveLeaveDialogOpen"
      @cancel="onLeaveDialogCancel"
      @submit="onLeaveDialogSubmit"
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

.loadingAnimation {
  display: flex;
  position: absolute;
  justify-content: center;
  top: 70px;
  right: 0;
  left: 0;
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
import {onMounted, provide, ref} from "vue";
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
  downloadPDFFromEngine,
  LoadTaskResultData,
  useCancelTaskMutation,
  useCompleteTaskMutation,
  useDeferTaskMutation,
  useSaveTaskMutation,
  useTaskQuery
} from "../middleware/tasks/taskMiddleware";
import {HumanTaskDetails} from "../middleware/tasks/tasksModels";
import {mergeObjects} from "../utils/mergeObjects";
import {parseQueryParameterInputs} from "../utils/urlQueryForFormFields";
import {validateSchema} from "../utils/validateSchema";
import {MessageType, useNotificationContext} from "@/middleware/snackbar";
import {useAccessibility} from "../store/modules/accessibility";

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

const cancelText = ref("Aufgabe Abbrechen");

const isDownloading = ref(false);
const hasDownloadError = ref(false);
const hasDownloadButton = ref(false);
const downloadButtonText = ref("Dokument herunterladen");

const isFollowUpDialogVisible = ref(false);

const el = ref<any>(null);

const saveLeaveDialogOpen = ref(false);
const next = ref<NavigationGuardNext | null>(null);

const router = useRouter();

const formFields = ref<any>({});

const isLoading = ref(false);

const safeValidation = ref(false);

const {showMessageAndLeavePage} = useNotificationContext();

const a11YNotificationEnabled = useAccessibility().a11YNotificationEnabled;

/**
 * toggle for showing fab menu
 */
const fab = ref(false);

const {
  data: taskLoadingResult,
  error: taskLoadingError,
  refetch: reload
} = useTaskQuery(taskId);

const loadTask = (data: LoadTaskResultData) => {
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
};

/**
 *  mutations:
 */

const {
  mutateAsync: cancelTask,
  isLoading: isCancelling,
  isError: hasCancelError
} = useCancelTaskMutation();

const {
  mutateAsync: deferTask
} = useDeferTaskMutation(taskId);

const {
  mutateAsync: completeTask,
  isLoading: isCompleting,
  isError: hasCompleteError
} = useCompleteTaskMutation(taskId);


const {
  mutateAsync: saveTask,
  isLoading: isSaving,
  isError: hasSaveError
} = useSaveTaskMutation(taskId);

provide("formContext", {id: taskId, type: "task"});
provide("apiEndpoint", ApiConfig.base);
provide("taskServiceApiEndpoint", ApiConfig.tasklistBase);
provide("mucsDmsApiEndpoint", ApiConfig.mucsDmsBase);
provide("alwDmsApiEndpoint", ApiConfig.alwDmsBase);


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

onMounted(() => {
  // Apply a @click.stop to the .v-speed-dial__list that wraps the default slot
  el.value
    ?.querySelector(".v-speed-dial__list")
    .addEventListener("click", (e: Event) => {
      e.stopPropagation();
    });

  isLoading.value = true;

  reload().then(() => {
    if (taskLoadingResult.value) {
      loadTask(taskLoadingResult.value);
    }
    if (taskLoadingError.value) {
      errorMessage.value = taskLoadingError.value;
    }
    isLoading.value = false;
  });


});

const handleCompleteTask = (model: any) => {
  hasChanges.value = false;
  completeTask(model)
    .then(() => {
      errorMessage.value = "";
    })
    .catch(error => {
      errorMessage.value = error;
      hasChanges.value = true;
    });
};

const handleSaveTask = (): Promise<void> => {
  hasChanges.value = false;
  if (!a11YNotificationEnabled()) {
    safeValidation.value = true;
  }
  return saveTask(model.value)
    .then(() => {
      errorMessage.value = "";
      return Promise.resolve();
    })
    .catch(error => {
      errorMessage.value = error;
      hasChanges.value = true;
      return Promise.reject();
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

  (hasChanges.value ? handleSaveTask() : Promise.resolve()).then(() => {
    deferTask(newFollowUpDate)
      .then(() => {
        errorMessage.value = "";
      })
      .catch((error) => {
        errorMessage.value = error;
      });
  });
};

const handleCancelTask = () => {
  isCancelling.value = true;
  cancelTask(taskId).catch((result) => {
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

const resetSafeValidation = () => {
  safeValidation.value = false;
};

const showError = (error: string) => {
  errorMessage.value = error;
  showMessageAndLeavePage(
    error,
    MessageType.ERROR,
    {path: "/task/" + taskId}
  );
};

</script>
