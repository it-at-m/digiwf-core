<template>
  <c-list-group-item
    component="a"
    :href="frontendURL"
    target="_blank"
    class="p-3"
    :title="newTabText"
    role="link"
    :aria-label="newTabText"
  >
    <dynamic-heading root-offset="1" class="mb-3 text-title">
      {{ serviceInstance.definitionName }}
    </dynamic-heading>
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
import { computed, ref } from "vue";

import { useBaseURL } from "@/composables/useBaseURL";
import { DATE_FORMAT, FRONTEND_INSTANCE_PATH } from "@/util/constants";
import { useNewTabText } from "@/composables/useNewTabText";
import DynamicHeading from "@/components/common/DynamicHeading.vue";

const { baseURL } = useBaseURL();

const props = defineProps<{
  serviceInstance: ServiceInstanceTO;
}>();

const { newTabText } = useNewTabText(ref("Vorgang in DigiWF öffnen"))

const createdDate = useDateFormat(props.serviceInstance.startTime, DATE_FORMAT);
const endedDate = useDateFormat(props.serviceInstance.endTime, DATE_FORMAT);

const frontendURL = computed(() => {
  return `${baseURL!.value}/#/${FRONTEND_INSTANCE_PATH}/${
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
  font-size: var(
    --digiwf-webcomponent-font-size-text,
    var(--digiwf-webcomponent-font-size-text-default)
  );
}
.text-title {
  font-size: var(
    --digiwf-webcomponent-font-size-title,
    var(--digiwf-webcomponent-font-size-title-default)
  );
}
</style>
