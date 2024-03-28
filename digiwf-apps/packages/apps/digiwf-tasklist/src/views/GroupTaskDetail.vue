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
                style="margin-left: 5pt"
                color="primary"
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
        <assign-yourself-dialog
          :assignee-formatted="task.assigneeFormatted || 'Unbekannter Nutzer'"
          :open="showModal"
          @cancel="showModal = false"
          @submit="triggerAssignTask"
        />
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
</style>

<script lang="ts" setup>
import {UserTO} from "@muenchen/digiwf-engine-api-internal";
import {provide, ref} from "vue";
import {useRouter} from "vue-router/composables";

import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {ApiConfig} from "../api/ApiConfig";
import AssignTaskDialog from "../components/task/AssignTaskDialog.vue";
import AssignYourselfDialog from "../components/task/AssignYourselfDialog.vue";
import {useStore} from "../hooks/store";
import {assignTask, loadTask} from "../middleware/tasks/taskMiddleware";
import {HumanTaskDetails} from "../middleware/tasks/tasksModels";
import {useCurrentUserInfo} from "../middleware/user/userMiddleware";

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});


// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
const taskId: string = props.id!;

const task = ref<HumanTaskDetails | null>(null);
const isLoading = ref(false);
const errorMessage = ref("");
const showModal = ref(false);

const showAssignDialog = ref(false);

const router = useRouter();
const store = useStore();

const {data: currentUser} = useCurrentUserInfo();

provide("formContext", {id: taskId, type: "task"});
provide("apiEndpoint", ApiConfig.base);
provide("mucsDmsApiEndpoint", ApiConfig.mucsDmsBase);
provide("alwDmsApiEndpoint", ApiConfig.alwDmsBase);
provide("taskServiceApiEndpoint", ApiConfig.tasklistBase);

const onInit = () => {
  isLoading.value = true;
  loadTask(taskId).then((result) => {
    isLoading.value = false;
    if (result.data) {
      task.value = result.data.task;
      errorMessage.value = "";
    }
    if (result.error) {
      errorMessage.value = result.error;
    }
  });
};

const checkTaskAssignment = () => {
  loadTask(taskId).then((result) => {
    if (result.data?.task?.assigneeId) {
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
  });
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
  assignTask(taskId, lhmObjectId).then((result) => {
    errorMessage.value = result.isError
      ? "Die Aufgabe konnte nicht zugewiesen werden."
      : "";
  });
};

onInit();
</script>
