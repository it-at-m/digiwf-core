<template>
  <c-list-group-item
    as="a"
    :href="frontendURL"
    target="_blank"
    class="p-3"
    :title="newTabText"
    role="link"
    :aria-label="newTabText"
  >
    <dynamic-heading
      :root-offset="1"
      class="mb-1 text-title"
    >
      {{ task.name }}
    </dynamic-heading>
    <p class="mb-3">({{ task.processName }})</p>
    <p class="mb-1">{{ TEXT_STARTED }} am {{ createdDate }}</p>
    <p
      v-if="task.description"
      class="mb-0 mt-3"
    >
      {{ task.description }}
    </p>
  </c-list-group-item>
</template>

<script setup lang="ts">
import type { Task } from "@muenchen/digiwf-task-api-internal";
import type { DeepReadonly } from "vue";

import { CListGroupItem } from "@coreui/vue";
import { useDateFormat } from "@vueuse/core";
import { computed, ref } from "vue";

import DynamicHeading from "@/components/common/DynamicHeading.vue";
import { useBaseURL } from "@/composables/useBaseURL";
import { useNewTabText } from "@/composables/useNewTabText";
import {
  DATE_FORMAT,
  TASKLIST_TASK_PATH,
  TEXT_STARTED,
} from "@/util/constants";

const { baseURL } = useBaseURL();

const props = defineProps<{
  task: DeepReadonly<Task>;
}>();

const { newTabText } = useNewTabText(ref("Aufgabe in DigiWF öffnen"));

const createdDate = useDateFormat(props.task.createTime, DATE_FORMAT);

const frontendURL = computed(() => {
  return `${baseURL!.value}/#/${TASKLIST_TASK_PATH}/${props.task.id}`;
});
</script>

<style scoped>
.list-group-item {
  --cui-list-group-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-list-group-bg: var(
    --digiwf-webcomponent-color-background,
    var(--digiwf-webcomponent-color-background-default)
  );
  --cui-list-group-action-hover-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-list-group-action-hover-bg: var(
    --digiwf-webcomponent-color-background,
    var(--digiwf-webcomponent-color-background-default)
  );
  --cui-list-group-action-active-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-list-group-action-active-bg: var(
    --digiwf-webcomponent-color-background,
    var(--digiwf-webcomponent-color-background-default)
  );
}
p {
  font: var(
    --digiwf-webcomponent-font-text,
    var(--digiwf-webcomponent-font-text-default)
  );
}
.text-title {
  font: var(
    --digiwf-webcomponent-font-title,
    var(--digiwf-webcomponent-font-title-default)
  );
}
</style>
