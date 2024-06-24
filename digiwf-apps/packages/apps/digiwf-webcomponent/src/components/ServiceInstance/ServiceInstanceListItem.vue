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
      class="mb-3 text-title"
    >
      {{ serviceInstance.definitionName }}
    </dynamic-heading>
    <p
      v-if="!serviceInstance.endTime"
      class="mb-1"
    >
      {{ TEXT_STARTED }} am {{ createdDate }}
    </p>
    <p
      v-else
      class="mb-1"
    >
      {{ TEXT_FINISHED }} am {{ endedDate }}
    </p>
    <p v-if="!serviceInstance.endTime">Status: {{ serviceInstance.status }}</p>
    <p
      v-if="serviceInstance.description"
      class="mb-0 mt-3"
    >
      {{ serviceInstance.description }}
    </p>
  </c-list-group-item>
</template>

<script setup lang="ts">
import type { ServiceInstanceTO } from "@muenchen/digiwf-engine-api-internal";
import type { DeepReadonly } from "vue";

import { CListGroupItem } from "@coreui/vue";
import { useDateFormat } from "@vueuse/core";
import { computed, ref } from "vue";

import DynamicHeading from "@/components/common/DynamicHeading.vue";
import { useBaseURL } from "@/composables/useBaseURL";
import { useNewTabText } from "@/composables/useNewTabText";
import {
  DATE_FORMAT,
  TASKLIST_SERVICE_INSTANCE_PATH,
  TEXT_FINISHED,
  TEXT_STARTED,
} from "@/util/constants";

const { baseURL } = useBaseURL();

const props = defineProps<{
  serviceInstance: DeepReadonly<ServiceInstanceTO>;
}>();

const { newTabText } = useNewTabText(ref("Vorgang in DigiWF öffnen"));

const createdDate = useDateFormat(props.serviceInstance.startTime, DATE_FORMAT);
const endedDate = useDateFormat(props.serviceInstance.endTime, DATE_FORMAT);

const frontendURL = computed(() => {
  return `${baseURL!.value}/#/${TASKLIST_SERVICE_INSTANCE_PATH}/${
    props.serviceInstance.id
  }`;
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
