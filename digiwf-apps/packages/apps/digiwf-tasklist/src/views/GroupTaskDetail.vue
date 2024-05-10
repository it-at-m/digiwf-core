<template>
  <app-view-layout>
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
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
    <v-flex v-if="task">
      <!-- header -->
      <v-flex style="justify-content: space-between">
        <v-row>
          <v-col
            cols="12"
            sm="6"
          >
            <span class="processName">{{ task.processName }}</span>
            <h1>{{ task.name }}</h1>
            <span>{{ task.description }}</span>
          </v-col>
          <v-col
            cols="12"
            sm="6"
            style="display: flex"
          >
            <v-flex
              style="
                align-items: flex-end;
                justify-content: flex-end;
                display: flex;
                padding-bottom: 10pt;
              "
            >
              <v-btn
                color="primary"
                @click="checkTaskAssignment"
              >
                <v-icon left> mdi-pencil</v-icon>
                Bearbeiten
              </v-btn>
              <v-btn
                color="primary"
                style="margin-left: 5pt"
                @click="openAssignDialog"
              >
                <v-icon left> mdi-send-outline</v-icon>
                Zuweisen
              </v-btn>
            </v-flex>
          </v-col>
        </v-row>
        <base-form
          v-if="task.form"
          :buttons-disabled="true"
          :form="task.form"
          :init-model="task.variables"
          :readonly-mode="true"
          class="taskForm"
        />
        <app-json-form
          v-else
          :readonly="true"
          :schema="task.schema"
          :value="task.variables"
        />
        <assign-yourself-dialog
          :assignee-formatted="task.assigneeFormatted || 'Unbekannter Nutzer'"
          :open="showModal"
          @cancel="showModal = false"
          @submit="triggerAssignTask"
        />
        <assign-task-dialog
          v-if="showAssignDialog"
          :open="true"
          :task-id="task.id"
          :task-name="task.name"
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

.loadingAnimation {
  display: flex;
  position: absolute;
  justify-content: center;
  top: 70px;
  right: 0;
  left: 0;
}
</style>

<script lang="ts" setup>
import {onMounted, provide, ref} from "vue";
import {useRouter} from "vue-router/composables";

import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {ApiConfig} from "../api/ApiConfig";
import AssignTaskDialog from "../components/task/AssignTaskDialog.vue";
import AssignYourselfDialog from "../components/task/AssignYourselfDialog.vue";
import {useStore} from "../hooks/store";
import {useAssignTaskMutation, useTaskQuery} from "../middleware/tasks/taskMiddleware";
import {useCurrentUserInfo} from "../middleware/user/userMiddleware";
import {HumanTaskDetails} from "../middleware/tasks/tasksModels";

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});


// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
const taskId: string = props.id!;

const showModal = ref(false);
const task = ref<HumanTaskDetails | undefined>(undefined);
const showAssignDialog = ref(false);

const router = useRouter();
const store = useStore();

const isLoading = ref(false);

const errorMessage = ref<string | null>();

const {data: currentUser} = useCurrentUserInfo();
const {data: taskLoadingResult, error, refetch: reload} = useTaskQuery(taskId);

const {
  mutateAsync: assignTask
} = useAssignTaskMutation(taskId);

provide("formContext", {id: taskId, type: "task"});
provide("apiEndpoint", ApiConfig.base);
provide("mucsDmsApiEndpoint", ApiConfig.mucsDmsBase);
provide("alwDmsApiEndpoint", ApiConfig.alwDmsBase);
provide("taskServiceApiEndpoint", ApiConfig.tasklistBase);

onMounted(() => {
  isLoading.value = true;
  reload().then(() => {
    if (taskLoadingResult.value) {
      task.value = taskLoadingResult.value.task;
    }
    isLoading.value = false;
  });
  errorMessage.value = error.value;
});

const checkTaskAssignment = () => {
  if (task.value?.assigneeId) {
    const lhmObjectId = currentUser.value?.lhmObjectId;
    if (task.value?.assigneeId != lhmObjectId) {
      showModal.value = true;
      setTimeout(() => (showModal.value = false), 10000);
    } else {
      router.push({path: "/task/" + taskId});
    }
  } else {
    triggerAssignTask();
  }
};

const openAssignDialog = () => {
  showAssignDialog.value = true;
};

const closeAssignDialog = () => {
  showAssignDialog.value = false;
};

const handleSuccessfullyAssignment = () => {
  router.push("/opengrouptask");
};

const triggerAssignTask = () => {
  showModal.value = false;

  const lhmObjectId = currentUser.value?.lhmObjectId;

  if (!lhmObjectId) {
    errorMessage.value = "Nutzerinformationen konnten nicht abgefragt werden";
    return;
  }
  assignTask(lhmObjectId)
    .then(() => errorMessage.value)
    .catch((error) => {
      errorMessage.value = error;
    });
};

</script>
