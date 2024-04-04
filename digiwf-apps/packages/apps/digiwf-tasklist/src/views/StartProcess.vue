<template>
  <app-view-layout>
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex v-if="process !== null">
      <h1>{{ process.name }}</h1>
      <p>{{ process.description }}</p>
      <base-form
        v-if="process.startForm"
        :has-complete-error="hasCompleteError"
        :is-completing="isCompleting"
        class="startForm"
        :form="process.startForm"
        :init-model="$route.query"
        :show-save-button="false"
        @model-changed="setDirty"
        @complete-form="startProcess"
      />
      <app-json-form
        v-else
        :value="formFields"
        :schema="process.jsonSchema"
        :is-completing="isCompleting"
        @complete-form="startProcess"
        @input="onAppJsonFormInput"
      />
    </v-flex>
    <leave-site-dialog
      :open="saveLeaveDialogOpen"
      @submit="onLeaveDialogSubmit"
      @cancel="onLeaveDialogCancel"
    />
  </app-view-layout>
</template>

<style scoped>
.startForm {
  margin-top: 1rem;
}
</style>

<script lang="ts" setup>
import { ServiceDefinitionDetailTO } from "@muenchen/digiwf-engine-api-internal";
import { JSONSchemaType } from "ajv";
import {provide, ref, defineProps, watch} from "vue";
import { onBeforeRouteLeave, useRouter } from "vue-router/composables";
import { NavigationGuardNext } from "vue-router/types/router";

import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import { ApiConfig } from "../api/ApiConfig";
import { callPostProcessInstance } from "../api/processInstances/processInstancesApiCalls";
import LeaveSiteDialog from "../components/common/LeaveSiteDialog.vue";
import { loadProcess } from "../middleware/processDefinitions/processDefinitionMiddleware";
import { invalidProcessInstances } from "../middleware/processInstances/processInstancesMiddleware";
import { invalidUserTasks } from "../middleware/tasks/taskMiddleware";
import { mergeObjects } from "../utils/mergeObjects";
import { parseQueryParameterInputs } from "../utils/urlQueryForFormFields";
import { JSFValue, validateSchema } from "../utils/validateSchema";

const props = defineProps({
  processKey: {
    type: String,
    required: true,
  },
});

// eslint-disable-next-line
const processKey: string = props.processKey!;


const process = ref<ServiceDefinitionDetailTO | null>(null);
const errorMessage = ref("");
const isCompleting = ref(false);
const hasCompleteError = ref(false);

const isDirty = ref(false);
const saveLeaveDialogOpen = ref(false);
const next = ref<NavigationGuardNext | null>(null);

const initialFormFields = ref<any>({});
const formFields = ref<any>({});

const router = useRouter();


watch(formFields, () => {
  setDirty();
});

provide("formContext", { id: props.processKey, type: "start" });
provide("apiEndpoint", ApiConfig.base);
provide("mucsDmsApiEndpoint", ApiConfig.mucsDmsBase);
provide("alwDmsApiEndpoint", ApiConfig.alwDmsBase);

onBeforeRouteLeave((to, from, nxt) => {
  if (valuesChanged() && isDirty.value) {
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
  const urlQueryParameter = router.currentRoute.query;
  const inputs = parseQueryParameterInputs(urlQueryParameter.inputs as string);

  loadProcess(processKey).then(({ data, error }) => {
    if (error) {
      errorMessage.value = error;
      return;
    }
    if (!data) {
      errorMessage.value = "Der Vorgang konnte nicht geladen werden.";
      return;
    }
    process.value = data;
    // use potential value of query parameter if variable is undefined or empty
    const initValue = validateSchema(
      process.value?.jsonSchema as JSONSchemaType<JSFValue>,
      mergeObjects(process.value?.startForm || {}, inputs)
    );
    initialFormFields.value = initValue;
    formFields.value = initValue;
    isDirty.value = false;
  });
};

const valuesChanged = () => {
  return (
    JSON.stringify(initialFormFields.value) !== JSON.stringify(formFields.value)
  );
};

const startProcess = (model: any) => {
  isCompleting.value = true;
  hasCompleteError.value = false;

  callPostProcessInstance(processKey, model)
    .then(() => {
      errorMessage.value = "";
      isDirty.value = false;
      invalidUserTasks();
      invalidProcessInstances();
      // hier eventuell zum userTask routen
      router.push({ path: "/process" });
    })
    .catch(() => {
      errorMessage.value = "Der Vorgang konnte nicht gestartet werden.";
      hasCompleteError.value = true;
    });
};

const onAppJsonFormInput = (newValue: any) => {
  formFields.value = newValue;
};

const setDirty = () => {
  isDirty.value = true;
};

onInit();
</script>
