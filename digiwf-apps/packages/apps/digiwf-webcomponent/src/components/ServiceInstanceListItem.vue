<template>
  <c-list-group-item
    component="a"
    :href="frontendURL"
    target="_blank"
    class="p-3"
  >
    <h5 class="mb-3">
      <strong>{{ serviceInstance.definitionName }}</strong>
    </h5>
    <p class="mb-1">Erstellt am {{ createdDate }}</p>
    <p
      v-if="serviceInstance.endTime"
      class="mb-1"
    >
      Abgeschlossen am {{ endedDate }}
    </p>
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

import { CListGroupItem } from "@coreui/vue";
import { useDateFormat } from "@vueuse/core";
import { computed } from "vue";

import { useInjectBaseURL } from "@/composables/useBaseURL";
import { DATE_FORMAT, FRONTEND_INSTANCE_PATH } from "@/util/constants";

const { digiWFBaseURL } = useInjectBaseURL();

const props = defineProps<{
  serviceInstance: ServiceInstanceTO;
}>();

const createdDate = useDateFormat(props.serviceInstance.startTime, DATE_FORMAT);
const endedDate = useDateFormat(props.serviceInstance.endTime, DATE_FORMAT);

const frontendURL = computed(() => {
  return `${digiWFBaseURL!.value}/#/${FRONTEND_INSTANCE_PATH}/${
    props.serviceInstance.id
  }`;
});
</script>

<style scoped>
.list-group-item {
  --cui-list-group-color: var(
    --lhm-widget-color-text,
    var(--lhm-widget-color-text-default)
  );
  --cui-list-group-bg: var(
    --lhm-widget-color-background,
    var(--lhm-widget-color-background-default)
  );
  --cui-list-group-action-hover-color: var(
    --lhm-widget-color-text,
    var(--lhm-widget-color-text-default)
  );
  --cui-list-group-action-hover-bg: var(
    --lhm-widget-color-background,
    var(--lhm-widget-color-background-default)
  );
  --cui-list-group-action-active-color: var(
    --lhm-widget-color-text,
    var(--lhm-widget-color-text-default)
  );
  --cui-list-group-action-active-bg: var(
    --lhm-widget-color-background,
    var(--lhm-widget-color-background-default)
  );
}
</style>
